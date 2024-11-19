import { useState } from "react";
import { UseFormRegisterReturn } from "react-hook-form"
import { FaRegEye, FaRegEyeSlash } from "react-icons/fa";


interface IPasswordInputProps {
    name: string,
    label: string
    register?: UseFormRegisterReturn,
    errorMessage?: string,
}

const PasswordInput = (props: IPasswordInputProps) => {

    const { name, register, label, errorMessage } = props;

    const [passwordHidden, setPasswordHidden] = useState(true);

    return (
        <div>
            <label htmlFor={name}
                className="block font-medium text-sm text-gray-900 ml-1 mb-1">
                {label}
            </label>

            <div className="relative flex items-center">
                <input {...register}
                    name={name} id={name} placeholder="Enter Your Password"
                    type={passwordHidden ? "password" : "text"}
                    className="w-full bg-gray-50 border border-gray-300 text-gray-900 rounded-lg p-2 outline-none focus:border-indigo-700 pe-16" />
                <button className="absolute right-4 z-10" type="button"
                    onClick={() => setPasswordHidden(!passwordHidden)}>
                    {
                        passwordHidden ? <FaRegEyeSlash /> : <FaRegEye />
                    }
                </button>
            </div>
            {errorMessage &&
                < p className="py-1 px-2 mt-1 text-red-900 bg-red-100 w-fit text-xs font-semibold">
                    {errorMessage}
                </p>
            }
        </div >
    )
}

export default PasswordInput