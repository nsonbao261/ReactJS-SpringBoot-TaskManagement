import { UseFormRegisterReturn } from "react-hook-form";

interface IDateInputProps {
    name: string,
    label: string,
    placeholder: string,
    register?: UseFormRegisterReturn,
    errorMessage?: string,
    onChange?: (value: string) => void,
}

const DateInput = (props: IDateInputProps) => {
    const { name, label, register, placeholder, errorMessage, onChange } = props;

    return (
        <div>
            <label htmlFor={name}
                className="block font-medium text-sm text-gray-900 ml-1 mb-1">
                {label}
            </label>

            <input {...register}
                type="date" name={name} id={name} placeholder={placeholder}
                className="w-full bg-gray-50 border border-gray-300 text-gray-900 rounded-lg p-2 outline-none focus:border-indigo-700" />

            {errorMessage &&
                < p className="py-1 px-2 mt-1 text-red-900 bg-red-100 w-fit text-xs font-semibold">
                    {errorMessage}
                </p>
            }
        </div>
    )
}

export default DateInput;