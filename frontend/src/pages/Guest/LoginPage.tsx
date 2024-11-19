import { Link, useNavigate } from "react-router-dom";
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from "@hookform/resolvers/yup";
import { useCookies } from "react-cookie";
import { COOKIE_KEY } from "@/constants/common";
import { useState } from "react";
import { PasswordInput, TextInput } from "@/components";
import { authApi } from "@/services/auth.service";
import { useDispatch } from "react-redux";
import { setUserInfo } from "@/store/reducers";
import { toast } from "react-toastify";


const LoginSchema = yup.object().shape({
    email: yup.string().required("Email is required").email("Email is inapproriate"),
    password: yup.string().required("Password is required").min(8, "Password has minimum of 8 characters")
});

interface ILoginFormData {
    email: string,
    password: string,
}

const LoginPage = () => {

    const [loading, setLoading] = useState(false);
    const [cookies, setCookies] = useCookies([COOKIE_KEY.ACCESS_TOKEN]);
    const navigate = useNavigate();
    const dispatch = useDispatch();


    const { register, handleSubmit, formState: { errors }, reset } = useForm({
        resolver: yupResolver(LoginSchema)
    })

    const onSubmitHandler = async (data: ILoginFormData) => {
        setLoading(true);

        try {
            const response = await authApi.login(data);

            if (response.data) {
                const accessToken = response.data.accessToken;
                const userInfo = response.data.user;

                //Save accessToken to cookie;
                setCookies(COOKIE_KEY.ACCESS_TOKEN, accessToken);

                //Save UserInfo to redux state;
                dispatch(setUserInfo(userInfo));

                //Navigate to Profile
                navigate("/profile");
            }

        } catch (error) {
            toast.error("Login Failed");
        }

        setLoading(false);
    }

    return (
        <div className="flex flex-col items-center bg-gradient-to-r from-emerald-500 to-indigo-500 min-h-screen min-w-screen py-12">
            <div className="flex flex-col justify-center items-center px-12 py-6 bg-white rounded-lg min-w-[80%] lg:min-w-[35%]">
                <h1 className="font-semibold text-xl text-gray-900">
                    LOGIN TO YOUR ACCOUNT
                </h1>

                <form onSubmit={handleSubmit(onSubmitHandler)} className="space-y-4 w-full mt-8">


                    <TextInput name="email" label="Email" type="email" register={register("email")} placeholder="Enter Your Email" errorMessage={errors.email?.message} />
                    < PasswordInput name="password" register={register("password")} label="Password"
                        errorMessage={errors.password?.message} />

                    <button
                        className="text-white rounded-lg font-semibold bg-indigo-900 hover:bg-indigo-700 py-2 w-full">
                        LOGIN
                    </button>


                </form>

                <p className="text-sm text-gray-500 font-light mt-4 self-end mr-2">
                    Don't have an account yet?
                    <Link to="/register"
                        className="font-semibold text-gray-900 hover:underline hover:text-indigo-900 pl-2">
                        Sign Up
                    </Link>
                </p>
            </div>
        </div>
    );
}

export default LoginPage;