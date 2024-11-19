import { COOKIE_KEY } from "@/constants/common";
import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";
import { Cookies } from "react-cookie";


export const axiosClient = axios.create({
    baseURL: `http://localhost:8080/api`,
    timeout: 30000,
    withCredentials: true,
})

const interceptorRequest = (config: AxiosRequestConfig) => {
    const cookies = new Cookies();
    const accessToken = cookies.get(COOKIE_KEY.ACCESS_TOKEN)


    //Set Authorization Header with Access Token
    if (accessToken) {

        const headers = {
            ...config.headers,
            Authorization: "Bearer " + accessToken,
        }

        config.headers = headers;
    }

    return config;
}

const interceptorResponse = (response: AxiosResponse) => {
    if (response && response.data) {
        return {
            ...response.data,
            status: response.status
        }
    }
    return response;
}

const interceptorError = (error: AxiosError) => {
    console.log(error)
    return Promise.reject(error);
}

axiosClient.interceptors.request.use(interceptorRequest as any, interceptorError)
axiosClient.interceptors.response.use(interceptorResponse, interceptorError);