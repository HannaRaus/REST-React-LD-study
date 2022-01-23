import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";
import AuthenticationService from "../services/AuthenticationService";

const RegistrationPage = () => {
    const [name, setName] = useState('');
    const [phone, setPhone] = useState('');
    const [password, setPassword] = useState('');
    const [sendNotifications, setSendNotifications] = useState('');
    const [nameError, setNameError] = useState('');
    const [phoneError, setPhoneError] = useState('');
    const [passwordError, setPasswordError] = useState('');

    useEffect(() => {
        setNameError('');
        setPhoneError('');
        setPasswordError('');
    }, [name, phone, password, sendNotifications])

    const register = event => {
        event.preventDefault();
        AuthenticationService.register(name, phone, password, sendNotifications).then(response => {
            if (response.success) {
                window.location.href = '/lessons/all';
            } else if (response.validationErrors) {
                let validationErrors = response.validationErrors;
                validationErrors.forEach(error => {
                    switch (error) {
                        case 'WRONG_USER_NAME_FORMAT':
                            setNameError(error.name);
                            break;
                        case 'INCORRECT_PHONE_NUMBER':
                            setPhone(error.name);
                            break;
                        case 'WRONG_PASSWORD_FORMAT':
                            setPasswordError(error.name);
                            break;
                        case 'USER_ALREADY_REGISTERED':
                            setPhoneError(error.name);
                            break;
                    }
                })
            } else if (response.errorMessage) {

            }
        })
    }


    return (
        <div className="container">
            <form className="mt-4" onSubmit={register}>
                <h2 className="form-label">Create account</h2>
                <div className="d-grid gap-2">
                    <label className="form-label mt-4">Enter name in range 3-50 symbols</label>
                    <input className="form-control" type="text" placeholder="Enter your name"
                           value={name} onChange={event => setName(event.target.value)} required/>
                    <h5 className="ui-state-error-text" style={{color: 'red'}}>{nameError}</h5>

                    <label className="form-label mt-4">Enter phone number in format +380...</label>
                    <input className="form-control" type="text" placeholder="Enter your phone number"
                           value={phone} onChange={event => setPhone(event.target.value)} required/>
                    <h5 className="ui-state-error-text" style={{color: 'red'}}>{phoneError}</h5>

                    <label className="form-label mt-4">Enter password in range 3-50 symbols</label>
                    <input className="form-control" type="password" placeholder="Enter your password"
                           value={password} onChange={event => setPassword(event.target.value)} required/>
                    <h5 className="ui-state-error-text" style={{color: 'red'}}>{passwordError}</h5>

                    <div className="form-check">
                        <input className="form-check-input" type="checkbox"
                               value={sendNotifications} onChange={event => setSendNotifications(event.target.value)}/>
                        <label className="form-check-label">Send me notifications</label>
                    </div>

                    <button className="btn btn-outline-primary btn-block" type="submit">Sign in</button>

                    <h4 className="text-center">
                        <Link to="/login">Return to login page</Link>
                    </h4>
                </div>
            </form>

        </div>
    );
};

export default RegistrationPage;