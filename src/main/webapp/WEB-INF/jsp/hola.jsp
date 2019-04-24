<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<h1>Wakanda forever ${mensaje}</h1>
<form action="/prueba" method="post">
    Genero: <input type="text" name="genero"/>
    <button type="submit">Buscar</button>
</form>

<table border="1">
    <tr>
        <th>Nombre</th>
        <th>Autor</th>
        <th>Estilo</th>
    </tr>
    <c:forEach items="${lista}" var="cancion">
        <tr>
            <td>${cancion.nombre}</td>
            <td>${cancion.autor}</td>
            <td>${cancion.genero}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>