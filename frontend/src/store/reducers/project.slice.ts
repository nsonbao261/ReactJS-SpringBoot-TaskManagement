import { IProject } from "@/types";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { RootState } from "../store";

interface IProjectState {
    projects: IProject[] | null
}

const initialState: IProjectState = {
    projects: null
}

const projectSlice = createSlice({
    name: 'project',
    initialState: initialState,
    reducers: {
        setProjects: (state: IProjectState, action: PayloadAction<IProject[] | null>) => {
            state.projects = action.payload
        }
    }
});


export const projectsSelector = (state: RootState) => state.projectReducer.projects;

export const projectReducer = projectSlice.reducer;
export const { setProjects } = projectSlice.actions;