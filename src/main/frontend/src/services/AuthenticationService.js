import axios from "../api/axios";

const LOGIN_URL = "/login";
const LOGOUT_URL = "/logout";
const REGISTER_URL = "/register";

const login = async (phone, password) => {
    return await axios.post(LOGIN_URL,
        JSON.stringify({phone, password}),
        {
            headers: {'Content-Type': 'application/json'}
        }).then(response => {
        let data = response.data;
        if (data.success) {
            localStorage.setItem("token", JSON.stringify(data.results.token))
        }
        return data;
    });
}

const logout = async () => {
    return await axios.post(LOGOUT_URL,
        null,
        {
            headers:
                {
                    'Content-type': 'application/json;charset=utf-8',
                    'Authorization': getToken()
                }
        }).then(response => {
        let data = response.data;
        if (data.success) {
            localStorage.removeItem("token");
            window.location.reload();
        }
        return data;
    })
}

const register = async (name, phone, password, sendNotifications) => {
    return await axios.post(REGISTER_URL,
        JSON.stringify({name, phone, password, sendNotifications}),
        {
            headers: {'Content-Type': 'application/json', 'Authorization': ''}
        }).then(response => {
        let data = response.data;
        if (data.success) {
            localStorage.setItem("token", JSON.stringify(data.results.token))
        }
        return data;
    });
}

const getToken = () => {
    return JSON.parse(localStorage.getItem("token"));
}

function authHeader() {
    let token = getToken();
    if (token) {
        return {'Authorization': token};
    } else {
        return {};
    }
}

const AuthenticationService = {
    login,
    logout,
    register,
    getToken,
    authHeader
}

export default AuthenticationService;