<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <c:set var="ctx" value="${pageContext.request.contextPath}" />

        <!DOCTYPE html>
        <html lang="es">

        <head>
            <title>Error 404 - Página no encontrada</title>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="${ctx}/css/styles.css">
        </head>

        <body class="error-404-body">
            <div class="error-404-container">
                <h1>404</h1>
                <h2>¡Ups! Página no encontrada.</h2>
                <img src="${ctx}/error/cat-error.gif" alt="Gato triste" class="error-gif" />
                <p>El gato con la tostada se ha caído y no da crédito... ¡No encontramos esta
                    página!</p>
                <a href="${ctx}/" class="btn-volver-inicio">Volver al inicio</a>
            </div>
        </body>

        </html>