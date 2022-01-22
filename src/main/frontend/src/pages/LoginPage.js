import React, {useContext, useEffect, useState} from 'react';
import {AuthenticationContext} from "../context";
import {Link} from "react-router-dom";
import AuthenticationService from "../services/AuthenticationService";

const LoginPage = () => {
    const {setAuth} = useContext(AuthenticationContext);
    const [phone, setPhone] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        setError('');
    }, [phone, password])

    const login = event => {
        event.preventDefault();
        AuthenticationService.login(phone, password).then(response => {
            if (response.success) {
                setAuth(response.results);
            } else {
                setError(response.errorMessage);
            }
        });
    }

    return (
        <div className="container">
            <form className="form-signin" onSubmit={login}>
                <h2 className="form-signin-heading">Log in</h2>
                <div className="d-grid gap-2">
                    <h5 className="ui-state-error-text" style={{color: 'red'}}>{error}</h5>
                    <label className="form-label mt-4">Phone number</label>
                    <input value={phone} onChange={event => setPhone(event.target.value)}
                           required type="text" className="form-control" id="phone" placeholder="Phone"/>
                    <label className="form-label mt-4">Password</label>
                    <input value={password} onChange={event => setPassword(event.target.value)}
                           required type="password" className="form-control" id="password" placeholder="Password"/>
                    <button className="btn btn-outline-primary btn-block" type="submit">Log in</button>
                    {/*//TODO forward to "/users/registration"*/}
                    <h4 className="text-center">
                        <Link to="/registration">Create an account</Link>
                    </h4>
                </div>
            </form>
        </div>
    );
};

export default LoginPage;