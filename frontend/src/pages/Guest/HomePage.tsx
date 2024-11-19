import { Link } from "react-router-dom";

const HomePage = () => {
    return (
        <div className="flex flex-col px-20 pt-20 pb-8 gap-12 min-h-screen justify-between">
            <h1 className="text-purple-900 font-semibold text-2xl text-center">
                A DEMO TASK MANGEMENT SYSTEM
            </h1>

            <div className="bg-purple-600 h-full pt-12 pb-8 rounded-3xl flex flex-col items-center justify-center gap-20">

                <div className="flex flex-col gap-4 max-w-lg">
                    <h2 className="font-semibold text-white text-center text-2xl">
                        Everything you can do with this app
                    </h2>

                    <p className="text-white text-center">
                        Welcome to your Task Management page, the central hub for organizing and streamlining your tasks. Here, you can effortlessly create, prioritize, and track your to-do list, ensuring nothing slips through the cracks.
                    </p>
                </div>

                <div className="flex flex-col px-60 gap-2">
                    <Link to="/register" className="py-2 px-32 bg-white text-purple-600 rounded-3xl font-semibold text-center hover:bg-purple-300">
                        REGISTER
                    </Link>

                    <Link to="/login" className="py-2 px-32 bg-white text-purple-600 rounded-3xl font-semibold text-center hover:bg-purple-300">
                        LOGIN
                    </Link>
                </div>

            </div>
        </div>
    )
}

export default HomePage;