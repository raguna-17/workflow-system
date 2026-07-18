import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

import { login } from "./api";


function LoginPage() {

    const navigate = useNavigate();


    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [error, setError] = useState("");



    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const data = await login({
                email,
                password
            });


            localStorage.setItem(
                "accessToken",
                data.accessToken
            );


            localStorage.setItem(
                "refreshToken",
                data.refreshToken
            );


            navigate("/transactions");

        } catch (err) {

            setError(
                "メールアドレスまたはパスワードが違います"
            );

        }
    };



    return (
        <div>

            <h1>
                ログイン
            </h1>


            {error && (
                <p>
                    {error}
                </p>
            )}


            <form onSubmit={handleSubmit}>


                <input
                    type="email"
                    placeholder="メールアドレス"
                    value={email}
                    onChange={
                        e => setEmail(e.target.value)
                    }
                />


                <input
                    type="password"
                    placeholder="パスワード"
                    value={password}
                    onChange={
                        e => setPassword(e.target.value)
                    }
                />


                <button type="submit">
                    ログイン
                </button>


            </form>


            <Link to="/register">
                アカウント作成はこちら
            </Link>


        </div>
    );
}


export default LoginPage;