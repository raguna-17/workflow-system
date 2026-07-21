package workflow.domains.approval;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workflow.domains.request.Request;
import workflow.domains.request.RequestRepository;
import workflow.domains.request.RequestStatus;
import workflow.domains.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final RequestRepository requestRepository;

    public Approval findById(Long approvalId){
        return approvalRepository.findById(approvalId)
                .orElseThrow(() -> new IllegalArgumentException("承認情報が存在しません"));
    }

    /**
     * 自分が担当する承認一覧取得
     */
    public List<Approval> getMyApprovals(User user, ApprovalStatus status){
        if(status == null){
            return approvalRepository.findByWorkflowStepApproverId(user.getId());
        }
        return approvalRepository.findByWorkflowStepApproverIdAndStatus(user.getId(), status);
    }

    /**
     * Requestに紐づく承認一覧取得
     */
    public List<Approval> getApprovals(Long requestId, User user){

        Request request=requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("申請が存在しません"));

        if(!request.getRequestedBy().getId().equals(user.getId())){
            throw new AccessDeniedException("アクセス権限がありません");
        }

        return approvalRepository.findByRequestId(requestId);
    }

    /**
     * 承認処理
     */
    @Transactional
    public Approval approve(Long approvalId,String comment,User user){

        Approval approval=getApproval(approvalId);
        validatePending(approval);
        approval.approve(user,comment);
        updateRequestStatus(approval.getRequest());

        return approval;
    }

    /**
     * 却下処理
     */
    @Transactional
    public Approval reject(Long approvalId,String comment,User user){

        Approval approval=getApproval(approvalId);
        validatePending(approval);
        approval.reject(user,comment);
        approval.getRequest().changeStatus(RequestStatus.REJECTED);

        return approval;
    }

    /**
     * Approval取得
     */
    private Approval getApproval(Long approvalId){
        return approvalRepository.findById(approvalId)
                .orElseThrow(() -> new IllegalArgumentException("承認情報が存在しません"));
    }

    /**
     * 承認待ち確認
     */
    private void validatePending(Approval approval){
        if(approval.getStatus()!=ApprovalStatus.PENDING){
            throw new IllegalStateException("処理済みの承認です");
        }
    }

    /**
     * 全承認完了確認
     */
    private void updateRequestStatus(Request request){

        List<Approval> approvals=approvalRepository.findByRequestId(request.getId());

        boolean completed=approvals.stream()
                .allMatch(approval->approval.getStatus()==ApprovalStatus.APPROVED);

        if(completed){
            request.changeStatus(RequestStatus.APPROVED);
        }else{
            request.changeStatus(RequestStatus.IN_PROGRESS);
        }
    }
}