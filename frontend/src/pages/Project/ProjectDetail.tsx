import { useParams } from "react-router-dom"
import demoProject from '@/assets/images/demo-project-image.png'
import { useEffect, useState } from "react";
import { IProject } from "@/types";
import DefaultLayout from "@/layouts/DefaultLayout";



const ProjectDetail = () => {

    const [projectDetail, setProjectDetail] = useState<IProject | null>(null);

    const { id } = useParams();
    const getProjectDetail = async () => {
        console.log(id);
    }

    useEffect(() => {
        getProjectDetail();
    }, [])

    return (
        <DefaultLayout>
            <div className="bg-slate-100 py-12 lg:px-32 px-8 flex flex-col items-center">
                <div className="text-2xl font-semibold text-gray-900 bg-white p-4 w-full">
                    Project Detail
                </div>

                <img src={demoProject} className="mt-4 w-full" />

                <div className="bg-white w-full lg:px-20 px-6 sm:text-base text-sm py-4 grid grid-cols-3">
                    <div className="col-span-3 text-white bg-purple-600 font-semibold px-2 py-3 rounded-lg">
                        Project Tittle
                    </div>

                    <div className="bg-slate-100 text-gray-900 col-span-1 px-2 py-3 font-semibold mt-1">
                        Project Description
                    </div>
                    <div className="col-span-2 text-gray-900 px-2 py-3">
                        This is Sample Description For Demo Project
                    </div>

                    <div className="bg-slate-100 text-gray-900 col-span-1 px-2 py-3 font-semibold mt-1">
                        Status
                    </div>
                    <div className="col-span-2 text-gray-900 px-2 py-3">
                        PENDING
                    </div>

                    <div className="bg-slate-100 text-gray-900 col-span-1 px-2 py-3 font-semibold mt-1">
                        Planned Due Date
                    </div>
                    <div className="col-span-2 text-gray-900 px-2 py-3">
                        01-01-2025
                    </div>

                    <div className="bg-slate-100 text-gray-900 col-span-1 px-2 py-3 font-semibold mt-1">
                        Actual Due Date
                    </div>
                    <div className="col-span-2 text-gray-900 px-2 py-3">
                        01-01-2025
                    </div>


                </div>

                <div className="w-full py-4 bg-white flex items-center justify-center gap-4">
                    <button className="rounded-lg px-2 py-3 bg-emerald-700 text-white font-semibold hover:bg-emerald-500">
                        COMPLETED
                    </button>
                    <button className="rounded-lg px-2 py-3 bg-red-700 text-white font-semibold hover:bg-red-500">
                        CANCELED
                    </button>
                </div>



            </div>
        </DefaultLayout>
    )
}

export default ProjectDetail
