import axios from "../api/axios";

const token = JSON.parse(localStorage.getItem("token"));

export default class LessonService {

    static async getLessons() {
        let response = await axios.get("/lessons/all", {headers: {'Authorization': token}});
        return response.data;
    }

    static async getLesson(id) {
        const response = await axios.get("/lessons/" + id, {headers: {'Authorization': token}});
        return response.data;
    }
}
