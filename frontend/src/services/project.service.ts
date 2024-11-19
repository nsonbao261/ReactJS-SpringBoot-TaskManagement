import { IApiResponse, IProject } from "@/types";
import { axiosClient } from "@/utils";

export const projectService = {
    getProjectByUserId(userId?: string): Promise<IApiResponse<any>> {
        return axiosClient.get(`/projects/search`, {
            params: {
                userId,
            }
        })
    },

    getProjectDetailById(projectId: number): Promise<IApiResponse<IProject>> {
        return axiosClient.get(`/projects/${projectId}`)
    }
}