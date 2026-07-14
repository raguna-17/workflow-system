import { useState } from "react";
import { createTodo } from "./api";

function TodoCreateForm({ onCreated }) {
    const [title, setTitle] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!title.trim()) {
            alert("タイトルを入力してください。");
            return;
        }

        try {
            await createTodo({
                title,
                completed: false,
            });

            setTitle("");
            onCreated();
        } catch (error) {
            console.error(error);
            alert("Todoの作成に失敗しました。");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder="Todoを入力"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
            />

            <button type="submit">
                追加
            </button>
        </form>
    );
}

export default TodoCreateForm;