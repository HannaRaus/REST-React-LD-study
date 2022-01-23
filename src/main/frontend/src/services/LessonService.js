import axios from "../api/axios";
import AuthenticationService from "./AuthenticationService";

const ALL = "/lessons/all";
const LESSON_BY_ID = "/lessons/:id";
const CREATE = "/lessons/create";


export default class LessonService {

    static async getLessons() {
        let response = await axios.get(ALL, {headers: AuthenticationService.authHeader()});
        return response.data;
    }

    static async getLesson(id) {
        const response = await axios.get("/lessons/" + id, {headers: AuthenticationService.authHeader()});
        return response.data;
    }

    static async create(data) {
        return await axios.post(CREATE, data, {headers: AuthenticationService.authHeader()})
            .then(response => {
                return response.data
            });
    }
}
