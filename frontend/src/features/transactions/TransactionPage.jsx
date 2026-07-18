import { useEffect, useState } from "react";

import TransactionForm from "./TransactionForm";
import {
    getTransactions,
    deleteTransaction,
} from "./api";

function TransactionPage() {

    const [transactions, setTransactions] = useState([]);
    const [error, setError] = useState("");


    const fetchTransactions = async () => {

        try {

            const data =
                await getTransactions();

            setTransactions(data);

        } catch {

            setError(
                "取引一覧の取得に失敗しました"
            );

        }

    };


    useEffect(() => {

        fetchTransactions();

    }, []);


    const handleDelete = async (id) => {

        const ok =
            window.confirm(
                "この取引を削除しますか？"
            );

        if (!ok) {
            return;
        }

        try {

            await deleteTransaction(id);

            fetchTransactions();

        } catch {

            setError(
                "取引の削除に失敗しました"
            );

        }

    };


    return (

        <div>

            <TransactionForm
                onCreated={fetchTransactions}
            />

            <hr />

            <h2>
                取引一覧
            </h2>

            {error && (
                <p>{error}</p>
            )}

            {transactions.length === 0 ? (

                <p>
                    取引はありません
                </p>

            ) : (

                <table>

                    <thead>

                        <tr>

                            <th>日付</th>
                            <th>カテゴリ</th>
                            <th>区分</th>
                            <th>金額</th>
                            <th>説明</th>
                            <th></th>

                        </tr>

                    </thead>

                    <tbody>

                        {transactions.map(transaction => (

                            <tr key={transaction.id}>

                                <td>
                                    {transaction.transactionDate}
                                </td>

                                <td>
                                    {transaction.categoryName}
                                </td>

                                <td>
                                    {transaction.categoryType === "EXPENSE"
                                        ? "支出"
                                        : "収入"}
                                </td>

                                <td>
                                    {transaction.amount.toLocaleString()} 円
                                </td>

                                <td>
                                    {transaction.description}
                                </td>

                                <td>

                                    <button
                                        onClick={() =>
                                            handleDelete(transaction.id)
                                        }
                                    >
                                        削除
                                    </button>

                                </td>

                            </tr>

                        ))}

                    </tbody>

                </table>

            )}

        </div>

    );

}

export default TransactionPage;