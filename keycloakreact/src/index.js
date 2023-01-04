import React from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App';
import keycloak from "./components/utils/keycloak";

const root = createRoot(document.getElementById('root'));

keycloak.init({
    onLoad: "check-sso",
    //silentCheckSsoRedirectUri: window.location.origin + "/silent-check-sso.html",
}).then(authenticated => {
    localStorage.setItem("react-token", keycloak.token);
    localStorage.setItem("react-refresh-token", keycloak.refreshToken);
    root.render(<App/>);
})