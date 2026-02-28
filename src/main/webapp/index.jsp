<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ page isELIgnored="false" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <!DOCTYPE html>
            <html lang="es">

            <head>
                <meta charset="UTF-8">
                <title>VeraByte - Inicio</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
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

                <div class="container">
                    <h1>Bienvenido a VeraByte</h1>
                    <c:if test="${not empty sessionScope.message}">
                        <div class="success-message">
                            ${sessionScope.message}
                        </div>
                        <c:remove var="message" scope="session" />
                    </c:if>

                    <form method="GET" action="lista" class="filters-form">
                        <div class="filter-group">
                            <label for="categoria">Filtrar por categoría</label>
                            <select name="categoria" id="categoria">
                                <option value="">Todas las categorías</option>
                                <c:forEach var="cat" items="${categorias}">
                                    <option value="${cat.idCategoria}" ${paramCategoria==cat.idCategoria ? 'selected'
                                        : '' }>${cat.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="filter-group">
                            <label for="marca">Filtrar por marca</label>
                            <select name="marca" id="marca">
                                <option value="">Todas las marcas</option>
                                <c:forEach var="m" items="${marcas}">
                                    <option value="${m}" ${paramMarca==m ? 'selected' : '' }>${m}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="filter-group">
                            <label>Filtrar por precio (€)</label>
                            <div style="display: flex; gap: 5px;">
                                <input type="number" name="precioMin" id="precioMin" placeholder="Mín."
                                    value="${paramPrecioMin}" step="1"
                                    style="width: 80px; padding: 10px; border: 1px solid #ddd; border-radius: 5px; outline: none; transition: border-color 0.3s;">
                                <input type="number" name="precioMax" id="precioMax" placeholder="Máx."
                                    value="${paramPrecioMax}" step="1"
                                    style="width: 80px; padding: 10px; border: 1px solid #ddd; border-radius: 5px; outline: none; transition: border-color 0.3s;">
                            </div>
                        </div>
                        <div class="filter-group">
                            <label for="nombre">Filtrar por nombre</label>
                            <input type="text" name="nombre" id="nombre" placeholder="Buscar por nombre..."
                                value="${paramNombre}">
                        </div>
                        <button type="submit" class="btn-filtrar"
                            style="align-self: flex-end; margin-bottom: 1px;">Filtrar</button>
                        <a href="lista" class="btn-limpiar"
                            style="align-self: flex-end; margin-bottom: 1px; display: flex; align-items: center; justify-content: center; box-sizing: border-box;">Limpiar</a>
                    </form>

                    <div class="productos-grid">
                        <c:choose>
                            <c:when test="${not empty productos}">
                                <c:forEach var="producto" items="${productos}">
                                    <div class="producto-card" data-id="${producto.idProducto}"
                                        style="cursor: pointer;">
                                        <div class="producto-img-container">
                                            <img src="${producto.imagen}" alt="${producto.nombre}" class="producto-img">
                                        </div>
                                        <div class="producto-info">
                                            <h3 class="producto-nombre">${producto.nombre}</h3>
                                            <p class="producto-marca">${producto.marca}</p>
                                            <p class="producto-precio">${producto.precio} €</p>
                                            <form method="POST" action="carrito"
                                                style="margin-top: 10px; text-align: center;">
                                                <input type="hidden" name="idProducto" value="${producto.idProducto}">
                                                <input type="hidden" name="nombre" value="${producto.nombre}">
                                                <input type="hidden" name="precio" value="${producto.precio}">
                                                <input type="hidden" name="imagen" value="${producto.imagen}">
                                                <button type="submit" class="btn-comprar-store">Comprar</button>
                                            </form>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p>No hay productos disponibles actualmente.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="modal fade" id="pokemonModal" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-lg">
                        <div class="modal-content">
                            <div class="modal-body" id="modalBody">
                                <div class="loading-pixel">LOADING...</div>
                            </div>
                        </div>
                    </div>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
                <script src="js/CargaModal.js"></script>

            </body>

            </html>