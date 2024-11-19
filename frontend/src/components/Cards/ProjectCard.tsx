import demoProject from '@/assets/images/demo-project-image.png'
import { FaRegEdit, FaRegTrashAlt } from "react-icons/fa";
import { Link } from 'react-router-dom';



interface IProjectCardProps {
    projectTittle: string,
    description?: string,
    projectImage?: string,
    plannedDueTime: string,
    status: string,
    projectId: number,
}


const STATUS_BACKGROUND_COLOR: Record<string, string> = {
    "PENDING": "bg-blue-700",
    "PROGRESSING": "bg-orange-700",
    "COMPLETED": "bg-emerald-700",
    "CANCELED": "bg-red-700",
}

const ProjectCard = (props: IProjectCardProps) => {
    const { projectImage, projectTittle, description, plannedDueTime, status, projectId } = props;
    return (
        <div className='rounded-2xl bg-white py-4 px-8 shadow-lg'>
            <Link to={`/projects/${projectId}`}
                className='text-gray-900 font-semibold hover:text-purple-700'>
                {projectTittle}
            </Link>

            <img src={projectImage ?? demoProject} className='object-cover mt-2' />

            <div className='mt-4 w-full'>


                <p className='text-sm text-gray-700 text-justify mt-2'>
                    {description}
                </p>


                <p className='text-sm text-gray-700 font-semibold mt-2'>
                    Planned Due Date:
                    <span className='bg-slate-300 p-1 rounded-lg ml-2 font-normal'>
                        {plannedDueTime}
                    </span>
                </p>
                <div className='w-full flex justify-between items-center'>

                    <span className={
                        STATUS_BACKGROUND_COLOR[status] + " text-sm font-semibold text-white mt-2 py-1 px-2 rounded-lg w-fit"
                    }>
                        {status}
                    </span>

                    <div className='flex justify-center items-center gap-2'>
                        <button className='text-blue-700 hover:bg-blue-100 p-1 rounded-lg'>
                            <FaRegEdit className='size-6' />
                        </button>
                        <button className='text-red-700 hover:bg-red-100 p-1 rounded-lg'>
                            <FaRegTrashAlt className='size-6' />
                        </button>
                    </div>
                </div>


            </div>
        </div>
    )
}

export default ProjectCard;