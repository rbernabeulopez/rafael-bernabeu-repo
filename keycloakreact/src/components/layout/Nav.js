import React from "react";
import {Link} from "react-router-dom";
import {logout, isAuthenticated, securePage} from "../utils/keycloak";

const Nav = () => {
    return (
        <div>
            <div className="top-0 w-full flex flex-wrap">
                <section className="x-auto">
                    <nav className="flex justify-between bg-gray-200 text-blue-800 w-screen">
                        <div className="px-5 xl:px-12 py-6 flex w-full items-center">
                            <h1 className="text-3xl font-bold font-heading">
                                Keycloak React AUTH.
                            </h1>
                            <ul className="hidden md:flex px-4 mx-auto font-semibold font-heading space-x-12">
                                <li>
                                    <Link to="/">
                                        Home
                                    </Link>
                                </li>
                                <li>
                                    <Link to="/secured">
                                        Secured Page
                                    </Link>
                                </li>
                                <li>
                                    {
                                        isAuthenticated() ? <Link onClick={() => logout()} to={"/"}>Logout</Link> :
                                            <Link onClick={() => securePage()} to={"/"}>Login</Link>
                                    }
                                </li>
                            </ul>
                            <hr />
                        </div>
                    </nav>
                </section>
            </div>
        </div>
    );
};



export default Nav;