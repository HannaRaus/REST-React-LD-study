import axios from "axios";

const LESSON_BASE_URL = "http://localhost:9000/lessons/";

export default class LessonService {

    static async getLessons() {
        const response = await axios.get(LESSON_BASE_URL + "all");
        return response.data;
    }

    static async getLesson(id) {
        const response = await axios.get(LESSON_BASE_URL + id);
        return response.data;
    }
}
