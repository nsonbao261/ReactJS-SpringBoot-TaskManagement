import { DefaultFooter, DefaultHeader } from "@/components";
import { ReactNode } from "react"

interface IDefaultLayoutProps {
    children?: ReactNode
}

const DefaultLayout = (props: IDefaultLayoutProps) => {

    const { children } = props;
    return (
        <>
            <div className="w-full min-h-screen">
                <DefaultHeader />
                {children}
                <DefaultFooter />
            </div>
        </>
    )
}

export default DefaultLayout
