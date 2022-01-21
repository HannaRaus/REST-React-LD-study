import React, {useContext, useState} from 'react';
import {AuthenticationContext} from "../context";
import UserService from "../services/UserService";
import {useHistory} from "react-router-dom";

const LoginPage = () => {
    const history = useHistory();
    const {isAuthenticated, setAuthenticated} = useContext(AuthenticationContext);
    const [phone, setPhone] = useState('');
    const [password, setPassword] = useState('');

    const login = event => {
        event.preventDefault();
        const loginData = {
            'phone': phone,
            'password': password
        }
        UserService.login(loginData).then(response => {
            if (response.success) {
                setAuthenticated(true);
                localStorage.setItem('authenticated', 'true');
                history.push("/lessons/all");
            }
        })
    }

    return (
        <div className="container">
            <form className="form-signin" onSubmit={login}>
                <h2 className="form-signin-heading">Log in</h2>
                <div className="d-grid gap-2">
                    {/*//TODO show errors*/}
                    <label className="form-label mt-4">Phone number</label>
                    <input value={phone} onChange={event => setPhone(event.target.value)}
                           required type="text" className="form-control" id="phone" placeholder="Phone"/>
                    <label className="form-label mt-4">Password</label>
                    <input value={password} onChange={event => setPassword(event.target.value)}
                           required type="password" className="form-control" id="password" placeholder="Password"/>
                    <button className="btn btn-outline-primary btn-block" type="submit">Log in</button>
                    {/*//TODO forward to "/users/registration"*/}
                    <h4 className="text-center">
                        <a>Create an account</a>
                    </h4>
                </div>
            </form>
        </div>
    );
};

export default LoginPage;