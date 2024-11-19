import { TextInput } from "../Inputs"

interface ICreateProjectModalProps {
    setIsShow: () => void
}

const CreateProjectModal = (props: ICreateProjectModalProps) => {

    const { setIsShow } = props;
    return (
        <div className='backdrop-blur-lg z-20 fixed top-0 left-0 min-h-screen w-screen flex flex-col justify-center items-center'>

            <div className='flex flex-col items-center bg-white lg:w-[35%] w-[80%] pb-4'>
                <h2 className='text-white font-semibold text-xl bg-purple-700 py-2 w-full text-center'>
                    CREATE NEW PROJECT
                </h2>

                <form className='space-y-4 w-full mt-4 px-4'>
                    <TextInput name='projectName' label='Project Name' type='text' placeholder='Enter your project name' />

                    <TextInput name='description' label='Project Description' type='text' placeholder='Enter your project description' />


                    <div className='flex justify-end items-center gap-8'>
                        <button type='button' className='font-semibold p-2 text-white bg-emerald-700 hover:bg-emerald-400 rounded-lg'>
                            ACCEPT
                        </button>

                        <button type='button' className='font-semibold p-2 text-white bg-red-700 hover:bg-red-400 rounded-lg'
                            onClick={setIsShow}>
                            CANCEL
                        </button>
                    </div>
                </form>
            </div>

        </div>
    );
}

export default CreateProjectModal;