import axios from "../api/axios";

const LOGIN_URL = "/login";
const LOGOUT_URL = "/logout";
const REGISTER_URL = "/register";

const login = async (phone, password) => {
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

const logout = () => {
    //TODO send post to server
    localStorage.removeItem("auth");
    localStorage.removeItem("token");
}

const register = async (name, phone, password, sendNotifications) => {
    return await axios.post(REGISTER_URL, JSON.stringify({name, phone, password, sendNotifications}));
}

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("auth"));
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
    getCurrentUser,
    getToken,
    authHeader
}

export default AuthenticationService;