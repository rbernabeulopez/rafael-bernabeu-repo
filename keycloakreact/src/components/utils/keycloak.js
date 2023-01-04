import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
    url: "http://localhost:4300/auth",
    realm: "keycloackreact",
    clientId: "react-client"
})

export const isAuthenticated = () => {
    return keycloak.authenticated
}

export const securePage = () => {
    console.log(localStorage.getItem("react-token"))
    if(!isAuthenticated())
        keycloak.login()
}

export const logout = () => {
    window.location.href = "/"
    keycloak.logout()
}

export const useRefreshToken = () => {
    setTimeout(() => {
        console.log("Actualizando token...")
        keycloak.updateToken(70).then((refreshed) => {
            if (refreshed) {
                console.debug('Token refreshed' + refreshed);
            } else {
                console.warn('Token not refreshed, valid for '
                    + Math.round(keycloak.tokenParsed.exp + keycloak.timeSkew - new Date().getTime() / 1000) + ' seconds');
            }
        }).catch(() => {
            console.error('Failed to refresh token');
        });
    }, 60000)
}

export default keycloak