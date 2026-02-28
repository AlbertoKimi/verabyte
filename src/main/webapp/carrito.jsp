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
                        <h2
                            style="font-family: var(--font-heading); text-transform: uppercase; border-bottom: var(--border-width) solid var(--border-dark); padding-bottom: 0.5rem; margin-bottom: 1rem; text-align: center;">
                            Mi Carrito de Compras</h2>

                        <c:choose>
                            <c:when test="${not empty sessionScope.carrito and not empty sessionScope.carrito.items}">
                                <div class="carrito-cart-wrapper" style="margin-bottom: 20px;">
                                    <table class="carrito-table">
                                        <thead>
                                            <tr>
                                                <th style="padding: 10px 15px;">Producto</th>
                                                <th style="padding: 10px 15px;">Precio</th>
                                                <th style="padding: 10px 15px;">Cantidad</th>
                                                <th style="padding: 10px 15px;">Subtotal</th>
                                                <th style="padding: 10px 15px;">Acciones</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="item" items="${sessionScope.carrito.items}">
                                                <tr id="row-${item.idProducto}">
                                                    <td style="padding: 10px 15px;">
                                                        <div class="item-producto-container">
                                                            <img src="${item.imagen}" alt="${item.nombre}"
                                                                style="width: 45px; height: 45px; object-fit: contain; border: 2px solid var(--border-dark); border-radius: 4px; background: #fff;">
                                                            <span style="font-size: 0.95rem;">${item.nombre}</span>
                                                        </div>
                                                    </td>
                                                    <td style="padding: 10px 15px;">${item.precio} €</td>
                                                    <td style="padding: 10px 15px; white-space: nowrap;">
                                                        <button class="btn-qty"
                                                            style="padding: 2px 10px; font-size: 1rem;"
                                                            onclick="updateCart('${item.idProducto}', 'disminuir')"
                                                            <c:if test="${item.cantidad <= 1}">disabled</c:if>
                                                            >-</button>
                                                        <span id="qty-${item.idProducto}"
                                                            style="display: inline-block; min-width: 30px; text-align: center; font-weight: bold; margin: 0 10px;">${item.cantidad}</span>
                                                        <button class="btn-qty"
                                                            style="padding: 2px 10px; font-size: 1rem;"
                                                            onclick="updateCart('${item.idProducto}', 'aumentar')">+</button>
                                                    </td>
                                                    <td style="padding: 10px 15px;"><span
                                                            id="sub-${item.idProducto}">${item.precio *
                                                            item.cantidad}</span>
                                                        €</td>
                                                    <td style="padding: 10px 15px;">
                                                        <button class="btn-remove"
                                                            style="padding: 6px 12px; font-size: 0.85rem;"
                                                            onclick="updateCart('${item.idProducto}', 'eliminar')">
                                                            ELIMINAR</button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                    <div class="resumen-container"
                                        style="box-shadow: none; border: none; padding: 15px 0; margin: 0; background: transparent;">
                                        <div class="resumen-item" style="margin-bottom: 5px; font-size: 0.9rem;">
                                            <span class="resumen-item-label">Subtotal (Base Imponible)</span>
                                            <span>
                                                <span id="cart-subtotal">
                                                    <fmt:formatNumber value="${sessionScope.carrito.total}"
                                                        type="number" minFractionDigits="2" maxFractionDigits="2" />
                                                </span> €
                                            </span>
                                        </div>
                                        <div class="resumen-item" style="margin-bottom: 5px; font-size: 0.9rem;">
                                            <span class="resumen-item-label">IVA (21%)</span>
                                            <span>
                                                <span id="cart-iva">
                                                    <fmt:formatNumber value="${sessionScope.carrito.total * 0.21}"
                                                        type="number" minFractionDigits="2" maxFractionDigits="2" />
                                                </span> €
                                            </span>
                                        </div>
                                        <div class="resumen-total"
                                            style="margin-top: 15px; margin-bottom: 0; margin-left: auto; margin-right: 0; padding: 6px 15px; font-size: 1.1rem; max-width: 300px;">
                                            <span style="margin-right: 20px;">TOTAL A PAGAR</span>
                                            <span>
                                                <span id="cart-total">
                                                    <fmt:formatNumber value="${sessionScope.carrito.total * 1.21}"
                                                        type="number" minFractionDigits="2" maxFractionDigits="2" />
                                                </span> €
                                            </span>
                                        </div>
                                    </div>
                                </div>

                                <div class="action-buttons">
                                    <button type="button" class="btn-action btn-danger"
                                        style="padding: 8px 16px; font-size: 0.85rem;" onclick="vaciarCarrito()">Vaciar
                                        Carrito</button>
                                    <button class="btn-action btn-volver" style="padding: 8px 16px; font-size: 0.85rem;"
                                        onclick="window.location.href='lista'">Seguir
                                        Comprando</button>

                                    <c:choose>
                                        <c:when test="${not empty sessionScope.usuario}">
                                            <form action="checkout" method="POST" style="margin: 0;">
                                                <button type="submit" class="btn-action btn-checkout"
                                                    style="padding: 8px 16px; font-size: 0.85rem;">💳 Pagar y
                                                    Confirmar Pedido</button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="checkout-disabled-container"
                                                style="display: flex; align-items: center; gap: 10px;">
                                                <span class="checkout-disabled-text" style="font-size: 0.8rem;">Debes
                                                    iniciar sesión para comprar</span>
                                                <button type="button" class="btn-action btn-checkout-disabled"
                                                    style="padding: 8px 16px; font-size: 0.85rem;" disabled>💳 PAGAR Y
                                                    CONFIRMAR PEDIDO</button>
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