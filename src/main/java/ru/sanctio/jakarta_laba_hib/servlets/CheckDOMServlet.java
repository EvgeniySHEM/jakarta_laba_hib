package ru.sanctio.jakarta_laba_hib.servlets;

import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ru.sanctio.jakarta_laba_hib.beans.SelectBeanLocal;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "CheckDOM", value = "/CheckDOM")
public class CheckDOMServlet extends HttpServlet {

    @EJB
    SelectBeanLocal selectBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String filterXML = request.getParameter("filterXML");
        String xmlFile = request.getParameter("xmlFile");

        List<ClientEntity> clients = selectBean.readXML(xmlFile, filterXML);
        if(selectBean.checkClients(clients, response)) {
            return;
        }

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<table align=\"center\" cellpadding=\"5\" border=\"5px double #000\" cellspacing=\"0\">");
        out.println("<tr>");
        out.println("<th>clientId</th>");
        out.println("<th>clientName</th>");
        out.println("<th>type</th>");
        out.println("<th>added</th>");
        out.println("<th>ip</th>");
        out.println("<th>mac</th>");
        out.println("<th>model</th>");
        out.println("<th>address</th>");
        out.println("</tr>");
        for (ClientEntity client : clients) {
            for (AddressEntity address : client.getAddresses()) {
                out.println("<tr>");
                out.println("<td>" + client.getClientId() + "</td>");
                out.println("<td>" + client.getClientName() + "</td>");
                out.println("<td>" + client.getType() + "</td>");
                out.println("<td>" + client.getAdded() + "</td>");
                out.println("<td>" + address.getIp() + "</td>");
                out.println("<td>" + address.getMac() + "</td>");
                out.println("<td>" + address.getModel() + "</td>");
                out.println("<td>" + address.getAddress() + "</td>");
                out.println("</tr>");
            }
        }
        out.println("</table>");;
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}