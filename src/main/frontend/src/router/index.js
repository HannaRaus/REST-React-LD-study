import LessonsPage from "../pages/LessonsPage";
import LessonPage from "../pages/LessonPage";
import LoginPage from "../pages/LoginPage";
import Menu from "../components/Menu";

export const privateRoutes = [
    {path: '/lessons/all', component: Menu, exact: true},
    {path: '/lessons/all', component: LessonsPage, exact: true},
    {path: '/lessons/:id', component: LessonPage, exact: true}
]

export const publicRoutes = [
    {path: '/login', component: LoginPage, exact: true}
]