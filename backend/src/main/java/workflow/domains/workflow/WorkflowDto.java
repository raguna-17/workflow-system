package workflow.domains.workflow;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class WorkflowDto {

    /** Workflow作成 */
    public record WorkflowCreateRequest(@NotBlank String name) {}

    /** WorkflowStep追加 */
    public record StepCreateRequest(@NotNull Integer stepOrder, @NotNull Long approverId) {}

    /** Workflowレスポンス */
    public record Response(Long id, String name, Long createdBy) {}

    /** Workflow詳細 */
    public record DetailResponse(Long id, String name, Long createdBy, List<StepResponse> steps) {}

    /** WorkflowStepレスポンス */
    public record StepResponse(Long id, Integer stepOrder, Long approverId, String approverEmail) {}
}