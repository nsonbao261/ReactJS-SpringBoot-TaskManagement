import { configureStore } from "@reduxjs/toolkit";
import { authReducer } from "./reducers";
import { projectReducer } from "./reducers/project.slice";

export const store = configureStore({
    reducer: {
        authReducer,
        projectReducer
    }
})

export type RootState = ReturnType<typeof store.getState>