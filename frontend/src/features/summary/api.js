import api from "../../lib/axios";

/**
 * カテゴリ別支出集計取得
 */
export const getExpenseSummary = async () => {

    const response = await api.get(
        "/summary/expenses"
    );

    return response.data;

};