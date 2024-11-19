import { useForm } from 'react-hook-form';
import { PasswordInput } from '../Inputs'
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { IChangePassswordRequest } from '@/types';
import { useState } from 'react';
import { authApi } from '@/services/auth.service';
import { useAuth } from '@/hooks';
import { toast } from 'react-toastify';

interface IChangePasswordModalProps {
    setIsShow: () => void
}

interface IChangePasswordFormData {
    currentPassword: string;
    newPassword: string;
    newPasswordConfirm: string;
}

const ChangePasswordSchema = yup.object().shape({
    currentPassword: yup.string().required("Password is required").min(8, "Password has minimum of 8 characters"),
    newPassword: yup.string().required("Password is required").min(8, "Password has minimum of 8 characters"),
    newPasswordConfirm: yup.string().required("Password Confirmation is required")
        .oneOf([yup.ref('newPassword')], 'Passwords do not match'),
})
const ChangePasswordModal = (props: IChangePasswordModalProps) => {

    const { setIsShow } = props

    const { userInfo } = useAuth();

    const [loading, setLoading] = useState(false);

    const { register, reset, handleSubmit, formState: { errors } } = useForm({
        resolver: yupResolver(ChangePasswordSchema),
    })

    const handleChangePassword = async (data: IChangePasswordFormData) => {
        setLoading(true);
        try {
            const { currentPassword, newPassword } = data
            const response = await authApi.changePassword({
                currentPassword, newPassword, userId: userInfo?.userId ?? ""
            });
            console.log(response)
        } catch (error) {
            console.log(error);
        }
        setLoading(false);
    }

    return (
        <div className='backdrop-blur-lg z-20 fixed top-0 left-0 min-h-screen w-screen flex flex-col justify-center items-center'>

            <div className='flex flex-col items-center bg-white lg:w-[35%] w-[80%] pb-4'>
                <h2 className='text-white font-semibold text-xl bg-purple-700 py-2 w-full text-center'>
                    CHANGE PASSWORD
                </h2>

                <form className='space-y-4 w-full mt-4 px-4'
                    onSubmit={handleSubmit(handleChangePassword)}>
                    <PasswordInput name='currentPassword' label='Current Password' errorMessage={errors.currentPassword?.message} register={register("currentPassword")} />
                    <PasswordInput name='newPassword' label='New Password' errorMessage={errors.newPassword?.message} register={register("newPassword")} />
                    <PasswordInput name='newPasswordConfirm' label='Password Confirmation' errorMessage={errors.newPasswordConfirm?.message} register={register("newPasswordConfirm")} />


                    <div className='flex justify-end items-center gap-8'>
                        <button type='submit' className='font-semibold p-2 text-white bg-emerald-700 hover:bg-emerald-400 rounded-lg'>
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
    )
}

export default ChangePasswordModal
