import { useEffect, useState } from "react";

import { getTodos } from "./api";

import TodoCreateForm from "./TodoCreateForm";
import TodoItem from "./TodoItem";
import Spinner from "../../components/Spinner";

function TodoListPage() {
    const [todos, setTodos] = useState([]);
    const [loading, setLoading] = useState(true);

    // Todo一覧取得
    const fetchTodos = async () => {
        try {
            setLoading(true);

            const data = await getTodos();

            setTodos(data);
        } catch (error) {
            console.error("Todo取得失敗", error);
            alert("Todoの取得に失敗しました。");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchTodos();
    }, []);

    if (loading) {
        return <Spinner />;
    }

    return (
        <div style={{ maxWidth: "700px", margin: "40px auto" }}>
            <h1>Todo一覧</h1>

            <TodoCreateForm onCreated={fetchTodos} />

            <div style={{ marginTop: "24px" }}>
                {todos.length === 0 ? (
                    <p>Todoはありません。</p>
                ) : (
                    todos.map((todo) => (
                        <TodoItem
                            key={todo.id}
                            todo={todo}
                            onUpdated={fetchTodos}
                        />
                    ))
                )}
            </div>
        </div>
    );
}

export default TodoListPage;