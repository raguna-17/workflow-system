import { useEffect, useState } from "react";

import { getExpenseSummary } from "./api";

import {
    PieChart,
    Pie,
    Cell,
    Tooltip,
    Legend,
    ResponsiveContainer,
} from "recharts";

const COLORS = [
    "#0088FE",
    "#00C49F",
    "#FFBB28",
    "#FF8042",
    "#A28CFF",
    "#FF6699",
    "#66CC99",
    "#44ff70",
];

function SummaryPage() {

    console.log("SummaryPage loaded");

    const [categoryExpenses, setCategoryExpenses] = useState([]);

    const [error, setError] = useState("");

    useEffect(() => {

        console.log("useEffect start");

        const fetchSummary = async () => {

            console.log("fetchSummary start");

            try {

                const data =
                    await getExpenseSummary();

                console.log(data);

                setCategoryExpenses(
                    data.categoryExpenses
                );

            } catch (error) {

                console.log(error);

                setError(
                    "集計の取得に失敗しました"
                );

            }

        };

        fetchSummary();

    }, []);

    return (

        <div>

            <h1>
                カテゴリ別支出
            </h1>

            {error && (
                <p>{error}</p>
            )}

            {categoryExpenses.length === 0 ? (

                <p>
                    データがありません
                </p>

            ) : (

                <>

                    <div
                        style={{
                            width: "100%",
                            height: 400,
                        }}
                    >

                        <ResponsiveContainer>

                            <PieChart>

                                <Pie
                                    data={categoryExpenses}
                                    dataKey="amount"
                                    nameKey="categoryName"
                                    cx="50%"
                                    cy="50%"
                                    outerRadius={140}
                                    label
                                >

                                    {categoryExpenses.map(
                                        (entry, index) => (

                                            <Cell
                                                key={entry.categoryName}
                                                fill={
                                                    COLORS[
                                                    index % COLORS.length
                                                    ]
                                                }
                                            />

                                        )
                                    )}

                                </Pie>

                                <Tooltip />

                                <Legend />

                            </PieChart>

                        </ResponsiveContainer>

                    </div>

                    <table>

                        <thead>

                            <tr>

                                <th>
                                    カテゴリ
                                </th>

                                <th>
                                    支出
                                </th>

                            </tr>

                        </thead>

                        <tbody>

                            {categoryExpenses.map(category => (

                                <tr
                                    key={category.categoryName}
                                >

                                    <td>
                                        {category.categoryName}
                                    </td>

                                    <td>
                                        {category.amount.toLocaleString()} 円
                                    </td>

                                </tr>

                            ))}

                        </tbody>

                    </table>

                </>

            )}

        </div>

    );

}

export default SummaryPage;