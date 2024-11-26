import { CreateProject } from "@/pages/Project";
import { IApiResponse, ICreateProjectRequest, IProject } from "@/types";
import { axiosClient } from "@/utils";

export const projectApi = {
    getProjectByUserId(userId?: string): Promise<IApiResponse<any>> {
        return axiosClient.get(`/projects/search`, {
            params: {
                userId,
            }
        })
    },

    getProjectDetailById(projectId: string): Promise<IApiResponse<IProject>> {
        return axiosClient.get(`/projects/${projectId}`)
    },

    CreateProject(data: ICreateProjectRequest): Promise<IApiResponse<IProject>> {
        return axiosClient.post("/projects", data)
    }
}