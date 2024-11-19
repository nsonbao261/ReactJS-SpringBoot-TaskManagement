export interface IApiResponse<T> {
    data?: T,
    message?: string
    errors?: boolean
}