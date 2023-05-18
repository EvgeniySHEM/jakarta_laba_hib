package ru.sanctio.jakarta_laba_hib.servlets;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.sanctio.jakarta_laba_hib.repository.DbManagerLocal;

import java.io.IOException;

@WebServlet(name = "AuthorizationServlet", value = "/Authorization-servlet")
public class AuthorizationServlet extends HttpServlet {
    @EJB
    private DbManagerLocal repository;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");

        boolean check = repository.checkUser(request.getParameter("username"), request.getParameter("password"));
        if(check) {
            getServletContext().getRequestDispatcher("/ViewListServlet").forward(request, response);
        } else {
            response.sendError(401, "В доступе отказано : логин или пароль не найдены");
        }
    }
}