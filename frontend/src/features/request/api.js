import api from "../../lib/axios";


// 自分の申請一覧取得
export const getMyRequests = async () => {

    const response = await api.get(
        "/requests/my"
    );

    return response.data;

};



// 申請作成
export const createRequest = async (requestData) => {

    const response = await api.post(
        "/requests",
        requestData
    );

    return response.data;

};