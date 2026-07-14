import axios from "../../lib/axios";

// 一覧取得
export const getTodos = async () => {
    const response = await axios.get("/todos");
    return response.data;
};

// 1件取得
export const getTodo = async (id) => {
    const response = await axios.get(`/todos/${id}`);
    return response.data;
};

// 作成
export const createTodo = async (todo) => {
    const response = await axios.post("/todos", todo);
    return response.data;
};

// 更新
export const updateTodo = async (id, todo) => {
    const response = await axios.put(`/todos/${id}`, todo);
    return response.data;
};

// 削除
export const deleteTodo = async (id) => {
    await axios.delete(`/todos/${id}`);
};