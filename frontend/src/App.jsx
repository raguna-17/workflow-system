import { BrowserRouter, Routes, Route } from "react-router-dom";

import Layout from "./layouts/Layout";
import TodoListPage from "./features/todos/TodoListPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<TodoListPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;