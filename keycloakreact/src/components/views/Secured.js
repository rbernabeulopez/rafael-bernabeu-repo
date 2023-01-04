import React, {useEffect} from 'react';
import {securePage} from "../utils/keycloak";


const Secured = () => {
    useEffect(() => securePage(), [])
    return (
        <div>
            <h1 className="text-black text-4xl">Welcome to the Protected Page.</h1>
        </div>
    );
};

export default Secured;