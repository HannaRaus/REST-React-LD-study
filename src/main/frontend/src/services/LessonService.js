import axios from "../api/axios";
import AuthenticationService from "./AuthenticationService";

export default class LessonService {

    static async getLessons() {
        let response = await axios.get("/lessons/all", {headers: AuthenticationService.authHeader()});
        return response.data;
    }

    static async getLesson(id) {
        const response = await axios.get("/lessons/" + id, {headers: AuthenticationService.authHeader()});
        return response.data;
    }
}
