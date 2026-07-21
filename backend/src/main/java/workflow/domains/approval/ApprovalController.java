package workflow.domains.approval;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import workflow.domains.user.User;

import java.util.List;

@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
public class ApprovalController {

    private final ApprovalService approvalService;

    /**
     * 自分が担当する承認一覧取得
     */
    @GetMapping("/my")
    public ResponseEntity<List<ApprovalDto.Response>> getMyApprovals(
            @RequestParam(required = false) ApprovalStatus status,
            Authentication authentication){

        User user=getUser(authentication);

        List<ApprovalDto.Response> response=approvalService
                .getMyApprovals(user,status)
                .stream()
                .map(ApprovalDto.Response::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    /**
     * Requestに紐づく承認一覧取得
     */
    @GetMapping("/request/{requestId}")
    public ResponseEntity<List<ApprovalDto.Response>> getApprovals(
            @PathVariable Long requestId,
            Authentication authentication){

        User user=getUser(authentication);

        List<ApprovalDto.Response> response=approvalService
                .getApprovals(requestId,user)
                .stream()
                .map(ApprovalDto.Response::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    /**
     * 承認詳細取得
     */
    @GetMapping("/{approvalId}")
    public ResponseEntity<ApprovalDto.Response> getApproval(
            @PathVariable Long approvalId,
            Authentication authentication){

        User user=getUser(authentication);
        checkApprovalPermission(user,approvalId);

        Approval approval=approvalService.findById(approvalId);

        return ResponseEntity.ok(new ApprovalDto.Response(approval));
    }

    /**
     * 承認
     */
    @PostMapping("/{approvalId}/approve")
    public ResponseEntity<ApprovalDto.Response> approve(
            @PathVariable Long approvalId,
            @RequestBody ApprovalDto.ActionRequest request,
            Authentication authentication){

        User user=getUser(authentication);
        checkApprovalPermission(user,approvalId);

        Approval approval=approvalService.approve(
                approvalId,
                request.getComment(),
                user
        );

        return ResponseEntity.ok(new ApprovalDto.Response(approval));
    }

    /**
     * 却下
     */
    @PostMapping("/{approvalId}/reject")
    public ResponseEntity<ApprovalDto.Response> reject(
            @PathVariable Long approvalId,
            @RequestBody ApprovalDto.ActionRequest request,
            Authentication authentication){

        User user=getUser(authentication);
        checkApprovalPermission(user,approvalId);

        Approval approval=approvalService.reject(
                approvalId,
                request.getComment(),
                user
        );

        return ResponseEntity.ok(new ApprovalDto.Response(approval));
    }

    /**
     * JWTユーザー取得
     */
    private User getUser(Authentication authentication){
        return (User) authentication.getPrincipal();
    }

    /**
     * 承認権限チェック
     */
    private void checkApprovalPermission(User user,Long approvalId){

        Approval approval=approvalService.findById(approvalId);

        // ADMINは許可
        if(user.getRole().name().equals("ADMIN")){
            return;
        }

        // USERの場合
        if(!approval.getWorkflowStep().getApprover().getId().equals(user.getId())){
            throw new AccessDeniedException("この承認を実行する権限がありません");
        }
    }
}