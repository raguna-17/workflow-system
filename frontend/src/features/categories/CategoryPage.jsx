import { useEffect, useState } from "react";

import { getCategories } from "./api";

function CategoryPage() {

    const [categories, setCategories] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {

        const fetchCategories = async () => {

            try {

                const data = await getCategories();

                setCategories(data);

            } catch (err) {

                setError("カテゴリの取得に失敗しました");

            }

        };

        fetchCategories();

    }, []);

    return (

        <div>

            <h1>カテゴリ一覧</h1>

            {error && <p>{error}</p>}

            <table>

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>カテゴリ名</th>
                        <th>種類</th>
                    </tr>
                </thead>

                <tbody>

                    {categories.map(category => (

                        <tr key={category.id}>

                            <td>{category.id}</td>

                            <td>{category.name}</td>

                            <td>
                                {category.type === "EXPENSE"
                                    ? "支出"
                                    : "収入"}
                            </td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </div>

    );

}

export default CategoryPage;