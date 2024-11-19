import { COOKIE_KEY } from "@/constants/common";
import { userInfoSelector, setUserInfo } from "@/store/reducers";
import { useCookies } from "react-cookie";
import { useDispatch, useSelector } from "react-redux"
import { useNavigate } from "react-router-dom";

export const useAuth = () => {
    const userInfo = useSelector(userInfoSelector);
    const [cookies, setCookies, removeCookies] = useCookies([COOKIE_KEY.ACCESS_TOKEN]);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const logout = () => {
        //Remove accessToken from Cokkies;
        if (cookies.accessToken) {
            removeCookies(COOKIE_KEY.ACCESS_TOKEN);
        }
        //Set userInfo to null;
        if (userInfo) {
            dispatch(setUserInfo(null));
        }
        //Navigate
        navigate("/");
    }


    return {
        userInfo,
        logout,
    };
}