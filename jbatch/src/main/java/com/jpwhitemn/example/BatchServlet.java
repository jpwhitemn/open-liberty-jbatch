package com.jpwhitemn.example;

import jakarta.inject.Inject;
import jakarta.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/job/*")
@FormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(errorPage = "/error.html",
                loginPage = "/welcome.html"))
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"admin"},
        transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL))
public class BatchServlet extends HttpServlet {

    private static final long serialVersionUID = 2L;

    @Inject
    BatchControllerBean controllerBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/job/1".equals(req.getRequestURI())) {
            System.out.println("Directing to controller Job #1");
            controllerBean.startJob1();
        } else if ("/job/2".equals(req.getRequestURI())) {
            System.out.println("Directing to controller Job #2");
            controllerBean.startJob2();
        } else if ("/job/3".equals(req.getRequestURI())) {
            System.out.println(controllerBean.status());
        } else {
            System.out.println("Unknown job request number:" + req.getRequestURI());
        }
        resp.sendRedirect("/home");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
