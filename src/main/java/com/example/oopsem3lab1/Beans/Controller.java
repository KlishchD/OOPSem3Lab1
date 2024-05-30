package com.example.oopsem3lab1.Beans;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Controller {
    public boolean isLoggedIn(HttpServletRequest req) {
        return getSession(req) != null;
    }

    public boolean isLogoutAction(HttpServletRequest req) {
        return getAction(req).equals("logout");
    }

    public boolean hasRole(HttpServletRequest req, String role) {
        KeycloakSecurityContext context = getSession(req);

        if (context == null)
        {
            return false;
        }

        AccessToken token = context.getToken();
        AccessToken.Access access = token.getRealmAccess();
        return access.getRoles().contains(role);
    }

    public String getUsername(HttpServletRequest req) {
        KeycloakSecurityContext context = getSession(req);
        AccessToken token = context.getToken();

        return token.getPreferredUsername();
    }

    private String getReferrerUri(HttpServletRequest req) {
        StringBuffer uri = req.getRequestURL();
        String q = req.getQueryString();
        if (q != null) {
            uri.append("?").append(q);
        }
        return uri.toString();
    }

    private KeycloakSecurityContext getSession(HttpServletRequest req) {
        return (KeycloakSecurityContext) req.getAttribute(KeycloakSecurityContext.class.getName());
    }

    public void logout(HttpServletRequest req) {
        try {
            req.logout();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAction(HttpServletRequest req) {
        if (req.getParameter("action") == null) return "";
        return req.getParameter("action");
    }
}