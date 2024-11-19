import { IApiResponse } from "@/types";
import { axiosClient } from "@/utils";

interface IUploadResponse {
    downloadUrl: string
}

export const uploadApi = {
    upload(data: any): Promise<IApiResponse<any>> {
        return axiosClient.post("/upload/", data,
            {
                headers: {
                    "Content-Type": 'multipart/form-data',
                }
            }
        );
    }
}