export interface ITask {
    taskId: number;
    taskName: string;
    description: string;
    status: string;
    priority: string;
    plannedDueTime: string;
    actualDueTime: string;
    projectId: number;
}


export interface ICreateTaskRequest {
    projectId: number;
    taskName: string;
    description?: string;
    priority: string;
    plannedDueTime: Date;
}

export interface IUpdateTaskRequest {
    taskName?: string;
    description?: string;
    priority?: string;
    status?: string
    plannedDueTime?: Date;
    actualDueTime?: Date;
}