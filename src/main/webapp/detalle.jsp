<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ page isELIgnored="false" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

            <c:if test="${not empty producto}">
                <div class="detalle-modal-container" style="position: relative;">
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"
                        style="position: absolute; top: 15px; right: 15px;"></button>
                    <img src="${producto.imagen}" alt="${producto.nombre}" class="detalle-modal-img">

                    <div class="detalle-modal-info">
                        <h3>${producto.nombre}</h3>
                        <p class="marca">${producto.marca}</p>
                        <p class="desc">${producto.descripcion}</p>
                        <p class="precio">${producto.precio} €</p>

                        <form method="POST" action="carrito"
                            style="width: 100%; max-width: 300px; margin: 0 auto; text-align: center;">
                            <input type="hidden" name="idProducto" value="${producto.idProducto}">
                            <input type="hidden" name="nombre" value="${producto.nombre}">
                            <input type="hidden" name="precio" value="${producto.precio}">
                            <input type="hidden" name="imagen" value="${producto.imagen}">
                            <button type="submit" class="btn-comprar-store">Comprar</button>
                        </form>
                    </div>
                </div>
            </c:if>