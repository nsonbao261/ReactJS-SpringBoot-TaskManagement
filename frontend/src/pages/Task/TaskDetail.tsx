import DefaultLayout from "@/layouts/DefaultLayout";
import { taskApi } from "@/services/task.service";
import { ITask } from "@/types";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom"
import { toast } from "react-toastify";

const TaskDetail = () => {

    const [task, setTask] = useState<ITask | null>(null);
    const { id } = useParams();

    const getTaskDetail = async () => {
        await taskApi.getTaskById(id).then(
            (response) => {
                if (response && response.data)
                    setTask(response.data);
            }
        ).catch(
            (error) => {
                toast.error("Failed to load Task Detail");
            }
        )
    }

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

    useEffect(() => {
        getTaskDetail();
    }, [])

    return (
        <DefaultLayout>
            <div className="bg-slate-100 py-8 lg:px-16 px-8 flex flex-col items-center">
                <div className="text-2xl font-semibold text-gray-900 bg-white p-4 w-full flex items-center justify-between">
                    Task Detail
                </div>


                {
                    task &&
                    <div className="bg-white w-full lg:px-20 px-6 sm:text-base text-sm py-4 grid grid-cols-3">
                        <div className="col-span-3 text-white bg-purple-600 font-semibold px-2 py-3 rounded-lg">
                            {task.taskName}
                        </div>

                        <div className="bg-slate-100 text-gray-900 col-span-1 px-2 py-3 font-semibold mt-1">
                            Task Description
                        </div>
                        <div className="col-span-2 text-gray-900 px-2 py-3">
                            {task?.description}
                        </div>

                        <div className="bg-slate-100 text-gray-900 col-span-1 px-2 py-3 font-semibold mt-1">
                            Status
                        </div>
                        <div className="col-span-2 text-gray-900 px-2 py-3">
                            <span className={STATUS_BACKGROUND_COLOR[task.status] +
                                " font-semibold text-white mt-2 py-1 px-2 rounded-lg w-fit"} >
                                {task.status}
                            </span>
                        </div>

                        <div className="bg-slate-100 text-gray-900 col-span-1 px-2 py-3 font-semibold mt-1">
                            Priority
                        </div>
                        <div className="col-span-2 text-gray-900 px-2 py-3">
                            <span className={PRIORITY_BACKGROUND_COLOR[task.priority] +
                                " font-semibold text-white mt-2 py-1 px-2 rounded-lg w-fit"} >
                                {task.priority}
                            </span>
                        </div>

                        <div className="bg-slate-100 text-gray-900 col-span-1 px-2 py-3 font-semibold mt-1">
                            Planned Due Date
                        </div>
                        <div className="col-span-2 text-gray-900 px-2 py-3">
                            {task?.plannedDueTime}
                        </div>

                        <div className="bg-slate-100 text-gray-900 col-span-1 px-2 py-3 font-semibold mt-1">
                            Actual Due Date
                        </div>
                        <div className="col-span-2 text-gray-900 px-2 py-3">
                            {task?.actualDueTime}
                        </div>
                    </div>
                }

                <div className="w-full flex items-center justify-center gap-8 bg-white py-4">
                    <button className="rounded-lg px-2 py-3 bg-emerald-700 text-white font-semibold hover:bg-emerald-500">
                        COMPLETE
                    </button>
                    <button className="rounded-lg px-2 py-3 bg-red-700 text-white font-semibold hover:bg-red-500">
                        CANCEL
                    </button>
                </div>
            </div>
        </DefaultLayout>
    )
}

export default TaskDetail
