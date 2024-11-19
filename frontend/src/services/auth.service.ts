import { IApiResponse, IChangePassswordRequest, ILoginRequest, ILoginResponse, IRegisterRequest, IRegisterResponse, IUserInfo, IUserUpdateRequest } from "@/types";
import { axiosClient } from "@/utils";

export const authApi = {
    login(data: ILoginRequest): Promise<IApiResponse<ILoginResponse>> {
        return axiosClient.post("/auth/login", data);
    },

    changePassword(data: IChangePassswordRequest): Promise<IApiResponse<any>> {
        return axiosClient.post("/auth/change-password", data);
    },

    register(data: IRegisterRequest): Promise<IApiResponse<IRegisterResponse>> {
        return axiosClient.post("/auth/register", data);
    },

    updateUser(data: IUserUpdateRequest, userId: string): Promise<IApiResponse<IUserInfo>> {
        return axiosClient.put(`/users/${userId}`, data)
    },

    getUserInfo(userId: string): Promise<IApiResponse<IUserInfo>> {
        return axiosClient.get(`/users/${userId}`)
    }

}