package ru.sanctio.jakarta_laba_hib.servlets;

import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ru.sanctio.jakarta_laba_hib.xml_parsers.TransformerLocal;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CreateXMLServlet", value = "/CreateXMLServlet")
public class CreateXMLServlet extends HttpServlet {

    @EJB
    private TransformerLocal transformer;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String xmlName = request.getParameter("xmlName");
        transformer.createXml(xmlName);
        response.setStatus(200);
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>XML файл создан</h1>");
        out.println("</body></html>");
    }
}