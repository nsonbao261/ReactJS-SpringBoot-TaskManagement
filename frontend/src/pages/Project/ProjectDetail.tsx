import { Link, useParams } from "react-router-dom"
import demoProject from '@/assets/images/demo-project-image.png'
import { useEffect, useState } from "react";
import { IProject, ITask } from "@/types";
import DefaultLayout from "@/layouts/DefaultLayout";
import { projectApi } from "@/services";
import { toast } from "react-toastify";
import { taskApi } from "@/services/task.service";



const ProjectDetail = () => {

    const [projectDetail, setProjectDetail] = useState<IProject | null>(null);
    const [tasks, setTasks] = useState<ITask[] | null>(null);

    const { id } = useParams();
    const getProjectDetail = async () => {
        if (id) {
            await projectApi.getProjectDetailById(id).then(
                (response) => {
                    if (response && response.data) {
                        setProjectDetail(response.data);
                    }
                }, (error) => {
                    toast.error("Load Project Failed");
                    setProjectDetail(null);
                }
            )
        }
    }

    const getTaskByProject = async () => {
        await taskApi.getTaskByProjectId(Number(id)).then(
            (response) => {
                if (response && response.data) {
                    setTasks(response.data.content);
                }
            }
        ).catch((error) => {
            toast.error("Load Task Failed");
        })
    }

    useEffect(() => {
        getProjectDetail();
    }, [])

    useEffect(() => {
        getTaskByProject();
    }, [])

    const STATUS_BACKGROUND_COLOR: Record<string, string> = {
        "PENDING": "bg-blue-700",
        "PROGRESSING": "bg-orange-700",
        "COMPLETED": "bg-emerald-700",
        "CANCELED": "bg-red-700",
    }

    const PRIORITY_BACKGROUND_COLOR: Record<string, string> = {
        "HIGH": "bg-red-700",
        "MEDIUM": "bg-orange-700",
        "LOW": "bg-emerald-700",
    }

    return (
        <DefaultLayout>
            <div className="bg-slate-100 py-8 lg:px-16 px-8 flex flex-col items-center">
                <div className="text-2xl font-semibold text-gray-900 bg-white p-4 w-full">
                    Project Detail
                </div>

                <img src={demoProject} className="mt-4 w-full" />

                {
                    projectDetail &&
                    <div className="bg-white w-full lg:px-20 px-6 sm:text-base text-sm py-4 grid grid-cols-3">
                        <div className="col-span-3 text-white bg-purple-600 font-semibold px-2 py-3 rounded-lg">
                            {projectDetail?.projectName}
                        </div>

                        <div className="bg-slate-100 text-gray-900 col-span-1 px-2 py-3 font-semibold mt-1">
                            Project Description
                        </div>
                        <div className="col-span-2 text-gray-900 px-2 py-3">
                            {projectDetail?.description}
                        </div>

                        <div className="bg-slate-100 text-gray-900 col-span-1 px-2 py-3 font-semibold mt-1">
                            Status
                        </div>
                        <div className="col-span-2 text-gray-900 px-2 py-3">
                            <span className={STATUS_BACKGROUND_COLOR[projectDetail.status] +
                                " font-semibold text-white mt-2 py-1 px-2 rounded-lg w-fit"} >
                                {projectDetail.status}
                            </span>
                        </div>

                        <div className="bg-slate-100 text-gray-900 col-span-1 px-2 py-3 font-semibold mt-1">
                            Planned Due Date
                        </div>
                        <div className="col-span-2 text-gray-900 px-2 py-3">
                            {projectDetail?.plannedDueTime}
                        </div>

                        <div className="bg-slate-100 text-gray-900 col-span-1 px-2 py-3 font-semibold mt-1">
                            Actual Due Date
                        </div>
                        <div className="col-span-2 text-gray-900 px-2 py-3">
                            {projectDetail?.actualDueTime}
                        </div>
                    </div>
                }

                <div className="w-full py-4 bg-white flex items-center justify-center gap-4">
                    <button className="rounded-lg px-2 py-3 bg-emerald-700 text-white font-semibold hover:bg-emerald-500">
                        COMPLETE
                    </button>
                    <button className="rounded-lg px-2 py-3 bg-red-700 text-white font-semibold hover:bg-red-500">
                        CANCEL
                    </button>
                </div>


                <div className="mt-4 bg-white flex flex-col items-center justify-center w-full py-4">
                    <div className="w-full flex items-center justify-end py-4 px-12">
                        <Link to={`/projects/${projectDetail?.projectId}/add-task`} className="rounded-lg p-2 bg-emerald-700 text-white font-semibold hover:bg-emerald-500 text-xs">
                            NEW TASK
                        </Link>
                    </div>
                    <table className="text-sm text-gray-900 w-full">
                        <thead className="bg-gray-100 border-gray-500 font-semibold">
                            <tr className="text-nowrap">
                                <th className="px-2 py-3">
                                    TASK NAME
                                </th>
                                <th className="px-2 py-3">
                                    DESCRIPTION
                                </th>
                                <th className="px-2 py-3">
                                    STATUS
                                </th>
                                <th className="px-2 py-3">
                                    PRIORITY
                                </th>
                                <th className="px-2 py-3">
                                    PLANNED DUE TIME
                                </th>
                                <th className="px-2 py-3">
                                    ACTUAL DUE TIME
                                </th>
                            </tr>
                        </thead>

                        <tbody>
                            {
                                tasks?.map(item =>
                                    <tr className="border-b">
                                        <td className="px-2 py-3 font-semibold">
                                            <Link to={`/tasks/${item.taskId}`}
                                                className="hover:text-purple-600 hover:underline">
                                                {item.taskName}
                                            </Link>
                                        </td>

                                        <td className="px-2 py-3">
                                            {item.description}
                                        </td>

                                        <td className="px-2 py-3 text-center">
                                            <span className={STATUS_BACKGROUND_COLOR[item.status] +
                                                " font-semibold text-white mt-2 py-1 px-2 rounded-lg w-fit"} >
                                                {item.status}
                                            </span>
                                        </td>

                                        <td className="px-2 py-3 text-center">
                                            <span className={PRIORITY_BACKGROUND_COLOR[item.priority] +
                                                " font-semibold text-white mt-2 py-1 px-2 rounded-lg w-fit"} >
                                                {item.priority}
                                            </span>
                                        </td>

                                        <td className="px-2 py-3 text-center">
                                            {item.plannedDueTime}
                                        </td>

                                        <td className="px-2 py-3 text-center">
                                            {item.actualDueTime}
                                        </td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>


            </div>
        </DefaultLayout>
    )
}

export default ProjectDetail
