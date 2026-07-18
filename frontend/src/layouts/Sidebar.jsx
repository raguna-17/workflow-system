import { Link } from "react-router-dom";

const Sidebar = () => {

    const menu = [
        {
            name: "取引一覧",
            path: "/transactions"
        },
        {
            name: "カテゴリ管理",
            path: "/categories"
        },
        {
            name: "収支分析",
            path: "/summary"
        }
    ];

    return (
        <aside className="sidebar">

            <h2 className="sidebar-title">
                家計簿
            </h2>

            <nav className="sidebar-nav">

                {menu.map(item => (

                    <Link
                        key={item.path}
                        to={item.path}
                        className="sidebar-link"
                    >
                        {item.name}
                    </Link>

                ))}

            </nav>

        </aside>
    );
};

export default Sidebar;