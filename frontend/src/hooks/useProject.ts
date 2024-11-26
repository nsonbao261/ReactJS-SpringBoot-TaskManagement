import { projectApi } from "@/services";
import { userInfoSelector } from "@/store/reducers";
import { projectsSelector, setProjects } from "@/store/reducers/project.slice"
import { useDispatch, useSelector } from "react-redux"

export const useProject = () => {
    const projects = useSelector(projectsSelector);
    const userInfo = useSelector(userInfoSelector);
    const dispatch = useDispatch();

    const getProjectByUserId = async () => {
        console.log(userInfo)
        await projectApi.getProjectByUserId(userInfo?.userId).
            then((response) => {
                if (response && response.data) {
                    dispatch(setProjects(response.data.content))
                }
                console.log(response.data)
            }).catch(error => {
                console.log(error);
                dispatch(setProjects(null));
            })
    }

    return {
        projects,
        getProjectByUserId,
    }
}