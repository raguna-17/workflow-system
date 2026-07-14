import { useState } from "react";

import { deleteTodo } from "./api";
import TodoEditModal from "./TodoEditModal";

function TodoItem({ todo, onUpdated }) {
    const [open, setOpen] = useState(false);

    const handleDelete = async () => {
        if (!window.confirm("削除しますか？")) {
            return;
        }

        try {
            await deleteTodo(todo.id);
            onUpdated();
        } catch (error) {
            console.error(error);
            alert("削除に失敗しました。");
        }
    };

    return (
        <>
            <div
                style={{
                    display: "flex",
                    gap: "12px",
                    alignItems: "center",
                    marginBottom: "12px",
                }}
            >
                <span>
                    {todo.completed ? "✅" : "⬜"} {todo.title}
                </span>

                <button onClick={() => setOpen(true)}>
                    編集
                </button>

                <button onClick={handleDelete}>
                    削除
                </button>
            </div>

            {open && (
                <TodoEditModal
                    todo={todo}
                    onClose={() => setOpen(false)}
                    onUpdated={() => {
                        setOpen(false);
                        onUpdated();
                    }}
                />
            )}
        </>
    );
}

export default TodoItem;