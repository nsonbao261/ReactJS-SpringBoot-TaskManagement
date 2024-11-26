import { userInfoSelector } from '@/store/reducers'
import React, { ReactNode } from 'react'
import { useSelector } from 'react-redux'
import { Navigate } from 'react-router-dom';


const ProtectedRoute = ({ children }: { children: ReactNode }) => {
    const userInfo = useSelector(userInfoSelector);
    return (
        userInfo
            ? children
            : <Navigate to="/" />
    )
}

export default ProtectedRoute
