import { DateInput, SelectInput, TextInput } from "@/components"
import { useProject } from "@/hooks/useProject";
import DefaultLayout from "@/layouts/DefaultLayout"
import { taskApi } from "@/services/task.service";
import { ICreateTaskRequest } from "@/types";
import { yupResolver } from "@hookform/resolvers/yup";
import { error } from "console";
import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";
import * as yup from 'yup'

const CreateTaskSchema = yup.object().shape({
    taskName: yup.string().required("Task Name is Required"),
    description: yup.string(),
    plannedDueTime: yup.date().required("Planned Due Time is Required"),
    priority: yup.string().required().notOneOf([""], "Priority is required")
})


interface ICreateTaskForm {
    taskName: string;
    description?: string;
    priority: string;
    plannedDueTime: Date;
}

const CreateTask = () => {

    const { id } = useParams();
    const navigate = useNavigate();

    const { register, handleSubmit, reset } = useForm({
        resolver: yupResolver(CreateTaskSchema)
    })




    const onSubmitHandler = async (data: ICreateTaskForm) => {
        const request: ICreateTaskRequest = {
            ...data,
            projectId: Number(id),
        }

        await taskApi.createTask(request).then(
            (response) => {
                if (response && response.data) {
                    toast.success("Task Created");
                    navigate(`/projects/${id}`)
                }
            }
        ).catch(
            () => {
                reset();
                toast.error("Create Task Failed")
            }
        )
    }
    return (
        <DefaultLayout>
            <div className="bg-slate-100 py-12 lg:px-32 px-8 flex flex-col items-center">
                <div className="bg-white p-4 w-full lg:px-20 px-6">
                    <h1 className="text-2xl text-gray-900 font-semibold">
                        Create Task
                    </h1>

                    <form className="mt-8 space-y-4 w-full" onSubmit={handleSubmit(onSubmitHandler)}>
                        <TextInput name="taskName" placeholder="Enter Your Task Name" type="text" label="Task Name" register={register("taskName")} />

                        <DateInput name="plannedDueTime" placeholder="Planned Due Date" label="Planned Due Date" register={register("plannedDueTime")} />
                        {/* <DateInput name="actualDueTime" placeholder="Actual Due Time" label="Actual Due Date" /> */}

                        <SelectInput name="priority" label="Priority" register={register("priority")}>
                            <option selected value="">Select Priority</option>
                            <option value="HIGH">High</option>
                            <option value="MEDIUM">Medium</option>
                            <option value="LOW">Low</option>
                        </SelectInput>

                        <div>
                            <label htmlFor="description"
                                className="block font-medium text-sm text-gray-900 ml-1 mb-1">
                                Description
                            </label>

                            <textarea {...register("description")}
                                className="w-full bg-gray-50 border border-gray-300 text-gray-900 rounded-lg p-2 outline-none focus:border-indigo-700 pe-16" />
                        </div>


                        <button
                            className="text-white rounded-lg font-semibold bg-indigo-900 hover:bg-indigo-700 py-2 px-3" type="submit">
                            CREATE TASK
                        </button>
                    </form>
                </div>
            </div>

        </DefaultLayout>
    )
}

export default CreateTask
