import { Link } from "react-router-dom";

const Sidebar = () => {

    const menu = [
        {
            name: "ダッシュボード",
            path: "/dashboard"
        },
        {
            name: "ワークフロー管理",
            path: "/workflows"
        },
        {
            name: "申請一覧",
            path: "/requests"
        },
        {
            name: "承認管理",
            path: "/approvals"
        },
        {
            name: "ユーザー管理",
            path: "/users"
        }
    ];

    return (
        <aside className="sidebar">
            <h2 className="sidebar-title">
                workflow-system
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