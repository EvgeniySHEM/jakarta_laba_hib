<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="Create" method="post" align="center" autocomplete="off">
    <p>
    <h1><%= "Registration client" %>
    </h1></p>
    <table align="center" cellpadding="5">
        <tr>
            <td><label>Client name</label></td>
            <td><input type="text" name="clientName" required autocomplete="off">
                Макс
            </td>
        </tr>
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
        <tr>
            <td><label>Type</label></td>
            <td><select name="select">
                <option value="Физическое лицо">Физическое лицо</option>
                <option value="Юридическое лицо">Юридическое лицо</option>
            </select></td>
        </tr>
        <tr>
            <td><label>Registration date</label></td>
            <td><input type="date" name="date" autocomplete="off" required></td>
        </tr>
    </table>
    <p><input type="submit" value="Save"></p>
</form>
</body>
</html>