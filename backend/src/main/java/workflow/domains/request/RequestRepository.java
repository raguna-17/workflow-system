package workflow.domains.request;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RequestRepository
        extends JpaRepository<Request, Long> {


    /**
     * 申請者ごとの申請一覧
     */
    List<Request> findByRequestedById(
            Long userId
    );


    /**
     * 状態ごとの申請一覧
     */
    List<Request> findByStatus(
            RequestStatus status
    );


    /**
     * ワークフローごとの申請一覧
     */
    List<Request> findByWorkflowId(
            Long workflowId
    );

}