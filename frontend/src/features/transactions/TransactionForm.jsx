import { useEffect, useState } from "react";

import { createTransaction } from "./api";
import { getCategories } from "../categories/api";

function TransactionForm({ onCreated }) {

    const [amount, setAmount] = useState("");
    const [description, setDescription] = useState("");
    const [transactionDate, setTransactionDate] = useState("");
    const [categoryId, setCategoryId] = useState("");

    const [categories, setCategories] = useState([]);

    const [error, setError] = useState("");

    useEffect(() => {

        const fetchCategories = async () => {

            try {

                const data = await getCategories();

                setCategories(data);

            } catch {

                setError("カテゴリの取得に失敗しました");

            }

        };

        fetchCategories();

    }, []);


    const handleSubmit = async (e) => {

        e.preventDefault();

        setError("");

        try {

            await createTransaction({

                amount: Number(amount),

                description,

                transactionDate,

                categoryId: Number(categoryId),

            });

            setAmount("");
            setDescription("");
            setTransactionDate("");
            setCategoryId("");

            if (onCreated) {
                onCreated();
            }

        } catch {

            setError("取引の登録に失敗しました");

        }

    };


    return (

        <form onSubmit={handleSubmit}>

            <h2>取引登録</h2>

            {error && <p>{error}</p>}

            <div>

                <label>金額</label>

                <input
                    type="number"
                    value={amount}
                    onChange={(e) =>
                        setAmount(e.target.value)
                    }
                    required
                />

            </div>

            <div>

                <label>説明</label>

                <input
                    type="text"
                    value={description}
                    onChange={(e) =>
                        setDescription(e.target.value)
                    }
                />

            </div>

            <div>

                <label>日付</label>

                <input
                    type="date"
                    value={transactionDate}
                    onChange={(e) =>
                        setTransactionDate(e.target.value)
                    }
                    required
                />

            </div>

            <div>

                <label>カテゴリ</label>

                <select
                    value={categoryId}
                    onChange={(e) =>
                        setCategoryId(e.target.value)
                    }
                    required
                >

                    <option value="">
                        選択してください
                    </option>

                    {categories.map(category => (

                        <option
                            key={category.id}
                            value={category.id}
                        >
                            {category.name}
                            {" "}
                            (
                            {category.type === "EXPENSE"
                                ? "支出"
                                : "収入"}
                            )
                        </option>

                    ))}

                </select>

            </div>

            <button type="submit">

                登録

            </button>

        </form>

    );

}

export default TransactionForm;