export interface IProject {
    projectId: number;
    projectName: string;
    description?: string;
    plannedDueTime: string;
    actualDueTime?: string;
    status: string;
}


export interface ICreateProjectRequest {
    projectName: string;
    description?: string;
    plannedDueTime: Date;
    userId?: string;
}