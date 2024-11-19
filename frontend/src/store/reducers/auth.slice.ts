import { IUserInfo } from '@/types';
import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RootState } from '../store';

interface IAuthState {
    userInfo?: IUserInfo | null,
}

const initialState: IAuthState = {
    userInfo: null,
}

const authSlice = createSlice({
    name: 'auth',
    initialState: initialState,
    reducers: {
        setUserInfo: (state: IAuthState, action: PayloadAction<IUserInfo | null>) => {
            state.userInfo = action.payload;
        }
    }
})

export const userInfoSelector = (state: RootState) => state.authReducer.userInfo

export const { setUserInfo } = authSlice.actions;
export const authReducer = authSlice.reducer;