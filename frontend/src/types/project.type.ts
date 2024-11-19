export interface IProject {
    projectId: number;
    projectName: string;
    description?: string;
    plannedDueTime: string;
    actualDueTime?: string;
    status: string;
}