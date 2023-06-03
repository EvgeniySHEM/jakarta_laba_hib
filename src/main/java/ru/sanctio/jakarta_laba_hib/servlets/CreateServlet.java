package ru.sanctio.jakarta_laba_hib.servlets;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.sanctio.jakarta_laba_hib.beans.UpdateBeanLocal;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;

import java.io.IOException;

@WebServlet(name = "Create", value = "/Create")
public class CreateServlet extends HttpServlet {
    @EJB
    private UpdateBeanLocal updateBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String clientId = request.getParameter("clientId");
        String ip = request.getParameter("ip");
        String mac = request.getParameter("mac");
        String model = request.getParameter("model");
        String address = request.getParameter("address");

        AddressEntity newAddress = null;
        try {
            newAddress = new AddressEntity(ip, mac, model, address);
        } catch (NullPointerException | IllegalArgumentException ex) {
            response.sendError(490, ex.getMessage());
        }
        if (!response.isCommitted()) {
            if (updateBean.addClientAddress(newAddress, clientId)) {
                response.sendRedirect("ViewListServlet");
            } else {
                response.sendError(490, "Такой адрес уже есть в базе данных");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String clientName = request.getParameter("clientName");
        String selectType = request.getParameter("select");
        String date = request.getParameter("date");
        String ip = request.getParameter("ip");
        String mac = request.getParameter("mac");
        String model = request.getParameter("model");
        String address = request.getParameter("address");

        ClientEntity newClient = null;
        AddressEntity newAddress = null;
        try {
            newClient = new ClientEntity(clientName, selectType, date);
            newAddress = new AddressEntity(ip, mac, model, address);
            newClient.addAddress(newAddress);
        } catch (NullPointerException | IllegalArgumentException ex) {
            response.sendError(490, ex.getMessage());
        }
        if (!response.isCommitted()) {
            if (updateBean.createNewClient(newClient, newAddress)) {
                response.sendRedirect("ViewListServlet");
            } else {
                response.sendError(490, "Клиент с таким адресом уже есть в базе данных");
            }
        }
    }
}