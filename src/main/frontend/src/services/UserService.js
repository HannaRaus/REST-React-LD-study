import axios from "axios";

const USERS_BASE_URL = "http://localhost:9000/users/";

export default class UserService {

    static async login(data) {
        let response = await axios.post(USERS_BASE_URL + "login", data);
        return response.data;
    }
};