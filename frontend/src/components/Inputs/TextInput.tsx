import { error } from "console";
import { register } from "module"
import { UseFormRegisterReturn } from "react-hook-form"

interface ITextInputProps {
    name: string,
    label: string,
    type: string,
    placeholder: string,
    register?: UseFormRegisterReturn,
    errorMessage?: string
}

const TextInput = (props: ITextInputProps) => {

    const { name, label, type, register, placeholder, errorMessage } = props;

    return (
        <div>
            <label htmlFor={name}
                className="block font-medium text-sm text-gray-900 ml-1 mb-1">
                {label}
            </label>

            <input {...register}
                type={type} name={name} id={name} placeholder={placeholder}
                className="w-full bg-gray-50 border border-gray-300 text-gray-900 rounded-lg p-2 outline-none focus:border-indigo-700 pe-16" />

            {errorMessage &&
                < p className="py-1 px-2 mt-1 text-red-900 bg-red-100 w-fit text-xs font-semibold">
                    {errorMessage}
                </p>
            }
        </div>
    )
}

export default TextInput;