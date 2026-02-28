<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ page isELIgnored="false" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <!DOCTYPE html>
            <html lang="es">

            <head>
                <meta charset="UTF-8">
                <c:set var="isEdit" value="${requestScope.isEdit != null ? requestScope.isEdit : false}" />
                <title>${isEdit ? 'Modificar Perfil' : 'Registro de Usuario'}</title>
                <link rel="stylesheet" href="css/styles.css">
                <c:if test="${not isEdit}">
                    <script src="js/registro.js" defer></script>
                </c:if>
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

                <c:set var="errores" value="${sessionScope.errores}" />
                <c:set var="formulario" value="${sessionScope.formulario}" />
                <c:remove var="errores" scope="session" />
                <c:remove var="formulario" scope="session" />

                <c:if test="${not empty sessionScope.error}">
                    <c:set var="errorGeneral" value="${sessionScope.error}" />
                    <c:remove var="error" scope="session" />
                </c:if>

                <div class="register-container">
                    <h2>${isEdit ? 'Modificar Mis Datos' : 'Registro'}</h2>

                    <c:if test="${not empty errores.general}">
                        <div class="error-message-container">${errores.general}
                        </div>
                    </c:if>
                    <c:if test="${not empty errorGeneral}">
                        <div class="error-message-container">${errorGeneral}
                        </div>
                    </c:if>
                    <c:if test="${not empty sessionScope.message}">
                        <div class="success-message">${sessionScope.message}
                        </div>
                        <c:remove var="message" scope="session" />
                    </c:if>

                    <form action="${isEdit ? 'UpdateUserServlet' : 'RegisterServlet'}" method="post" id="registroForm"
                        enctype="multipart/form-data">
                        <div class="form-grid">
                            <div class="form-group full-width">
                                <label for="email">Email:</label>
                                <input type="email" id="email" name="email" value="${formulario.email}" ${isEdit
                                    ? 'readonly class="readonly-input"' : '' }>
                                <c:if test="${isEdit}"><small>El email no se puede modificar.</small></c:if>
                                <c:if test="${not empty errores.email}">
                                    <span class="error-message-text">${errores.email}</span>
                                </c:if>
                            </div>

                            <c:if test="${isEdit}">
                                <div class="form-group full-width">
                                    <label for="current_password">Contraseña Actual:</label>
                                    <input type="password" id="current_password" name="current_password">
                                    <c:if test="${not empty errores.current_password}">
                                        <span class="error-message-text">${errores.current_password}</span>
                                    </c:if>
                                </div>
                            </c:if>

                            <div class="form-group">
                                <label for="password">${isEdit ? 'Nueva Contraseña (Opcional):' : 'Contraseña
                                    (Cliente):'}</label>
                                <input type="password" id="password" name="password">
                                <span id="password-error"></span>
                                <c:if test="${not empty errores.password}">
                                    <span class="error-message-text">${errores.password}</span>
                                </c:if>
                                <c:if test="${not empty errores.passwordSeguridad}">
                                    <span class="error-message-text">${errores.passwordSeguridad}</span>
                                </c:if>
                            </div>

                            <div class="form-group">
                                <label for="confirm_password">${isEdit ? 'Repetir Nueva Contraseña:' : 'Repetir
                                    Contraseña:'}</label>
                                <input type="password" id="confirm_password" name="confirm_password">
                                <span id="password-match-error" class="error-message-text" style="display: none;">Las
                                    contraseñas no coinciden</span>
                            </div>

                            <div class="form-group">
                                <label for="nombre">Nombre:</label>
                                <input type="text" id="nombre" name="nombre" value="${formulario.nombre}">
                                <c:if test="${not empty errores.nombre}">
                                    <span class="error-message-text">${errores.nombre}</span>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label for="apellidos">Apellidos:</label>
                                <input type="text" id="apellidos" name="apellidos" value="${formulario.apellidos}">
                                <c:if test="${not empty errores.apellidos}">
                                    <span class="error-message-text">${errores.apellidos}</span>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label for="nif">NIF (Solo números):</label>
                                <input type="text" id="nif" name="nif" value="${formulario.nif}" ${isEdit
                                    ? 'readonly class="readonly-input"' : '' }>
                                <c:if test="${isEdit}"><small>El NIF no se puede modificar.</small></c:if>
                                <c:if test="${not empty errores.nif}">
                                    <span class="error-message-text">${errores.nif}</span>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label for="telefono">Teléfono:</label>
                                <input type="tel" id="telefono" name="telefono" value="${formulario.telefono}">
                                <c:if test="${not empty errores.telefono}">
                                    <span class="error-message-text">${errores.telefono}</span>
                                </c:if>
                            </div>
                            <div class="form-group full-width">
                                <label for="direccion">Dirección:</label>
                                <input type="text" id="direccion" name="direccion" value="${formulario.direccion}">
                            </div>
                            <div class="form-group">
                                <label for="cp">Código Postal:</label>
                                <input type="text" id="cp" name="cp" value="${formulario.cp}">
                                <c:if test="${not empty errores.cp}">
                                    <span class="error-message-text">${errores.cp}</span>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label for="localidad">Localidad:</label>
                                <input type="text" id="localidad" name="localidad" value="${formulario.localidad}">
                            </div>
                            <div class="form-group">
                                <label for="provincia">Provincia:</label>
                                <input type="text" id="provincia" name="provincia" value="${formulario.provincia}">
                            </div>

                            <div class="form-group full-width">
                                <label for="avatar">Avatar (Opcional):</label>
                                <input type="file" id="avatar" name="avatar" accept=".jpg, .jpeg, .png, .webp">
                                <c:if test="${not empty errores.avatar}">
                                    <span class="error-message-text">${errores.avatar}</span>
                                </c:if>
                            </div>
                        </div>

                        <div class="text-center">
                            <button type="submit" class="btn-submit">${isEdit ? 'Guardar Cambios' :
                                'Registrarse'}</button>
                        </div>
                    </form>

                    <div class="links">
                        <c:if test="${not isEdit}">
                            <a href="login.jsp">¿Ya tienes cuenta? Inicia sesión</a>
                            <br><br>
                        </c:if>
                        <a href="index">${isEdit ? 'Cancelar y volver al inicio' : 'Volver al inicio'}</a>
                    </div>
                </div>

            </body>

            </html>