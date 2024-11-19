import { DateInput, TextInput } from "@/components"
import DefaultLayout from "@/layouts/DefaultLayout"
import ImageHolder from "@/assets/images/image-holder.png";

const CreateProject = () => {
    return (
        <DefaultLayout>
            <div className="bg-slate-100 py-12 lg:px-32 px-8 flex flex-col items-center">
                <div className="bg-white p-4 w-full lg:px-20 px-6">
                    <h1 className="text-2xl text-gray-900 font-semibold">
                        Create Project
                    </h1>

                    <form className="mt-8 space-y-4 w-full">

                        <div className="flex flex-col items-center">
                            <img className="object-cover h-96"
                                src={ImageHolder} />

                            <label htmlFor="Project Image" className='text-gray-900 font-semibold hover:text-purple-900 cursor-pointer'>
                                Project Image
                            </label>

                            <input type="file" hidden accept='image/*' id="projectImage" name="projectImage" />
                        </div>
                        <TextInput name="projectName" placeholder="Enter Your Project Name" type="text" label="Project Name" />

                        <DateInput name="planneDueDate" placeholder="Planned Due Date" label="Planned Due Date" />
                        <DateInput name="actualDueTime" placeholder="Actual Due Time" label="Actual Due Date" />

                        <div>
                            <label htmlFor="description"
                                className="block font-medium text-sm text-gray-900 ml-1 mb-1">
                                Description
                            </label>

                            <textarea
                                className="w-full bg-gray-50 border border-gray-300 text-gray-900 rounded-lg p-2 outline-none focus:border-indigo-700 pe-16" />
                        </div>


                        <button
                            className="text-white rounded-lg font-semibold bg-indigo-900 hover:bg-indigo-700 py-2 px-3">
                            CREATE PROJECT
                        </button>
                    </form>
                </div>
            </div>

        </DefaultLayout>
    )
}

export default CreateProject
