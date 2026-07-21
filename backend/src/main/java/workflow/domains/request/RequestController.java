package workflow.domains.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import workflow.domains.approval.ApprovalDto;
import workflow.domains.user.User;

import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    /**
     * 申請作成
     */
    @PostMapping
    public ResponseEntity<RequestDto.Response> create(
            @RequestBody RequestDto.RequestCreateRequest requestDto,
            Authentication authentication){

        User user=(User)authentication.getPrincipal();

        Request request=requestService.createRequest(
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getWorkflowId(),
                user
        );

        return ResponseEntity.ok(new RequestDto.Response(request));
    }

    /**
     * 自分の申請一覧
     */
    @GetMapping("/my")
    public ResponseEntity<List<RequestDto.Response>> getMyRequests(
            Authentication authentication){

        User user=(User)authentication.getPrincipal();

        List<RequestDto.Response> response=requestService
                .getMyRequests(user)
                .stream()
                .map(RequestDto.Response::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    /**
     * 申請詳細
     */
    @GetMapping("/{id}")
    public ResponseEntity<RequestDto.Response> getRequest(
            @PathVariable Long id,
            Authentication authentication){

        User user=(User)authentication.getPrincipal();

        Request request=requestService.getRequest(id,user);

        return ResponseEntity.ok(new RequestDto.Response(request));
    }

    /**
     * 申請に紐づく承認一覧
     */
    @GetMapping("/{requestId}/approvals")
    public ResponseEntity<List<ApprovalDto.Response>> getApprovals(
            @PathVariable Long requestId,
            Authentication authentication){

        User user=(User)authentication.getPrincipal();

        List<ApprovalDto.Response> response=requestService
                .getApprovals(requestId,user)
                .stream()
                .map(ApprovalDto.Response::new)
                .toList();

        return ResponseEntity.ok(response);
    }
}
