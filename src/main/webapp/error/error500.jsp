<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <c:set var="ctx" value="${pageContext.request.contextPath}" />

        <!DOCTYPE html>
        <html lang="es">

        <head>
            <title>Error 500 - Internal Server Error</title>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="${ctx}/css/styles.css">
        </head>

        <body class="error-500-body">
            <div class="error-500-container">
                <h1>500</h1>
                <h2>¡Error del Servidor!</h2>
                <img src="${ctx}/error/ira.gif" alt="Panda enfadado" class="error-gif" />
                <p>Algo se ha roto en el servidor. El oso panda está tan cabreado que ha destruido los cables...
                    ¡Nuestro equipo técnico ya está en ello!</p>
                <a href="${ctx}/" class="btn-volver-inicio">Volver al inicio</a>
            </div>
        </body>

        </html>