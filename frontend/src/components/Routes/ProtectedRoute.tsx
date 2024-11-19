import { COOKIE_KEY } from "@/constants/common"
import { ReactNode } from "react";
import { useCookies } from "react-cookie"
import { Link } from "react-router-dom";

const ProtectedRoute = ({ children }: { children: ReactNode }) => {

    const [cookies, _] = useCookies([COOKIE_KEY.ACCESS_TOKEN]);
    return (
        <div></div>
    )
}

export default ProtectedRoute
