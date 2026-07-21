package workflow.domains.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


public class RequestDto {


    /**
     * 申請作�EリクエスチE
     */
    @Getter
    @NoArgsConstructor
    public static class RequestCreateRequest {


        private String title;


        private String content;


        private Long workflowId;


        public RequestCreateRequest(
                String title,
                String content,
                Long workflowId
        ) {

            this.title = title;
            this.content = content;
            this.workflowId = workflowId;

        }

    }



    /**
     * 申請レスポンス
     */
    @Getter
    public static class Response {


        private Long id;


        private String title;


        private String content;


        private String status;


        private Long requesterId;


        private Long workflowId;


        private LocalDateTime createdAt;



        public Response(
                Request request
        ) {

            this.id = request.getId();
            this.title = request.getTitle();
            this.content = request.getContent();
            this.status = request.getStatus().name();

            this.requesterId =
                    request.getRequestedBy().getId();

            this.workflowId =
                    request.getWorkflow().getId();

            this.createdAt =
                    request.getCreatedAt();

        }

    }

}
