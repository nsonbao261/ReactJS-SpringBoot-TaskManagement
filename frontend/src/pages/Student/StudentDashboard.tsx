import userAva from '@/assets/images/user-ava.png';
import { CreateProjectModal, ProjectCard, TextInput } from '@/components';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { FaPlus } from 'react-icons/fa'
import DefaultLayout from '@/layouts/DefaultLayout';
import { useProject } from '@/hooks/useProject';

const StudentDashboard = () => {

    const [isProfileMenuShow, setIsProfileMenuShow] = useState(false);
    const [isCreateProjectShow, setIsCreateProjectShow] = useState(false);

    const { projects, getProjectByUserId, } = useProject();

    useEffect(() => {
        getProjectByUserId();
    }, [])
    return (
        <DefaultLayout>
            {/*Add Project Form*/}
            {
                isCreateProjectShow &&
                <CreateProjectModal setIsShow={() => setIsCreateProjectShow(false)} />
            }



            <div className='bg-slate-100 py-4 px-20'>

                <div className='flex items-center justify-between'>
                    <h2 className='text-gray-900 font-semibold text-lg'>
                        Recent Projects
                    </h2>

                    <Link className='py-2 px-4 text-white font-semibold bg-purple-700 rounded-xl hover:bg-purple-500 flex items-center justify-between gap-2 text-sm'
                        to="/projects/create">
                        <FaPlus />

                        PROJECT

                    </Link>
                </div>

                <div className='grid grid-cols-2 mt-4 gap-8'>
                    {
                        projects?.map(item => <ProjectCard
                            projectTittle={item.projectName}
                            description={item.description}
                            plannedDueTime={(new Date(item.plannedDueTime).toISOString().substring(0, 10))}
                            status={item.status}
                            projectId={item.projectId} />)
                    }
                </div>

            </div>

        </DefaultLayout>
    )
}

export default StudentDashboard;