import "./global.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { CreateTask, HomePage, LoginPage, ProfilePage, SignupPage, StudentDashboard, TaskDetail } from "@/pages";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import path from "path";
import { CreateProject, ProjectDetail } from "./pages/Project";
import { ProtectedRoute } from "./components";

function App() {

  const ROUTE_LIST = [
    {
      path: "/login",
      isProtected: false,
      element: <LoginPage />
    },
    {
      path: "/register",
      isProtected: false,
      element: <SignupPage />
    },
    {
      path: "/",
      isProtected: false,
      element: <HomePage />
    },
    {
      path: "/student/dashboard",
      isProtected: true,
      element: <StudentDashboard />
    },
    {
      path: "/profile",
      isProtected: true,
      element: <ProfilePage />
    },
    {
      path: "/projects/:id",
      isProtected: true,
      element: <ProjectDetail />
    },
    {
      path: "/projects/create",
      isProtected: true,
      element: <CreateProject />
    },
    {
      path: "/tasks/:id",
      isProtected: true,
      element: <TaskDetail />
    },
    {
      path: "/projects/:id/add-task",
      isProtected: true,
      element: <CreateTask />
    }
  ]

  return (
    <>
      <ToastContainer position="top-right" autoClose={1000} closeOnClick={true} />
      <BrowserRouter>
        <Routes>
          {
            ROUTE_LIST.map(route =>
              <Route
                path={route.path}
                element={route.isProtected
                  ? <ProtectedRoute>{route.element}</ProtectedRoute>
                  : route.element} />)
          }
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
