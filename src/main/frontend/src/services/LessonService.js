import axios from "../api/axios";

export default class LessonService {

    static async getLessons() {
        const response = await axios.get("/lessons/all");
        return response.data;
    }

    static async getLesson(id) {
        const response = await axios.get("/lessons/" + id);
        return response.data;
    }
}
