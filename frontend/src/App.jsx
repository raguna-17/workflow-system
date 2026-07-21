import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Layout from "./layouts/Layout";

import LoginPage from "./features/auth/LoginPage";
import RegisterPage from "./features/auth/RegisterPage";

import DashboardPage from "./features/dashboard/DashboardPage";

import RequestPage from "./features/request/RequestPage";
import ApprovalPage from "./features/approval/ApprovalPage";
import WorkflowPage from "./features/workflow/WorkflowPage";
import UserPage from "./features/user/UserPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* 認証不要 */}
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        {/* 認証後 */}
        <Route path="/" element={<Layout />}>

          {/* 初期ページ */}
          <Route index element={<Navigate to="/dashboard" replace />} />

          <Route path="dashboard" element={<DashboardPage />} />

          <Route path="requests" element={<RequestPage />} />

          <Route path="approvals" element={<ApprovalPage />} />

          <Route
            path="/approvals/:id"
            element={<ApprovalDetail />}
          />

          <Route path="workflows" element={<WorkflowPage />} />

          <Route
            path="workflows/:id"
            element={<WorkflowDetail />}
          />

          <Route path="users" element={<UserPage />} />

        </Route>

      </Routes>
    </BrowserRouter>
  );
}

export default App;