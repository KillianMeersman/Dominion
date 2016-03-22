<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dominion - login</title>
</head>
<body>
<h1>Dominion login</h1>
<form action="LoginController" method="post">
<input type="text" id="username" name="username" placeholder="Username" required />
<input type="password" id="password" name="password" placeholder="Password" required />
<input type="submit" value="Submit" />
</form>
</body>
</html>