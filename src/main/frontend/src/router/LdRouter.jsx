import React, {useContext} from 'react';
import {Redirect, Route, Switch} from "react-router-dom";
import {privateRoutes, publicRoutes} from "./index";
import {AuthenticationContext} from "../context";

const LdRouter = () => {
    const {auth} = useContext(AuthenticationContext);
    const {isLoading} = useContext(AuthenticationContext);

    if (isLoading) {
        return <div/>
    }

    return (
        auth
            ? <Switch>
                {privateRoutes.map(route =>
                    <Route
                        key={route.path}
                        component={route.component}
                        path={route.path}
                        exact={route.exact}/>
                )}
                <Redirect to="/lessons/all"/>
            </Switch>
            : <Switch>
                {publicRoutes.map(route =>
                    <Route
                        key={route.path}
                        component={route.component}
                        path={route.path}
                        exact={route.exact}/>
                )}
                <Redirect to="/login"/>
            </Switch>

    );
};

export default LdRouter;