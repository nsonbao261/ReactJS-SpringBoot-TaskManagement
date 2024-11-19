import "./global.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { HomePage, LoginPage, ProfilePage, SignupPage, StudentDashboard } from "@/pages";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import path from "path";
import { CreateProject, ProjectDetail } from "./pages/Project";

function App() {

  const ROUTE_LIST = [
    {
      path: "/login",
      element: <LoginPage />
    },
    {
      path: "/register",
      element: <SignupPage />
    },
    {
      path: "/",
      element: <HomePage />
    },
    {
      path: "/student/dashboard",
      element: <StudentDashboard />
    },
    {
      path: "/profile",
      element: <ProfilePage />
    },
    {
      path: "projects/:id",
      element: <ProjectDetail />
    },
    {
      path: "projects/create",
      element: <CreateProject />
    }
  ]

  return (
    <>
      <ToastContainer position="top-right" autoClose={1000} closeOnClick={true} />
      <BrowserRouter>
        <Routes>
          {
            ROUTE_LIST.map(route => <Route path={route.path} element={route.element} />)
          }
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
