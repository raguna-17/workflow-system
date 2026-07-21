package workflow.domains.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workflow.domains.approval.Approval;
import workflow.domains.approval.ApprovalRepository;
import workflow.domains.user.User;
import workflow.domains.workflow.Workflow;
import workflow.domains.workflow.WorkflowRepository;
import workflow.domains.workflow.WorkflowStep;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestService {

    private final RequestRepository requestRepository;
    private final WorkflowRepository workflowRepository;
    private final ApprovalRepository approvalRepository;

    /**
     * 申請作成
     */
    @Transactional
    public Request createRequest(String title, String content, Long workflowId, User user) {

        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new IllegalArgumentException("Workflowが存在しません"));

        Request request = new Request(title, content, user, workflow);
        Request savedRequest = requestRepository.save(request);

        for (WorkflowStep step : workflow.getSteps()) {
            Approval approval = new Approval(savedRequest, step);
            approvalRepository.save(approval);
        }

        return savedRequest;
    }

    /**
     * 自分の申請一覧
     */
    public List<Request> getMyRequests(User user) {
        return requestRepository.findByRequestedById(user.getId());
    }

    /**
     * 詳細取得
     */
    public Request getRequest(Long requestId, User user) {

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("申請が存在しません"));

        if (!request.getRequestedBy().getId().equals(user.getId())) {
            throw new SecurityException("アクセス権限がありません");
        }

        return request;
    }
}