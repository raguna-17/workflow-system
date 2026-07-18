import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Layout from "./layouts/Layout";

import LoginPage from "./features/auth/LoginPage";
import RegisterPage from "./features/auth/RegisterPage";

import TransactionPage from "./features/transactions/TransactionPage";
import CategoryPage from "./features/categories/CategoryPage";
import SummaryPage from "./features/summary/SummaryPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* 認証不要ページ */}
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        {/* ログイン後ページ */}
        <Route path="/" element={<Layout />}>
          <Route index element={<Navigate to="/transactions" />} />

          <Route path="transactions" element={<TransactionPage />} />
          <Route path="categories" element={<CategoryPage />} />
          <Route path="summary" element={<SummaryPage />} />
        </Route>

      </Routes>
    </BrowserRouter>
  );
}

export default App;