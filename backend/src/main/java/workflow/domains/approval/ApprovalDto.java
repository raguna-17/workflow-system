package workflow.domains.approval;


import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;



public class ApprovalDto {



    /**
     * 承認・却下リクエスト
     */
    @Getter
    @NoArgsConstructor
    public static class ActionRequest {


        /**
         * 承認コメント
         */
        private String comment;



        public ActionRequest(
                String comment
        ) {

            this.comment = comment;

        }

    }





    /**
     * 承認レスポンス
     */
    @Getter
    public static class Response {


        private Long id;


        /**
         * 対象Request ID
         */
        private Long requestId;


        /**
         * WorkflowStep ID
         */
        private Long workflowStepId;


        /**
         * 承認者ID
         */
        private Long approvedById;


        /**
         * 状態
         */
        private ApprovalStatus status;


        /**
         * コメント
         */
        private String comment;


        /**
         * 作成日時
         */
        private LocalDateTime createdAt;


        /**
         * 承認日時
         */
        private LocalDateTime approvedAt;



        public Response(
                Approval approval
        ) {


            this.id = approval.getId();


            this.requestId =
                    approval.getRequest()
                            .getId();


            this.workflowStepId =
                    approval.getWorkflowStep()
                            .getId();



            if(approval.getApprovedBy() != null){

                this.approvedById =
                        approval.getApprovedBy()
                                .getId();

            }



            this.status =
                    approval.getStatus();


            this.comment =
                    approval.getComment();


            this.createdAt =
                    approval.getCreatedAt();


            this.approvedAt =
                    approval.getApprovedAt();

        }

    }


}