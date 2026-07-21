package workflow.domains.workflow;


import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/workflows")
public class WorkflowController {



    private final WorkflowService workflowService;




    public WorkflowController(

            WorkflowService workflowService

    ) {

        this.workflowService = workflowService;

    }






    /**
     * Workflow作�E
     *
     * ADMINのみ
     */
    @PostMapping
    public ResponseEntity<WorkflowDto.Response> createWorkflow(

            @Valid
            @RequestBody
            WorkflowDto.WorkflowCreateRequest request

    ) {


        WorkflowDto.Response response =

                workflowService.createWorkflow(request);



        return ResponseEntity

                .status(HttpStatus.CREATED)

                .body(response);

    }







    /**
     * Workflow一覧取征E
     *
     * 認証ユーザー利用可能
     */
    @GetMapping
    public ResponseEntity<List<WorkflowDto.Response>> getWorkflows() {


        return ResponseEntity.ok(

                workflowService.getWorkflows()

        );

    }


    @PostMapping("/{workflowId}/steps")
    public ResponseEntity<Void> addStep(
                @PathVariable Long workflowId,
                @Valid @RequestBody WorkflowDto.StepCreateRequest request) {

        workflowService.addStep(workflowId, request);
        return ResponseEntity.ok().build();
    }




    /**
     * Workflow詳細取征E
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkflowDto.DetailResponse> getWorkflow(

            @PathVariable Long id

    ) {


        return ResponseEntity.ok(

                workflowService.getWorkflow(id)

        );

    }

}
