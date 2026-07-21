import { useState } from "react";
import { register } from "./api";

function UserForm({ onSuccess }) {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await register({
                email,
                password
            });

            setEmail("");
            setPassword("");

            if (onSuccess) {
                onSuccess();
            }

        } catch (err) {
            setError("ユーザー登録に失敗しました");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>ユーザー登録</h2>

            {error && <p>{error}</p>}

            <input
                type="email"
                placeholder="メールアドレス"
                value={email}
                onChange={e => setEmail(e.target.value)}
            />

            <input
                type="password"
                placeholder="パスワード"
                value={password}
                onChange={e => setPassword(e.target.value)}
            />

            <button type="submit">
                登録
            </button>
        </form>
    );
}

export default UserForm;