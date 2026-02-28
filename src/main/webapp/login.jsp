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

            <body>

                <header>
                    <div class="logo">
                        <a href="lista" class="header-logo-link">
                            <img src="Imagenes/Logo-Tienda2.png" alt="Logo VeraByte" class="header-logo-img">
                            VeraByte
                        </a>
                    </div>
                    <nav>
                        <c:choose>
                            <c:when test="${not empty sessionScope.usuario}">
                                <div class="header-user-info">
                                    <img src="avatar?id=${sessionScope.usuario.userId}&t=<%= System.currentTimeMillis() %>"
                                        alt="Avatar" class="header-avatar">
                                    <span>Bienvenido, <strong>${sessionScope.usuario.username}</strong></span>
                                </div>
                                <a href="carrito.jsp" class="nav-cart-link">Mi Carrito</a>
                                <a href="mis-pedidos" class="mr-15 font-bold">Mis Pedidos</a>
                                <a href="UpdateUserServlet">Modificar Datos</a>
                                <a href="logout">Cerrar Sesión</a>
                            </c:when>
                            <c:otherwise>
                                <a href="carrito.jsp" class="nav-cart-link">Mi Carrito</a>
                                <a href="login.jsp">Iniciar Sesión</a>
                                <a href="registro.jsp">Registrarse</a>
                            </c:otherwise>
                        </c:choose>
                    </nav>
                </header>

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

                        <div class="text-center">
                            <button type="submit" class="btn-submit">Entrar</button>
                        </div>
                    </form>

                    <div class="links">
                        <a href="index">Volver al inicio</a>
                        <p>¿No tienes cuenta? <a href="registro.jsp">Regístrate aquí</a></p>

                    </div>
                </div>

            </body>

            </html>