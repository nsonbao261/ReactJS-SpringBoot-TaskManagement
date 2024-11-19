import DefaultLayout from "@/layouts/DefaultLayout"
import userAva from '@/assets/images/user-ava.png';
import { useEffect, useState } from "react";
import ChangePasswordModal from "@/components/Modals/ChangePasswordModal";
import { EditProfileModal } from "@/components";
import { useAuth } from "@/hooks";
import { IoIosMale, IoIosFemale } from "react-icons/io";

const ProfilePage = () => {

    const [changePasswordModalShow, setChangePaswordModalShow] = useState(false);
    const [editProfileModalShow, setEditProfileModalShow] = useState(false);
    const { userInfo } = useAuth();


    return (


        <DefaultLayout>

            {
                changePasswordModalShow &&
                <ChangePasswordModal setIsShow={() => setChangePaswordModalShow(false)} />
            }

            {
                editProfileModalShow &&
                <EditProfileModal setIsShow={() => setEditProfileModalShow(false)} />
            }
            <div className="bg-slate-100 flex flex-col justify-center items-center py-12">
                <div className="bg-white rounded-lg shadow-lg flex flex-col items-center px-20 py-12">

                    <img src={userInfo?.avatarUrl ?? userAva} className="w-32 h-32 rounded-full" />
                    {
                        userInfo?.firstName && userInfo.lastName &&
                        <p className="text-lg font-semibold text-gray-900 mt-4">
                            {userInfo?.firstName + " " + userInfo?.lastName}
                        </p>
                    }
                    <p className="text-sm text-gray-900 mt-4">
                        {userInfo?.email}
                    </p>

                    {
                        userInfo?.gender &&
                        <p className="text-sm text-gray-900 font-semibold mt-1 flex items-center justify-center gap-1">
                            {
                                userInfo.gender == "MALE"
                                    ? <IoIosMale className="size-4 text-blue-600" />
                                    : <IoIosFemale className="size-4 text-pink-600" />
                            }
                            {userInfo?.gender}
                        </p>
                    }

                    {
                        userInfo?.birthday &&
                        <p className="text-sm text-gray-900 mt-1">
                            {(new Date(userInfo?.birthday)).toLocaleDateString()}
                        </p>
                    }

                    <button className="text-white font-semibold py-2 rounded-lg bg-purple-700 hover:bg-purple-400 w-48 mt-4"
                        onClick={() => setChangePaswordModalShow(true)}>
                        CHANGE PASSWORD
                    </button>
                    <button className="text-white font-semibold py-2 rounded-lg bg-purple-700 hover:bg-purple-400 w-48 mt-2"
                        onClick={() => setEditProfileModalShow(true)}>
                        EDIT PROFILE
                    </button>

                </div>
            </div>
        </DefaultLayout >
    )
}

export default ProfilePage