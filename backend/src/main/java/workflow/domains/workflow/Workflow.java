package workflow.domains.workflow;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import workflow.domains.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "workflows")
public class Workflow {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * ワークフロー名
     */
    @Column(nullable = false)
    private String name;



    /**
     * 作成者
     * ADMINを想定
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "created_by",
            nullable = false
    )
    private User createdBy;



    /**
     * 承認ステップ一覧
     */
    @OneToMany(
            mappedBy = "workflow",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("stepOrder ASC")
    private List<WorkflowStep> steps =
            new ArrayList<>();



    @Column(nullable = false)
    private LocalDateTime createdAt;



    private LocalDateTime updatedAt;




    public Workflow(
            String name,
            User createdBy

    ) {

        this.name = name;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();

    }




    /**
     * 承認ステップ追加
     */
    public void addStep(
            WorkflowStep step
    ) {

        steps.add(step);
        step.setWorkflow(this);

    }

}