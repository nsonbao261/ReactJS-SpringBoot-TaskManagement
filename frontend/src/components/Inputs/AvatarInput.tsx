import avaPlaceholder from '@/assets/images/avatar-placeholder.png'
import { UseFormRegisterReturn } from 'react-hook-form';

interface IAvatarInputProps {
    avatar: string | null,
    label: string,
    name: string,
    register?: UseFormRegisterReturn,
    errorMessage?: string
}

const AvatarInput = (props: IAvatarInputProps) => {
    const { avatar, label, name, register, errorMessage } = props;
    return (
        <div className="flex flex-col items-center">
            <img className="rounded-full size-32 object-cover"
                src={avatar ?? avaPlaceholder} />

            <label htmlFor={name} className='text-gray-900 font-semibold hover:text-purple-900 cursor-pointer'>
                {label}
            </label>

            <input {...register} type="file" hidden accept='image/*' id={name} name={name} />

            {errorMessage &&
                < p className="py-1 px-2 mt-1 text-red-900 bg-red-100 w-fit text-xs font-semibold">
                    {errorMessage}
                </p>
            }

        </div>
    )
}

export default AvatarInput
