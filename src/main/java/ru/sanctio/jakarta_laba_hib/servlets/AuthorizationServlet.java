package ru.sanctio.jakarta_laba_hib.servlets;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.sanctio.jakarta_laba_hib.dao.DbManagerLocal;

import java.io.IOException;

@WebServlet(name = "AuthorizationServlet", value = "/AuthorizationServlet")
public class AuthorizationServlet extends HttpServlet {
    @EJB
    private DbManagerLocal repository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean check = repository.checkUser(request.getParameter("username"), request.getParameter("password"));
        if(check) {
            getServletContext().getRequestDispatcher("/ViewListServlet").forward(request, response);
        } else {
            response.sendError(401, "В доступе отказано : логин или пароль не найдены");
        }
    }
}