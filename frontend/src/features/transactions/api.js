import api from "../../lib/axios";

/**
 * 取引一覧取得
 */
export const getTransactions = async () => {

    const response = await api.get(
        "/transactions"
    );

    return response.data;
};


/**
 * 取引登録
 */
export const createTransaction = async (data) => {

    const response = await api.post(
        "/transactions",
        data
    );

    return response.data;
};


/**
 * 取引削除
 */
export const deleteTransaction = async (id) => {

    await api.delete(
        `/transactions/${id}`
    );

};