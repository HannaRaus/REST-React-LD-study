import React, {useEffect, useState} from "react";
import 'bootstrap/dist/css/bootstrap.css';
import {BrowserRouter} from "react-router-dom";
import Menu from "./components/Menu";
import LdRouter from "./router/LdRouter";
import {AuthContext} from "./context";

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
        <AuthContext.Provider value={{
            isAuthenticated,
            setAuthenticated,
            isLoading
        }}>
            <BrowserRouter>
                <Menu/>
                <LdRouter/>
            </BrowserRouter>
        </AuthContext.Provider>
    );
}

export default LdStudyApp;
