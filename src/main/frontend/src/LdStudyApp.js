import React, {useEffect, useState} from "react";
import 'bootstrap/dist/css/bootstrap.css';
import {BrowserRouter} from "react-router-dom";
import LdRouter from "./router/LdRouter";
import {AuthenticationContext} from "./context";

function LdStudyApp() {
    const [isAuthenticated, setAuthenticated] = useState(false);
    const [isLoading, setLoading] = useState(true);

    useEffect(() => {
        if (localStorage.getItem('authenticated')) {
            setAuthenticated(true);
        }
        setLoading(false);
    }, [])

    return (
        <AuthenticationContext.Provider value={{
            isAuthenticated,
            setAuthenticated,
            isLoading
        }}>
            <BrowserRouter>
                <LdRouter/>
            </BrowserRouter>
        </AuthenticationContext.Provider>
    );
}

export default LdStudyApp;
