import React, {useContext} from 'react';
import {AuthContext} from "../context";

const LoginPage = () => {
    const {isAuthenticated, setAuthenticated} = useContext(AuthContext);

    const login = event => {
        event.preventDefault();
        setAuthenticated(true);
        localStorage.setItem('authenticated', 'true')
    }

    return (
        <div className="container">
            {/*//TODO forward to "/login"*/}
            <form className="form-signin" onSubmit={login}>
                <h2 className="form-signin-heading">Log in</h2>
                <div className="d-grid gap-2">
                    {/*//TODO show errors*/}
                    <label className="form-label mt-4">Phone number</label>
                    <input required type="text" className="form-control" id="phone" placeholder="Phone"/>
                    <label className="form-label mt-4">Password</label>
                    <input required type="password" className="form-control" id="password"
                           placeholder="Password"/>
                    <button className="btn btn-outline-primary btn-block" type="submit">Log in</button>
                    {/*//TODO forward to "/users/registration"*/}
                    <h4 className="text-center">
                        <a>Create an account</a>
                    </h4>
                </div>


            </form>

        </div>

        /*
        <div class="container">
    <form method="POST" class="form-signin" action="/users/login">
        <h2 class="form-signin-heading">Log in</h2>
        <div class="form-group ${error != null ? 'has-error' : ''}">

            <div th:form="${error != null ? 'has-error' : ''}">
                <span class="ui-state-error-text" style="color: red" th:text="${message}"></span>

                <div class="form-group">
                    <label for="phone" class="form-label mt-4">Phone number</label>
                    <input required type="text" class="form-control" id="phone" name="phone" placeholder="Phone">
                </div>
                <div class="form-group">
                    <label for="password" class="form-label mt-4">Password</label>
                    <input required type="password" class="form-control" id="password" name="password"
                           placeholder="Password">
                </div>
                <span class="ui-state-error-text" style="color: red" th:text="${error}"></span>

            </div>

            <button class="btn btn-outline-primary btn-block" type="submit">Log in</button>
            <h4 class="text-center"><a href="/users/registration">Create an account</a></h4>
        </div>
    </form>
</div>
         */
    );
};

export default LoginPage;