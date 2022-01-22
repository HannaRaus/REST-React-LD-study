import LessonsPage from "../pages/LessonsPage";
import LessonPage from "../pages/LessonPage";
import LoginPage from "../pages/LoginPage";
import RegistrationPage from "../pages/RegistrationPage";

export const privateRoutes = [
    {path: '/lessons/all', component: LessonsPage, exact: true},
    {path: '/lessons/:id', component: LessonPage, exact: true}
]

export const publicRoutes = [
    {path: '/login', component: LoginPage, exact: true},
    {path: '/registration', component: RegistrationPage, exact: true}
]