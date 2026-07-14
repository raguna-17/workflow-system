import { useState } from "react";

import { updateTodo } from "./api";

function TodoEditModal({
    todo,
    onClose,
    onUpdated,
}) {
    const [title, setTitle] = useState(todo.title);
    const [completed, setCompleted] = useState(todo.completed);

    const handleSave = async () => {
        try {
            await updateTodo(todo.id, {
                title,
                completed,
            });

            onUpdated();
        } catch (error) {
            console.error(error);
            alert("更新に失敗しました。");
        }
    };

    return (
        <div
            style={{
                border: "1px solid #ccc",
                padding: "20px",
                marginTop: "10px",
            }}
        >
            <h3>Todo編集</h3>

            <input
                value={title}
                onChange={(e) => setTitle(e.target.value)}
            />

            <div>
                <label>
                    <input
                        type="checkbox"
                        checked={completed}
                        onChange={(e) =>
                            setCompleted(e.target.checked)
                        }
                    />
                    完了
                </label>
            </div>

            <button onClick={handleSave}>
                保存
            </button>

            <button onClick={onClose}>
                キャンセル
            </button>
        </div>
    );
}

export default TodoEditModal;