import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

import { register } from "./api";


function RegisterPage() {


    const navigate = useNavigate();


    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [error, setError] = useState("");



    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const response = await register({
                email,
                password
            });

            localStorage.setItem(
                "accessToken",
                response.accessToken
            );

            localStorage.setItem(
                "refreshToken",
                response.refreshToken
            );

            navigate("/");

        } catch (err) {

            setError(
                "登録に失敗しました"
            );

        }

    };



    return (

        <div>

            <h1>
                ユーザー登録
            </h1>


            {error && (
                <p>{error}</p>
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
                    登録
                </button>


            </form>


            <Link to="/login">
                ログインはこちら
            </Link>


        </div>

    );
}


export default RegisterPage;