<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Authorization page</title>
</head>
<body>
<form action="Authorization-servlet" method="post" align="center" autocomplete="off">
  <p><h1><%= "Authorization" %></h1></p>
  <p><label>Username</label></p>
  <p><input type="text" name="username" placeholder="username" required autocomplete="off"></p>
  <p><label>Password</label></p>
  <p><input type="password" name="password" placeholder="password" required autocomplete="off"></p>
  <p><input type="submit" value="Sing in"></p>
</form>

<%--<form action="Registration.jsp" method="get" align="center" autocomplete="off">--%>
<%--    <p><input type="submit" value="Registration"></p>--%>
<%--</form>--%>
</body>
</html>