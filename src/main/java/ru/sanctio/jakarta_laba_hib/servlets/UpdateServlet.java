package ru.sanctio.jakarta_laba_hib.servlets;

import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ru.sanctio.jakarta_laba_hib.beans.UpdateBeanLocal;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;

import java.io.IOException;

@WebServlet(name = "UpdateServlet", value = "/UpdateServlet")
public class UpdateServlet extends HttpServlet {
    @EJB
    private UpdateBeanLocal updateBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String addressId = request.getParameter("addressId");

        AddressEntity addressEntity = updateBean.selectAddress(addressId);
        System.out.println(addressEntity);

        request.setAttribute("clientId", addressEntity.getClient().getClientId());
        request.setAttribute("addressId", addressEntity.getId());
        request.setAttribute("clientName", addressEntity.getClient().getClientName());
        request.setAttribute("type", addressEntity.getClient().getType());
        request.setAttribute("date", addressEntity.getClient().getAdded());
        request.setAttribute("ip", addressEntity.getIp());
        request.setAttribute("mac", addressEntity.getMac());
        request.setAttribute("model", addressEntity.getModel());
        request.setAttribute("address", addressEntity.getAddress());

        getServletContext().getRequestDispatcher("/UpdateClient.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String clientId = request.getParameter("clientId");
        String addressId = request.getParameter("addressId");
        String clientName = request.getParameter("clientName");
        String type = request.getParameter("type");
        String date = request.getParameter("date");
        String ip = request.getParameter("ip");
        String mac = request.getParameter("mac");
        String model = request.getParameter("model");
        String address = request.getParameter("address");

        ClientEntity client = null;
        AddressEntity addressEntity = null;
        try {
            client = new ClientEntity(Integer.parseInt(clientId),clientName,type,date);
            addressEntity = new AddressEntity(Integer.parseInt(addressId),ip,mac,model,address);
            client.addAddress(addressEntity);
        } catch (NullPointerException | IllegalArgumentException ex) {
            response.sendError(490, ex.getMessage());
        }
        if(!response.isCommitted()) {
            if(updateBean.update(client, addressEntity)) {
                response.sendRedirect("ViewListServlet");
            } else {
                response.sendError(490, "Клиент с таким адресом уже есть в базе данных");
            }
        }
    }
}