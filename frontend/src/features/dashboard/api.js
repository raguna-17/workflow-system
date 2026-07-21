import api from "../../lib/axios";


// ダッシュボード取得
export const getDashboard = async () => {

    const response = await api.get(
        "/dashboard"
    );

    return response.data;

};