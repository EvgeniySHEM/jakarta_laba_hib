package ru.sanctio.jakarta_laba_hib.servlets;

import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ru.sanctio.jakarta_laba_hib.beans.UpdateBeanLocal;

import java.io.IOException;

@WebServlet(name = "Delete", value = "/Delete")
public class DeleteServlet extends HttpServlet {
    @EJB
    private UpdateBeanLocal updateBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String addressId = request.getParameter("addressId");
        updateBean.delete(addressId);
        response.sendRedirect("ViewListServlet");
    }
}