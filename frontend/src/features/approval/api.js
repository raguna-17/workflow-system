import api from "../../lib/axios";


// 指定した申請の承認一覧取得
export const getApprovals = async (requestId) => {

    const response = await api.get(
        `/approvals/request/${requestId}`
    );

    return response.data;

};




// 承認
export const approveApproval = async (
    approvalId,
    comment
) => {

    const response = await api.post(

        `/approvals/${approvalId}/approve`,

        {
            comment
        }

    );


    return response.data;

};




// 却下
export const rejectApproval = async (
    approvalId,
    comment
) => {

    const response = await api.post(

        `/approvals/${approvalId}/reject`,

        {
            comment
        }

    );


    return response.data;

};