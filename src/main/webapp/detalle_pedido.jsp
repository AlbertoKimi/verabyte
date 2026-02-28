<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ page isELIgnored="false" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

                <div class="table-responsive">
                    <table class="table table-striped table-hover mt-3">
                        <thead>
                            <tr>
                                <th>Producto</th>
                                <th>Precio Ud.</th>
                                <th>Cantidad</th>
                                <th>Subtotal</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="totalSuma" value="0" />
                            <c:choose>
                                <c:when test="${not empty lineasPedido}">
                                    <c:forEach var="linea" items="${lineasPedido}">
                                        <tr>
                                            <td>
                                                <div class="item-producto-container">
                                                    <img src="${linea.imagen}" alt="${linea.nombre}"
                                                        class="item-producto-imagen">
                                                    <span>${linea.nombre}</span>
                                                </div>
                                            </td>
                                            <td>${linea.precio} €</td>
                                            <td>${linea.cantidad}</td>
                                            <td>
                                                <fmt:formatNumber value="${linea.precio * linea.cantidad}"
                                                    maxFractionDigits="2" /> €
                                            </td>
                                        </tr>
                                        <c:set var="totalSuma" value="${totalSuma + (linea.precio * linea.cantidad)}" />
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="4" class="text-center">No se encontraron detalles para este pedido.
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                        <c:if test="${not empty lineasPedido}">
                            <tfoot>
                                <tr>
                                    <th colspan="3" class="text-end">Total Productos (sin IVA):</th>
                                    <th>
                                        <fmt:formatNumber value="${totalSuma}" maxFractionDigits="2" /> €
                                    </th>
                                </tr>
                            </tfoot>
                        </c:if>
                    </table>
                </div>