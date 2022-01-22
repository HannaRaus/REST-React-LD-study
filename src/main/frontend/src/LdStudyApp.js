import React, {useEffect, useState} from "react";
import 'bootstrap/dist/css/bootstrap.css';
import {BrowserRouter} from "react-router-dom";
import LdRouter from "./router/LdRouter";
import {AuthenticationContext} from "./context";
import AuthenticationService from "./services/AuthenticationService";

function LdStudyApp() {
    const [auth, setAuth] = useState({});
    const [isLoading, setLoading] = useState(true);

    useEffect(() => {
        if (AuthenticationService.getCurrentUser()) {
            setAuth(AuthenticationService.getCurrentUser());
        }
        setLoading(false);
    }, [])

    return (
        <AuthenticationContext.Provider value={{
            auth,
            setAuth,
            isLoading
        }}>
            <BrowserRouter>
                <LdRouter/>
            </BrowserRouter>
        </AuthenticationContext.Provider>
    );
}

export default LdStudyApp;
