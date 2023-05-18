package ru.sanctio.jakarta_laba_hib.servlets;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;
import ru.sanctio.jakarta_laba_hib.beans.UpdateBeanLocal;

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

        AddressEntity newAddress = new AddressEntity();
        try {
            newAddress.setIp(ip);
            newAddress.setMac(mac);
            newAddress.setModel(model);
            newAddress.setAddress(address);
        } catch (NullPointerException | IllegalArgumentException ex) {
            response.sendError(490, ex.getMessage());
        }
        if(!response.isCommitted()) {
            if(updateBean.addClientAddress(newAddress, clientId)) {
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

        ClientEntity newClient = new ClientEntity();
        AddressEntity newAddress = new AddressEntity();
        try {
            newClient.setClientName(clientName);
            newClient.setType(selectType);
            newClient.setAdded(date);
            newAddress.setIp(ip);
            newAddress.setMac(mac);
            newAddress.setModel(model);
            newAddress.setAddress(address);
            newClient.addAddress(newAddress);
//            updateBean.createNewClient(clientName,selectType,date,ip,mac,model,address);
        } catch (NullPointerException | IllegalArgumentException ex) {
            response.sendError(490, ex.getMessage());
        }
        if(!response.isCommitted()) {
            if(updateBean.createNewClient(newClient, newAddress)) {
                response.sendRedirect("ViewListServlet");
            } else {
                response.sendError(490, "Клиент с таким адресом уже есть в базе данных");
            }
        }
    }
}