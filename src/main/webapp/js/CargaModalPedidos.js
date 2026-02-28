document.addEventListener("DOMContentLoaded", () => {
    document.addEventListener("click", handlePedidoClick);
});

async function handlePedidoClick(e) {
    const item = e.target.closest(".pedidos-tr");

    if (!item || !item.hasAttribute("data-id")) return;

    const id = item.dataset.id;

    try {
        const html = await obtenerDetallePedido(id);
        mostrarModalPedido(html);
    } catch (error) {
        console.error(error);
        mostrarModalPedido("<p class='text-danger text-center'>Error al cargar los detalles del pedido.</p>");
    }
}

async function obtenerDetallePedido(id) {
    const response = await fetch(`detalle-pedido?id=${id}`);

    if (!response.ok) {
        throw new Error("Error al obtener los detalles del pedido");
    }

    return await response.text();
}

function mostrarModalPedido(html) {
    document.querySelector("#modalPedidoBody").innerHTML = html;

    const modal = bootstrap.Modal.getOrCreateInstance(
        document.querySelector("#pedidoModal")
    );

    modal.show();
}
