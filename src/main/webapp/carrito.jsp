<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ page isELIgnored="false" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                <!DOCTYPE html>
                <html lang="es">

                <head>
                    <meta charset="UTF-8">
                    <title>VeraByte - Mi Carrito</title>
                    <link rel="stylesheet" href="css/styles.css">
                    <script src="js/carrito.js"></script>
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

                    <div class="carrito-container">
                        <h2>Mi Carrito de Compras</h2>

                        <c:choose>
                            <c:when test="${not empty sessionScope.carrito and not empty sessionScope.carrito.items}">
                                <table class="carrito-table">
                                    <thead>
                                        <tr>
                                            <th>Producto</th>
                                            <th>Precio</th>
                                            <th>Cantidad</th>
                                            <th>Subtotal</th>
                                            <th>Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="item" items="${sessionScope.carrito.items}">
                                            <tr id="row-${item.idProducto}">
                                                <td>
                                                    <div class="item-producto-container">
                                                        <img src="${item.imagen}" alt="${item.nombre}"
                                                            class="item-producto-imagen">
                                                        <span>${item.nombre}</span>
                                                    </div>
                                                </td>
                                                <td>${item.precio} €</td>
                                                <td>
                                                    <button class="btn-qty"
                                                        onclick="updateCart('${item.idProducto}', 'disminuir')"
                                                        <c:if test="${item.cantidad <= 1}">disabled</c:if>>-</button>
                                                    <span id="qty-${item.idProducto}"
                                                        class="padding-x-10">${item.cantidad}</span>
                                                    <button class="btn-qty"
                                                        onclick="updateCart('${item.idProducto}', 'aumentar')">+</button>
                                                </td>
                                                <td><span id="sub-${item.idProducto}">${item.precio *
                                                        item.cantidad}</span>
                                                    €</td>
                                                <td>
                                                    <button class="btn-remove"
                                                        onclick="updateCart('${item.idProducto}', 'eliminar')">
                                                        Eliminar</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <div class="resumen-container"
                                    style="margin-top: 20px; margin-bottom: 30px; box-shadow: none; padding: 0;">
                                    <div class="resumen-item">
                                        <span class="resumen-item-label">Subtotal (Base Imponible)</span>
                                        <span>
                                            <span id="cart-subtotal">
                                                <fmt:formatNumber value="${sessionScope.carrito.total}" type="number"
                                                    minFractionDigits="2" maxFractionDigits="2" />
                                            </span> €
                                        </span>
                                    </div>
                                    <div class="resumen-item">
                                        <span class="resumen-item-label">IVA (21%)</span>
                                        <span>
                                            <span id="cart-iva">
                                                <fmt:formatNumber value="${sessionScope.carrito.total * 0.21}"
                                                    type="number" minFractionDigits="2" maxFractionDigits="2" />
                                            </span> €
                                        </span>
                                    </div>
                                    <div class="resumen-total" style="margin-bottom: 20px;">
                                        <span>TOTAL A PAGAR</span>
                                        <span>
                                            <span id="cart-total">
                                                <fmt:formatNumber value="${sessionScope.carrito.total * 1.21}"
                                                    type="number" minFractionDigits="2" maxFractionDigits="2" />
                                            </span> €
                                        </span>
                                    </div>
                                </div>

                                <div class="action-buttons">
                                    <button type="button" class="btn-action btn-danger" onclick="vaciarCarrito()">Vaciar
                                        Carrito</button>
                                    <button class="btn-action btn-clear" onclick="window.location.href='lista'">Seguir
                                        Comprando</button>

                                    <c:choose>
                                        <c:when test="${not empty sessionScope.usuario}">
                                            <form action="checkout" method="POST">
                                                <button type="submit" class="btn-action btn-checkout">💳 Pagar y
                                                    Confirmar Pedido</button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="checkout-disabled-container">
                                                <span class="checkout-disabled-text">Debes
                                                    iniciar sesión para comprar</span>
                                                <button type="button" class="btn-action btn-checkout-disabled"
                                                    disabled>💳 Pagar y Confirmar Pedido</button>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="empty-cart">
                                    <p>Tu carrito está vacío.</p>
                                    <a href="lista" class="btn-primary-link mt-20">Ir a la tienda</a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>


                </body>

                </html>