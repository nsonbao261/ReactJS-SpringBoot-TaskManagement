import { ReactNode } from "react"
import { UseFormRegisterReturn } from "react-hook-form";

interface ISelectInputProps {
    label: string,
    name: string,
    children?: ReactNode,
    register?: UseFormRegisterReturn,
    errorMessage?: string,

}

const SelectInput = (props: ISelectInputProps) => {
    const { label, name, children, register, errorMessage } = props;
    return (
        <div>
            <label htmlFor={name} className="text-sm font-medium text-gray-900 block mb-1 ml-1">
                {label}
            </label>
            <select {...register} name={name} id={name} className="w-full bg-gray-50 border border-gray-300 text-gray-900 rounded-lg p-2 outline-none focus:border-indigo-700">
                {children}
            </select>

            {errorMessage &&
                <p className="py-1 px-2 mt-1 text-red-900 bg-red-100 w-fit text-xs font-semibold">
                    {errorMessage}
                </p>
            }
        </div>
    )
}

export default SelectInput
