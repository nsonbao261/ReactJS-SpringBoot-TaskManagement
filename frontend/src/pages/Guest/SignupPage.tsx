import { AvatarInput, DateInput, PasswordInput, SelectInput, TextInput } from "@/components";
import { authApi, uploadApi } from "@/services";
import { yupResolver } from "@hookform/resolvers/yup";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import * as yup from 'yup';
import { IRegisterRequest } from "@/types";
import { toast } from "react-toastify";

const registerSchema = yup.object().shape({
    email: yup.string().required("Email is required").email("Email is inapproriate"),
    password: yup.string().required("Password is required").min(8, "Password has minimum of 8 characters"),
    firstName: yup.string().nullable("Firstname is required")
        .min(2, "First Name required 2-50 characters")
        .max(50, "First Name required 2-50 characters"),
    lastName: yup.string().required("Lastname is required")
        .min(2, "Last Name required 2-50 characters")
        .max(50, "Last Name required 2-50 characters"),
    gender: yup.string().required().notOneOf([""], "You must select your gender"),
    birthday: yup.date(),
    confirmPassword: yup.string().required("Password Confirmation is empty")
        .oneOf([yup.ref('password')], 'Passwords do not match'),
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

const SignupPage = () => {

    const [avatarPreview, setAvatarPreview] = useState<null | string>(null);
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);



    const { register, formState: { errors }, reset, handleSubmit, watch } = useForm({
        resolver: yupResolver(registerSchema)
    });



    const avatar: any = watch("avatar");

    useEffect(() => {
        if (avatar && avatar.length > 0) {
            const avatarUrl = URL.createObjectURL(avatar[0]);
            setAvatarPreview(avatarUrl);
        }
    }, [avatar])

    const uploadImage = async () => {
        try {
            const uploadedFile = avatar[0];

            const formData = new FormData();

            formData.append("uploadedFile", uploadedFile);

            const response = await uploadApi.upload(formData);
            return response.data;
        } catch (error) {
            return null;
        }
    }

    const onSubmitHandler = async (data: any) => {
        setLoading(true);
        try {
            const { email, firstName, lastName, password, gender, birthday } = data
            const avatarUrl = await uploadImage();
            const registerRequest: IRegisterRequest = {
                email, password, gender, firstName, lastName,
                birthday: new Date(birthday + 1),
                avatarUrl,
            }

            const response = await authApi.register(registerRequest);

            if (response && response.data) {
                setLoading(false);
                navigate("/login");
            }

        } catch (error: any) {
            console.log(error.request.response);
            toast.error("Sign Up Failed!!!");
        }
        setLoading(false);
    }


    return (
        <div className="flex flex-col justify-center items-center bg-gradient-to-r from-emerald-500 to-indigo-500 min-h-screen min-w-screen py-12">
            <div className="flex flex-col justify-center items-center px-12 py-6 bg-white rounded-lg min-w-[80%] lg:min-w-[35%]">
                <h1 className="font-semibold text-teal-2xl text-gray-900">
                    REGISTER TO GET AN ACCOUNT
                </h1>

                <form className="space-y-4 w-full mt-4" onSubmit={handleSubmit(onSubmitHandler)}
                    encType="multipart/form-data">
                    <AvatarInput label="Choose Your Avatar" name="avatar" avatar={avatarPreview} register={register("avatar")} errorMessage={errors.avatar?.message} />

                    <TextInput name="email" type="email" label="Email" placeholder="Enter Your Email" register={register("email")} errorMessage={errors.email?.message} />
                    <TextInput name="firstName" type="text" label="First Name" placeholder="Enter Your First Name" register={register("firstName")} errorMessage={errors.firstName?.message} />
                    <TextInput name="lastName" type="text" label="Last Name" placeholder="Enter Your Last Name" register={register("lastName")} errorMessage={errors.lastName?.message} />
                    <SelectInput name="gender" label="Gender" errorMessage={errors.gender?.message} register={register("gender")}>
                        <option selected value="">Select Your Gender</option>
                        <option value="MALE">Male</option>
                        <option value="FEMALE">Female</option>
                    </SelectInput>
                    <DateInput name="birthday" label="Date Of Birth" register={register("birthday")} errorMessage={errors.birthday?.message} placeholder="Enter Your Date Of Birth" />
                    <PasswordInput name="password" label="Password" errorMessage={errors.password?.message} register={register("password")} />
                    <PasswordInput name="confirmPassword" label="Password Confirmation" errorMessage={errors.confirmPassword?.message} register={register("confirmPassword")} />

                    <button
                        className="text-white rounded-lg font-semibold bg-indigo-900 hover:bg-indigo-700 py-2 w-full">
                        REGISTER
                    </button>
                </form>

                <p className="text-sm text-gray-500 font-light mt-4 self-end mr-2">
                    Already had an account?
                    <Link to="/login"
                        className="font-semibold text-gray-900 hover:underline hover:text-indigo-900 pl-2">
                        Log in
                    </Link>
                </p>
            </div>
        </div>
    );
}

export default SignupPage;