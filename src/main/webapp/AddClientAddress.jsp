<%--
  Created by IntelliJ IDEA.
  User: evgeniysharychenkov
  Date: 16.05.2023
  Time: 00:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="Create" method="get" align="center" autocomplete="off">
    <p><h1> Add client address</h1></p>
    <p>
        <input type="hidden" name="clientId" value="<%= request.getParameter("hidden") %>">
    </p>
    <table align="center" cellpadding="5">
        <tr>
            <td><label>IP-адрес (IPv4)</label></td>
            <td><input type="text" name="ip" required autocomplete="off" title="пример: 192.168.000.001">
                243.111.123.14
            </td>
        </tr>
        <tr>
            <td><label>Mac-адрес</label></td>
            <td><input type="text" name="mac" required autocomplete="off">
                77-6s-52-7f-ja-4h
            </td>
        </tr>
        <tr>
            <td><label>Model</label></td>
            <td><input type="text" name="model" required autocomplete="off">
                model2
            </td>
        </tr>
        <tr>
            <td><label>Address</label></td>
            <td><input type="text" name="address" required autocomplete="off">
                Moscow
            </td>
        </tr>
    </table>
    <p><input type="submit" value="Save"></p>
</form>
</body>
</html>
