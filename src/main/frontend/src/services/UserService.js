import axios from "axios";

const BASE_URL = "http://localhost:9000/";
const USERS_BASE_URL = BASE_URL + "users";

export default class UserService {

    static async login(data) {
        let response = await axios.post(
            BASE_URL + 'login',
            data,
            {
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        );

        return response.data;
    }
};