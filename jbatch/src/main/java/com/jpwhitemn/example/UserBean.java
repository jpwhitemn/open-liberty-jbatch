// tag::userbean[]
package com.jpwhitemn.example;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;

import java.io.Serializable;

@Named("userBean")
@RequestScoped

public class UserBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SecurityContext securityContext;

    public String getUsername() {
        return securityContext.getCallerPrincipal().getName();
    }

    public String getRoles() {
        String roles = "";
        if (securityContext.isCallerInRole("admin")) {
            roles = "admin";
        }
        if (securityContext.isCallerInRole("user")) {
            if (!roles.isEmpty()) {
                roles += ", ";
            }
            roles += "user";
        }
        return roles;
    }
}
// end::userbean[]
