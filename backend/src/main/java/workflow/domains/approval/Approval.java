package workflow.domains.approval;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;


import workflow.domains.request.Request;
import workflow.domains.user.User;
import workflow.domains.workflow.WorkflowStep;


import java.time.LocalDateTime;



@Entity
@Getter
@NoArgsConstructor
@Table(name = "approvals")
public class Approval {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    /**
     * 対象申請
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "request_id",
            nullable = false
    )
    private Request request;



    /**
     * 承認対象ステップ
     *
     * 例:
     * 部長承認
     * 総務承認
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "workflow_step_id",
            nullable = false
    )
    private WorkflowStep workflowStep;



    /**
     * 承認者
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "approved_by"
    )
    private User approvedBy;



    /**
     * 承認状態
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus status;



    /**
     * コメント
     */
    @Column(columnDefinition = "TEXT")
    private String comment;



    /**
     * 作成日時
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;



    /**
     * 承認日時
     */
    private LocalDateTime approvedAt;





    public Approval(

            Request request,

            WorkflowStep workflowStep

    ) {


        this.request = request;

        this.workflowStep = workflowStep;

        this.status = ApprovalStatus.PENDING;

        this.createdAt = LocalDateTime.now();

    }





    /**
     * 承認処理
     */
    public void approve(

            User user,

            String comment

    ) {


        this.approvedBy = user;

        this.comment = comment;

        this.status = ApprovalStatus.APPROVED;

        this.approvedAt = LocalDateTime.now();

    }






    /**
     * 却下処理
     */
    public void reject(

            User user,

            String comment

    ) {


        this.approvedBy = user;

        this.comment = comment;

        this.status = ApprovalStatus.REJECTED;

        this.approvedAt = LocalDateTime.now();

    }


}