<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ page isELIgnored="false" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <!DOCTYPE html>
            <html lang="es">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Iniciar Sesión - Verabyte</title>
                <link rel="stylesheet" href="css/styles.css">
            </head>

            <body class="flex-center-vh">

                <div class="login-container">
                    <h2>Iniciar Sesión</h2>

                    <c:if test="${not empty error}">
                        <div class="error-message">
                            ${error}
                        </div>
                    </c:if>

                    <form action="login" method="post">
                        <div class="form-group">
                            <label for="email">Correo Electrónico</label>
                            <input type="email" id="email" name="email" required placeholder="ejemplo@correo.com">
                        </div>

                        <div class="form-group">
                            <label for="password">Contraseña</label>
                            <input type="password" id="password" name="password" required placeholder="********">
                        </div>

                        <button type="submit" class="btn-submit">Entrar</button>
                    </form>

                    <div class="links">
                        <a href="index">Volver al inicio</a>
                        <p>¿No tienes cuenta? <a href="registro.jsp">Regístrate aquí</a></p>

                    </div>
                </div>

            </body>

            </html>