package workflow.domains.approval;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;



@Repository
public interface ApprovalRepository
        extends JpaRepository<Approval, Long> {



    /**
     * Requestに紐づく承認一覧
     */
    List<Approval> findByRequestId(
            Long requestId
    );



    /**
     * 承認者が担当する承認一覧
     *
     * WorkflowStepのapproverを参照
     */
    List<Approval> findByWorkflowStepApproverId(
            Long userId
    );



    /**
     * 状態ごとの承認一覧
     */
    List<Approval> findByStatus(
            ApprovalStatus status
    );



    /**
     * 承認者が担当する承認待ち一覧
     */
    List<Approval> findByWorkflowStepApproverIdAndStatus(
            Long userId,

            ApprovalStatus status
    );


}