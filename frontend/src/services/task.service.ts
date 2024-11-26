import { IApiResponse, ICreateTaskRequest, ITask, PageResponse } from "@/types";
import { axiosClient } from "@/utils";

export const taskApi = {
    getTaskByProjectId(
        projectId: number,
        pageNumber: number = 0,
        pageSize: number = 12,): Promise<IApiResponse<PageResponse<ITask>>> {
        return axiosClient.get("/tasks/search", {
            params: {
                projectId,
                pageNumber,
                pageSize
            }
        })
    },

    getTaskById(taskId?: string): Promise<IApiResponse<ITask>> {
        return axiosClient.get(`/tasks/${taskId}`);
    },

    createTask(data: ICreateTaskRequest): Promise<IApiResponse<ITask>> {
        return axiosClient.post("/tasks", data)
    }
}