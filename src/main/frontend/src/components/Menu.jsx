import React, {useContext} from 'react';
import {Link} from "react-router-dom";
import {AuthenticationContext} from "../context";

const Menu = () => {
    const {isAuthenticated, setAuthenticated} = useContext(AuthenticationContext);

    const logout = event => {
        event.preventDefault();
        setAuthenticated(false);
        localStorage.removeItem('authenticated')
    }

    return (
        <div>
            <div>
                <nav className="navbar navbar-expand-lg navbar-light bg-light">
                    <div className="container-fluid">
                        <ul className="nav nav-tabs">
                            <li className="nav-item">
                                <Link to="/lessons/all" className="nav-link active">Lessons</Link>
                            </li>
                        </ul>
                        {/*/lessons/search*/}
                        <form className="d-flex">
                            <input className="form-control mr-sm-2" placeholder="Search" type="search"/>
                            <button className="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                        </form>
                        <Link to="/logout" onClick={logout}
                              className="btn btn-outline-secondary my-2 my-sm-0"
                              type="submit">Logout</Link>
                    </div>
                </nav>
            </div>
        </div>
    );
};

export default Menu;