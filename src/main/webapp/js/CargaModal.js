document.addEventListener("DOMContentLoaded", () => {
    document.addEventListener("click", handleItemClick);
});

async function handleItemClick(e) {

    const item = e.target.closest(".producto-card");
    if (!item) return;

    if (e.target.closest("form") || e.target.closest("button")) return;

    const id = item.dataset.id;

    try {
        const html = await obtenerDetalle(id);
        mostrarModal(html);
    } catch (error) {
        console.error(error);
    }
}

async function obtenerDetalle(id) {
    const response = await fetch(
        `detalle?id=${id}`
    );

    if (!response.ok) {
        throw new Error("Error al obtener el detalle");
    }

    return await response.text();
}

function mostrarModal(html) {
    document.querySelector("#modalBody").innerHTML = html;

    const modal = bootstrap.Modal.getOrCreateInstance(
        document.querySelector("#pokemonModal")
    );

    modal.show();
}