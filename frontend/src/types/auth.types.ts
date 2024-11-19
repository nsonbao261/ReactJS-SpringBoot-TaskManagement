export interface ILoginRequest {
    email: string,
    password: string
}


export interface ILoginResponse {
    user: IUserInfo;
    accessToken: string;
}

export interface IRegisterRequest {
    email: string;
    password: string;
    firstName?: string;
    lastName?: string;
    gender?: string;
    birthday?: Date;
    avatarUrl?: string | null;
    role?: string;
}

export interface IRegisterResponse {
    userId: string;
    email: string;
    firstName?: string;
    lastName?: string;
    gender?: string;
    birthday?: string;
    avatarUrl?: string;
    role?: string
}



export interface IUserInfo {
    userId: string;
    email: string;
    firstName: string;
    lastName: string;
    gender: string;
    birthday?: string;
    avatarUrl?: string;
    role?: string
}


export interface IChangePassswordRequest {
    userId: string;
    currentPassword: string;
    newPassword: string;
}

export interface IUserUpdateRequest {
    firstName?: string;
    lastName?: string;
    gender?: string;
}