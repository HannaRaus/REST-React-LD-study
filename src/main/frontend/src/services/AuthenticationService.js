import axios from "../api/axios";

const LOGIN_URL = "/login";
const LOGOUT_URL = "/logout";
const REGISTER_URL = "/register";

export default class AuthenticationService {

    static async login(phone, password) {
        return await axios.post(LOGIN_URL, JSON.stringify({phone, password}),
            {
                headers: {'Content-Type': 'application/json'}
            }).then(response => {
            let data = response.data;
            if (data.success) {
                localStorage.setItem("auth", JSON.stringify(data.results))
                localStorage.setItem("token", JSON.stringify(data.results.token))
            }
            return data;
        });
    }

    static async logout() {
        //TODO send post to server
        localStorage.removeItem("auth");
        localStorage.removeItem("token");
    }

    static async register(name, phone, password, sendNotifications) {
        return await axios.post(REGISTER_URL, JSON.stringify({name, phone, password, sendNotifications}));
    }

    static getCurrentUser() {
        return JSON.parse(localStorage.getItem("auth"));
    }
};