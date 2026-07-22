package workflow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.util.ReflectionTestUtils;
import workflow.domains.approval.*;
import workflow.domains.request.*;
import workflow.domains.user.Role;
import workflow.domains.user.User;
import workflow.domains.workflow.Workflow;
import workflow.domains.workflow.WorkflowStep;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApprovalServiceTest {

    @Mock
    private ApprovalRepository approvalRepository;

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private ApprovalService approvalService;

    // 正常系

    @Test
    void findById() {

        User user = new User("test@test.com", "pass", Role.USER);
        Workflow workflow = new Workflow("稟議", user);
        Request request = new Request("タイトル", "内容", user, workflow);
        WorkflowStep step = new WorkflowStep(1, user);

        Approval approval = new Approval(request, step);

        when(approvalRepository.findById(1L))
                .thenReturn(Optional.of(approval));

        Approval result = approvalService.findById(1L);

        assertEquals(approval, result);
    }

    @Test
    void getMyApprovals() {

        User user = new User("test@test.com", "pass", Role.USER);
        ReflectionTestUtils.setField(user, "id", 1L);

        when(approvalRepository.findByWorkflowStepApproverId(1L))
                .thenReturn(List.of());

        assertTrue(
                approvalService.getMyApprovals(user, null).isEmpty()
        );

        verify(approvalRepository)
                .findByWorkflowStepApproverId(1L);
    }

    @Test
    void getMyApprovals_Status指定() {

        User user = new User("test@test.com", "pass", Role.USER);
        ReflectionTestUtils.setField(user, "id", 1L);

        when(approvalRepository.findByWorkflowStepApproverIdAndStatus(
                1L,
                ApprovalStatus.PENDING
        )).thenReturn(List.of());

        assertTrue(
                approvalService.getMyApprovals(
                        user,
                        ApprovalStatus.PENDING
                ).isEmpty()
        );

        verify(approvalRepository)
                .findByWorkflowStepApproverIdAndStatus(
                        1L,
                        ApprovalStatus.PENDING
                );
    }

    @Test
    void getApprovals() {

        User user = new User("test@test.com", "pass", Role.USER);
        ReflectionTestUtils.setField(user, "id", 1L);

        Workflow workflow = new Workflow("稟議", user);
        Request request = new Request("タイトル", "内容", user, workflow);

        when(requestRepository.findById(1L))
                .thenReturn(Optional.of(request));

        when(approvalRepository.findByRequestId(1L))
                .thenReturn(List.of());

        assertTrue(
                approvalService.getApprovals(1L, user).isEmpty()
        );

        verify(approvalRepository)
                .findByRequestId(1L);
    }

    @Test
    void approve() {

        User user = new User("test@test.com", "pass", Role.USER);

        Workflow workflow = new Workflow("稟議", user);

        Request request =
                new Request("タイトル", "内容", user, workflow);

        ReflectionTestUtils.setField(request, "id", 1L);

        WorkflowStep step =
                new WorkflowStep(1, user);

        Approval approval =
                new Approval(request, step);

        when(approvalRepository.findById(1L))
                .thenReturn(Optional.of(approval));

        when(approvalRepository.findByRequestId(1L))
                .thenReturn(List.of(approval));

        Approval result =
                approvalService.approve(
                        1L,
                        "承認",
                        user
                );

        assertEquals(
                ApprovalStatus.APPROVED,
                result.getStatus()
        );

        assertEquals(
                RequestStatus.APPROVED,
                request.getStatus()
        );
    }

    @Test
    void reject() {

        User user = new User("test@test.com", "pass", Role.USER);

        Workflow workflow = new Workflow("稟議", user);

        Request request =
                new Request("タイトル", "内容", user, workflow);

        WorkflowStep step =
                new WorkflowStep(1, user);

        Approval approval =
                new Approval(request, step);

        when(approvalRepository.findById(1L))
                .thenReturn(Optional.of(approval));

        Approval result =
                approvalService.reject(
                        1L,
                        "却下",
                        user
                );

        assertEquals(
                ApprovalStatus.REJECTED,
                result.getStatus()
        );

        assertEquals(
                RequestStatus.REJECTED,
                request.getStatus()
        );
    }

    // 異常系

    @Test
    void findById_承認情報が存在しない() {

        when(approvalRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> approvalService.findById(1L)
        );
    }

    @Test
    void getApprovals_申請が存在しない() {

        User user = new User("test@test.com", "pass", Role.USER);

        when(requestRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> approvalService.getApprovals(1L, user)
        );
    }

    @Test
    void getApprovals_他人の申請は取得できない() {

        User owner = new User("owner@test.com", "pass", Role.USER);
        ReflectionTestUtils.setField(owner, "id", 1L);

        User other = new User("other@test.com", "pass", Role.USER);
        ReflectionTestUtils.setField(other, "id", 2L);

        Workflow workflow = new Workflow("稟議", owner);

        Request request =
                new Request("タイトル", "内容", owner, workflow);

        when(requestRepository.findById(1L))
                .thenReturn(Optional.of(request));

        assertThrows(
                AccessDeniedException.class,
                () -> approvalService.getApprovals(1L, other)
        );
    }

    @Test
    void approve_承認情報が存在しない() {

        User user = new User("test@test.com", "pass", Role.USER);

        when(approvalRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> approvalService.approve(
                        1L,
                        "承認",
                        user
                )
        );
    }

    @Test
    void approve_処理済みは承認できない() {

        User user = new User("test@test.com", "pass", Role.USER);

        Workflow workflow = new Workflow("稟議", user);
        Request request =
                new Request("タイトル", "内容", user, workflow);

        WorkflowStep step =
                new WorkflowStep(1, user);

        Approval approval =
                new Approval(request, step);

        approval.approve(user, "済");

        when(approvalRepository.findById(1L))
                .thenReturn(Optional.of(approval));

        assertThrows(
                IllegalStateException.class,
                () -> approvalService.approve(
                        1L,
                        "再承認",
                        user
                )
        );
    }

    @Test
    void reject_承認情報が存在しない() {

        User user = new User("test@test.com", "pass", Role.USER);

        when(approvalRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> approvalService.reject(
                        1L,
                        "却下",
                        user
                )
        );
    }

    @Test
    void reject_処理済みは却下できない() {

        User user = new User("test@test.com", "pass", Role.USER);

        Workflow workflow = new Workflow("稟議", user);
        Request request =
                new Request("タイトル", "内容", user, workflow);

        WorkflowStep step =
                new WorkflowStep(1, user);

        Approval approval =
                new Approval(request, step);

        approval.reject(user, "済");

        when(approvalRepository.findById(1L))
                .thenReturn(Optional.of(approval));

        assertThrows(
                IllegalStateException.class,
                () -> approvalService.reject(
                        1L,
                        "再却下",
                        user
                )
        );
    }
}