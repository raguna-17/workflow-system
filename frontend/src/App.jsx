import { BrowserRouter, Routes, Route } from "react-router-dom";

import Layout from "./layouts/Layout";
import TodoListPage from "./features/todos/TodoListPage";
import LoginPage from "./features/auth/LoginPage";
import RegisterPage from "./features/auth/RegisterPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* 認証不要ページ */}
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />


        {/* ログイン後ページ */}
        <Route path="/" element={<Layout />}>
          <Route index element={<TodoListPage />} />
        </Route>

      </Routes>
    </BrowserRouter>
  );
}

export default App;