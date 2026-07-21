package workflow.domains.workflow;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workflow.domains.user.Role;
import workflow.domains.user.User;
import workflow.domains.user.UserRepository;

import java.util.List;

@Service
@Transactional
public class WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final UserRepository userRepository;

    public WorkflowService(WorkflowRepository workflowRepository, UserRepository userRepository) {
        this.workflowRepository = workflowRepository;
        this.userRepository = userRepository;
    }

    /** Workflow作成 */
    public WorkflowDto.Response createWorkflow(WorkflowDto.WorkflowCreateRequest request) {
        User currentUser = getCurrentUser();

        if (currentUser.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Workflow作成権限がありません");
        }

        Workflow workflow = new Workflow(request.name(), currentUser);
        Workflow savedWorkflow = workflowRepository.save(workflow);

        return new WorkflowDto.Response(
                savedWorkflow.getId(),
                savedWorkflow.getName(),
                savedWorkflow.getCreatedBy().getId()
        );
    }

    /** WorkflowStep追加 */
    public void addStep(Long workflowId, WorkflowDto.StepCreateRequest request) {

        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new IllegalArgumentException("Workflowが存在しません"));

        User approver = userRepository.findById(request.approverId())
                .orElseThrow(() -> new IllegalArgumentException("承認者が存在しません"));

        WorkflowStep step = new WorkflowStep(
                request.stepOrder(),
                approver
        );

        workflow.addStep(step);
        workflowRepository.save(workflow);
    }

    /** Workflow一覧 */
    @Transactional(readOnly = true)
    public List<WorkflowDto.Response> getWorkflows() {

        return workflowRepository.findAll().stream()
                .map(w -> new WorkflowDto.Response(
                        w.getId(),
                        w.getName(),
                        w.getCreatedBy().getId()))
                .toList();
    }

    /** Workflow詳細 */
    @Transactional(readOnly = true)
    public WorkflowDto.DetailResponse getWorkflow(Long id) {

        Workflow workflow = workflowRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workflowが存在しません"));

        List<WorkflowDto.StepResponse> steps = workflow.getSteps().stream()
                .map(step -> new WorkflowDto.StepResponse(
                        step.getId(),
                        step.getStepOrder(),
                        step.getApprover().getId(),
                        step.getApprover().getEmail()))
                .toList();

        return new WorkflowDto.DetailResponse(
                workflow.getId(),
                workflow.getName(),
                workflow.getCreatedBy().getId(),
                steps
        );
    }

    /** JWTから現在ログインユーザー取得 */
    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが存在しません"));
    }
}