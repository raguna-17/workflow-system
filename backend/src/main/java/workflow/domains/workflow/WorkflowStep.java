package workflow.domains.workflow;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import workflow.domains.user.User;



@Entity
@Getter
@NoArgsConstructor
@Table(name = "workflow_steps")
public class WorkflowStep {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    /**
     * 所属Workflow
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "workflow_id",
            nullable = false
    )
    private Workflow workflow;




    /**
     * 承認順番
     *
     * 1 → 課長
     * 2 → 部長
     */
    @Column(nullable = false)
    private Integer stepOrder;




    /**
     * 承認者
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "approver_id",
            nullable = false
    )
    private User approver;




    public WorkflowStep(
            Integer stepOrder,
            User approver

    ) {

        this.stepOrder = stepOrder;
        this.approver = approver;

    }



    protected void setWorkflow(
            Workflow workflow

    ) {

        this.workflow = workflow;

    }

}