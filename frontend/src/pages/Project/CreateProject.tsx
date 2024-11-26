import { DateInput, TextInput } from "@/components"
import DefaultLayout from "@/layouts/DefaultLayout"
import ImageHolder from "@/assets/images/image-holder.png";
import * as yup from 'yup';
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import { ICreateProjectRequest } from "@/types";
import { useAuth } from "@/hooks";
import { projectApi } from "@/services";
import { toast } from "react-toastify";
import { Navigate, useNavigate } from "react-router-dom";


const CreateProjectSchema = yup.object().shape({
    projectName: yup.string().required("Project Name is Required"),
    description: yup.string(),
    plannedDueTime: yup.date().required("Planned Due Time is Required"),
})


interface ICreateProjectForm {
    projectName: string;
    description?: string;
    plannedDueTime: Date;
}

const CreateProject = () => {

    const navigate = useNavigate();

    const { register, handleSubmit, reset, formState: { errors } } = useForm({
        resolver: yupResolver(CreateProjectSchema),
    })

    const { userInfo } = useAuth();

    const handleCreateProject = async (data: ICreateProjectForm) => {
        const { projectName, description, plannedDueTime } = data

        await projectApi.CreateProject({
            projectName,
            description,
            plannedDueTime,
            userId: userInfo?.userId
        }).then((response) => {
            if (response.data) {
                toast.success("Project Created");
                navigate("/student/dashboard");
            }
        }).catch((error) => {
            toast.error("Create Project Failed");
        })
    }

    return (
        <DefaultLayout>
            <div className="bg-slate-100 py-12 lg:px-32 px-8 flex flex-col items-center">
                <div className="bg-white p-4 w-full lg:px-20 px-6">
                    <h1 className="text-2xl text-gray-900 font-semibold">
                        Create Project
                    </h1>

                    <form className="mt-8 space-y-4 w-full" onSubmit={handleSubmit(handleCreateProject)}>

                        <div className="flex flex-col items-center">
                            <img className="object-cover h-96"
                                src={ImageHolder} />

                            <label htmlFor="projectImage" className='text-gray-900 font-semibold hover:text-purple-900 cursor-pointer'>
                                Project Image
                            </label>

                            <input type="file" hidden accept='image/*' id="projectImage" name="projectImage" />
                        </div>
                        <TextInput name="projectName" placeholder="Enter Your Project Name" type="text" label="Project Name" register={register("projectName")} errorMessage={errors.projectName?.message} />

                        <DateInput name="plannedDueTime" placeholder="Planned Due Date" label="Planned Due Date" register={register("plannedDueTime")} errorMessage={errors.plannedDueTime?.message} />
                        {/* <DateInput name="actualDueTime" placeholder="Actual Due Time" label="Actual Due Date" /> */}

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
                            CREATE PROJECT
                        </button>
                    </form>
                </div>
            </div>

        </DefaultLayout>
    )
}

export default CreateProject
