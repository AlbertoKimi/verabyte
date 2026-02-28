async function updateCart(idProducto, action) {
    try {
        const response = await fetch('ajax/cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'idProducto=' + idProducto + '&action=' + action
        });

        const data = await response.json();

        if (data.status === 'success') {

            const subtotalSinIva = data.total;
            const iva = subtotalSinIva * 0.21;
            const totalConIva = subtotalSinIva * 1.21;

            const totalEl = document.getElementById('cart-total');
            if (totalEl) totalEl.innerText = parseFloat(totalConIva).toFixed(2);

            const subtotalEl = document.getElementById('cart-subtotal');
            if (subtotalEl) subtotalEl.innerText = parseFloat(subtotalSinIva).toFixed(2);

            const ivaEl = document.getElementById('cart-iva');
            if (ivaEl) ivaEl.innerText = parseFloat(iva).toFixed(2);

            if (action === 'aumentar' || action === 'disminuir') {
                let qtySpan = document.getElementById('qty-' + idProducto);
                if (qtySpan) qtySpan.innerText = data.itemCantidad;

                let itemSubSpan = document.getElementById('sub-' + idProducto);
                if (itemSubSpan) itemSubSpan.innerText = parseFloat(data.itemSubtotal).toFixed(2);

                let btnMinus = document.querySelector(`#row-${idProducto} button[onclick*="disminuir"]`);
                if (btnMinus) {
                    btnMinus.disabled = data.itemCantidad <= 1;
                }
            } else if (action === 'eliminar') {
                let row = document.getElementById('row-' + idProducto);
                if (row) row.remove();
            }

            if (data.total == 0) {
                window.location.reload();
            }

        } else {
            alert('Error: ' + data.message);
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function vaciarCarrito() {
    try {
        const response = await fetch('ajax/cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'action=vaciar'
        });

        const data = await response.json();

        if (data.status === 'success') {
            window.location.reload();
        } else {
            alert('Error: ' + data.message);
        }
    } catch (error) {
        console.error('Error:', error);
    }
}
