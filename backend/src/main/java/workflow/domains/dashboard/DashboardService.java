package workflow.domains.dashboard;


import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import workflow.domains.approval.ApprovalRepository;
import workflow.domains.approval.ApprovalStatus;
import workflow.domains.request.RequestRepository;
import workflow.domains.request.RequestStatus;
import workflow.domains.user.User;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {



    private final RequestRepository requestRepository;

    private final ApprovalRepository approvalRepository;





    /**
     * ダッシュボード取得
     */
    public DashboardResponse getDashboard(

            User user

    ) {


        long requestCount =

                requestRepository
                        .findByRequestedById(
                                user.getId()
                        )
                        .size();




        long approvedCount =

                requestRepository
                        .findByRequestedById(
                                user.getId()
                        )
                        .stream()

                        .filter(request ->
                                request.getStatus()
                                        == RequestStatus.APPROVED
                        )

                        .count();





        long rejectedCount =

                requestRepository
                        .findByRequestedById(
                                user.getId()
                        )
                        .stream()

                        .filter(request ->
                                request.getStatus()
                                        == RequestStatus.REJECTED
                        )

                        .count();






        long pendingApprovalCount =

            approvalRepository
                .findByWorkflowStepApproverIdAndStatus(
                        user.getId(),
                        ApprovalStatus.PENDING
                )
                .size();


return new DashboardResponse(

                requestCount,

                approvedCount,

                rejectedCount,

                pendingApprovalCount

        );

    }






    public record DashboardResponse(

            long requestCount,

            long approvedCount,

            long rejectedCount,

            long pendingApprovalCount

    ){}



}