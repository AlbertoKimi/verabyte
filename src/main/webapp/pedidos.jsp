<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ page isELIgnored="false" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                <!DOCTYPE html>
                <html lang="es">

                <head>
                    <meta charset="UTF-8">
                    <title>VeraByte - Mis Pedidos</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
                        rel="stylesheet">
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
                                            alt="Avatar"
                                            style="width: 40px; height: 40px; border-radius: 50%; object-fit: cover;"
                                            class="header-avatar">
                                        <span>Bienvenido, <strong>${sessionScope.usuario.username}</strong></span>
                                    </div>
                                    <a href="carrito.jsp" class="font-bold">Mi Carrito</a>
                                    <a href="mis-pedidos" class="mr-15 font-bold">Mis
                                        Pedidos</a>
                                    <a href="UpdateUserServlet">Modificar Datos</a>
                                    <a href="logout">Cerrar Sesión</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="carrito.jsp" class="mr-15 font-bold">🛒 Mi
                                        Carrito</a>
                                    <a href="login.jsp">Iniciar Sesión</a>
                                    <a href="registro.jsp">Registrarse</a>
                                </c:otherwise>
                            </c:choose>
                        </nav>
                    </header>

                    <div class="container pedidos-container-custom">
                        <h2>Mis Pedidos</h2>

                        <c:choose>
                            <c:when test="${not empty misPedidos}">
                                <table class="pedidos-table">
                                    <thead>
                                        <tr class="pedidos-th-row">
                                            <th class="pedidos-th">ID Pedido</th>
                                            <th class="pedidos-th">Fecha</th>
                                            <th class="pedidos-th">Total</th>
                                            <th class="pedidos-th">Estado</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="pedido" items="${misPedidos}">
                                            <tr class="pedidos-tr" data-id="${pedido.idPedido}"
                                                style="cursor: pointer;">
                                                <td class="pedidos-td">#${pedido.idPedido}</td>
                                                <td class="pedidos-td">
                                                    <fmt:formatDate value="${pedido.fecha}" pattern="dd/MM/yyyy" />
                                                </td>
                                                <td class="pedidos-td">
                                                    <fmt:formatNumber value="${pedido.importe + pedido.iva}"
                                                        minFractionDigits="2" maxFractionDigits="2" /> €
                                                </td>
                                                <td class="pedidos-td">
                                                    <c:choose>
                                                        <c:when test="${pedido.estado == 'f'}">
                                                            <span class="status-completed">Completado</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="status-pending">Pendiente</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <div class="empty-pedidos">
                                    <p class="mb-15">Aún no has realizado ningún pedido.</p>
                                    <a href="lista" class="btn-primary-link">Ir a la tienda</a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Modal Detalles del Pedido -->
                    <div class="modal fade" id="pedidoModal" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Detalles del Pedido</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body" id="modalPedidoBody">
                                    <div class="text-center">Cargando detalles...</div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
                    <script src="js/CargaModalPedidos.js"></script>
                </body>

                </html>