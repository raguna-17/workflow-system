package workflow.domains.request;


public enum RequestStatus {


    /**
     * 申請中
     */
    SUBMITTED,


    /**
     * 承認処理中
     */
    IN_PROGRESS,


    /**
     * 承認完了
     */
    APPROVED,


    /**
     * 却下
     */
    REJECTED,


    /**
     * 取消
     */
    CANCELLED

}