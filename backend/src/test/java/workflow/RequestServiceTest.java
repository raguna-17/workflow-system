package workflow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.security.access.AccessDeniedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import workflow.domains.approval.ApprovalRepository;
import workflow.domains.request.*;
import workflow.domains.user.Role;
import workflow.domains.user.User;
import workflow.domains.workflow.Workflow;
import workflow.domains.workflow.WorkflowRepository;
import workflow.domains.workflow.WorkflowStep;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private WorkflowRepository workflowRepository;

    @Mock
    private ApprovalRepository approvalRepository;

    @InjectMocks
    private RequestService requestService;

//seijoukei
    @Test
    void createRequest() {
        User user = new User("test@test.com", "pass", Role.USER);
        Workflow workflow = new Workflow("稟議", user);
        workflow.addStep(new WorkflowStep(1, user));

        when(workflowRepository.findById(1L)).thenReturn(Optional.of(workflow));
        when(requestRepository.save(any(Request.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Request request = requestService.createRequest("タイトル", "内容", 1L, user);

        assertEquals("タイトル", request.getTitle());
        verify(requestRepository).save(any(Request.class));
        verify(approvalRepository).save(any());
    }

    @Test
    void getMyRequests() {
        User user = new User("test@test.com", "pass", Role.USER);
        ReflectionTestUtils.setField(user, "id", 1L);

        when(requestRepository.findByRequestedById(1L)).thenReturn(List.of());

        requestService.getMyRequests(user);

        verify(requestRepository).findByRequestedById(1L);
    }

    @Test
    void getRequest() {
        User user = new User("test@test.com", "pass", Role.USER);
        ReflectionTestUtils.setField(user, "id", 1L);

        Workflow workflow = new Workflow("稟議", user);
        Request request = new Request("タイトル", "内容", user, workflow);
        ReflectionTestUtils.setField(request, "id", 1L);

        when(requestRepository.findById(1L)).thenReturn(Optional.of(request));

        Request result = requestService.getRequest(1L, user);

        assertEquals("タイトル", result.getTitle());
    }

    @Test
    void getApprovals() {
        User user = new User("test@test.com", "pass", Role.USER);
        ReflectionTestUtils.setField(user, "id", 1L);

        Workflow workflow = new Workflow("稟議", user);
        Request request = new Request("タイトル", "内容", user, workflow);

        when(requestRepository.findById(1L)).thenReturn(Optional.of(request));
        when(approvalRepository.findByRequestId(1L)).thenReturn(List.of());

        assertTrue(requestService.getApprovals(1L, user).isEmpty());

        verify(approvalRepository).findByRequestId(1L);
    }




//ijoukei
    @Test
    void createRequest_Workflowが存在しない() {

        User user = new User("test@test.com", "pass", Role.USER);

        when(workflowRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> requestService.createRequest(
                        "タイトル",
                        "内容",
                        1L,
                        user
                )
        );
    }

    @Test
    void createRequest_Workflowに承認ステップが存在しない() {

        User user = new User("test@test.com", "pass", Role.USER);

        Workflow workflow = new Workflow("稟議", user);

        when(workflowRepository.findById(1L))
                .thenReturn(Optional.of(workflow));

        assertThrows(
                IllegalStateException.class,
                () -> requestService.createRequest(
                        "タイトル",
                        "内容",
                        1L,
                        user
                )
        );
    }

    @Test
    void getRequest_申請が存在しない() {

        User user = new User("test@test.com", "pass", Role.USER);

        when(requestRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> requestService.getRequest(1L, user)
        );
    }

    @Test
    void getRequest_他人の申請は取得できない() {

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
                () -> requestService.getRequest(1L, other)
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
                () -> requestService.getApprovals(1L, other)
        );
    }
}