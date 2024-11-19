import { useDispatch, useSelector } from "react-redux";
import { AvatarInput, DateInput, SelectInput, TextInput } from "../Inputs";
import { setUserInfo, userInfoSelector } from "@/store/reducers";
import { useEffect, useMemo, useState } from "react";
import * as yup from "yup";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import { IUserUpdateRequest } from "@/types";
import { authApi } from "@/services";
import { toast } from "react-toastify";
import { error } from "console";

interface IEditProfileModalProps {
    setIsShow: () => void
}

const updateProfileSchema = yup.object().shape({
    firstName: yup.string().required("Firstname is required")
        .min(2, "First Name required 2-50 characters")
        .max(50, "First Name required 2-50 characters"),
    lastName: yup.string().required("Lastname is required")
        .min(2, "Last Name required 2-50 characters")
        .max(50, "Last Name required 2-50 characters"),
    gender: yup.string().required().notOneOf([""], "You must select your gender"),
    birthday: yup.date(),
    avatar: yup.mixed()
        .test("fileSize", "File size must be lower than 5MB",
            (file: any) => {
                if (file && file.length > 0) {
                    const MAX_ALLOWED_FILE_SIZE = 5 * 1024 * 1024
                    return file[0].size <= MAX_ALLOWED_FILE_SIZE
                }
                return true
            }
        )
        .test("fileExtension", "Only jpeg, png, jpg, gif or webp are allowed", (file: any) => {
            if (file && file.length > 0) {
                const ALLOWED_FILE_TYPE: string[] = ["image/jpeg", "image/png", "image/jng", "image/gif", "image/webp"]
                return ALLOWED_FILE_TYPE.includes(file[0].type)


            }
            return true;
        })
})

const EditProfileModal = (props: IEditProfileModalProps) => {

    const { setIsShow } = props;
    const userInfo = useSelector(userInfoSelector);
    const dispatch = useDispatch();

    const [avatarPreview, setAvatarPreview] = useState<string | undefined>(userInfo?.avatarUrl);

    const { register, watch, handleSubmit, reset, setValue } = useForm({
        resolver: yupResolver(updateProfileSchema),
        defaultValues: useMemo(() => {
            return {
                firstName: userInfo?.firstName ?? "",
                lastName: userInfo?.lastName ?? "",
                gender: userInfo?.gender ?? "",
            }
        }, [userInfo])
    })

    // const avatar: any = watch("avatar");

    // useEffect(() => {
    //     if (avatar && avatar.length > 0) {
    //         const avatarUrl = URL.createObjectURL(avatar[0]);
    //         setAvatarPreview(avatarUrl);
    //     }
    // }, [avatar])

    const onSubmitHandler = async (data: IUserUpdateRequest) => {
        if (userInfo) {
            const userId = userInfo.userId;
            await authApi.updateUser(data, userId).then(
                (response) => {
                    if (response && response.data) {
                        dispatch(setUserInfo(response.data));
                        setIsShow();
                        toast.success("Update success");
                    }
                }
            ).catch((error) => {
                toast.error("Update Failed");
            })
        }
    }


    return (
        <div className='backdrop-blur-lg z-20 fixed top-0 left-0 min-h-screen w-screen flex flex-col justify-center items-center'>

            <div className='flex flex-col items-center bg-white lg:w-[35%] w-[80%] pb-4'>
                <h2 className='text-white font-semibold text-xl bg-purple-700 py-2 w-full text-center'>
                    EDIT PROFILE
                </h2>

                <form className='space-y-4 w-full mt-4 px-4' onSubmit={handleSubmit(onSubmitHandler)}>

                    <TextInput name="firstName" label="First Name" type="text" placeholder="Enter Your First Name" register={register("firstName")} />
                    <TextInput name="lastName" label="Last Name" type="text" placeholder="Enter Your Last Name" register={register("lastName")} />
                    {/* <DateInput name="birthday" label="Date Of Birth" placeholder="Enter  your birthday" register={register("birthday")} /> */}
                    <SelectInput name="gender" label="Gender" register={register("gender")}>
                        <option selected={userInfo?.gender == "MALE"} value="MALE">Male</option>
                        <option selected={userInfo?.gender == "FEMALE"} value="FEMALE">Female</option>
                    </SelectInput>


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

export default EditProfileModal
