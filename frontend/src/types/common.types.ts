export interface IApiResponse<T> {
    data?: T,
    message?: string
    errors?: boolean
}

export interface PageResponse<T> {
    content: T[];
    pageNumber: number;
    pageSize: number;
    totalElement: number;
    totalPage: number;
}