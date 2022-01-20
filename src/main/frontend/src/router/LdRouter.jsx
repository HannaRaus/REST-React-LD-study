import React, {useContext} from 'react';
import {Redirect, Route, Switch} from "react-router-dom";
import {privateRoutes, publicRoutes} from "./index";
import {AuthenticationContext} from "../context";

const LdRouter = () => {
    //TODO change to false then security will work
    const {isAuthenticated, isLoading} = useContext(AuthenticationContext);

    if (isLoading) {
        return <h2/>
    }
    return (
        isAuthenticated
            ? <Switch>
                {privateRoutes.map(route =>
                    <Route
                        key={route.path}
                        component={route.component}
                        path={route.path}
                        exact={route.exact}/>
                )}
                {/*<Redirect to="/lessons/all"/>*/}
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