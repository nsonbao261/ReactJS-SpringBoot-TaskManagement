import { useState } from "react";
import userAva from '@/assets/images/user-ava.png';
import { Link } from "react-router-dom";
import { useAuth } from "@/hooks";


const DefaultHeader = () => {

  const [isProfileMenuShow, setIsProfileMenuShow] = useState(false);
  const { logout, userInfo } = useAuth();

  return (
    <div>
      <div className="px-12 py-2 bg-gradient-to-r from-purple-600 to-sky-600 flex items-center justify-between relative top-0 w-full">

        <div className="flex items-center gap-4">

          <button onClick={() => setIsProfileMenuShow(!isProfileMenuShow)}>
            <img src={userInfo?.avatarUrl ?? userAva} className='rounded-full w-10 h-10' />
          </button>



          <div>
            {
              userInfo?.firstName && userInfo.lastName &&
              <p className='text-white text-sm font-semibold'>
                {userInfo.firstName + " " + userInfo.lastName}
              </p>
            }

            <p className='text-white text-sm font-semibold'>Hi, there!</p>
          </div>
        </div>

        <Link to="/student/dashboard" className='font-semibold text-white text-xl'>
          TASK MANAGEMENT SYSTEM
        </Link>
      </div>




      {/* Drop Down Menu */}
      {
        isProfileMenuShow &&
        <div className='rounded-lg absolute left-10 bg-white shadow-md focus:outline-none z-10 w-56 block'>
          <div className='py-1'>
            <Link to="/profile" className='block px-4 py-2 text-sm text-gray-900 font-semibold hover:bg-purple-700 hover:text-white'>
              Profile
            </Link>
            <Link to="#" className='block px-4 py-2 text-sm text-gray-900 font-semibold hover:bg-purple-700 hover:text-white'>
              Project
            </Link>
            <button onClick={() => logout()} className='block px-4 py-2 text-sm text-red-900 font-semibold hover:bg-red-700 hover:text-white w-full text-left'>
              Log Out
            </button>
          </div>
        </div>
      }
    </div>
  )
}

export default DefaultHeader
