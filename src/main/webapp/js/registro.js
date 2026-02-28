document.addEventListener('DOMContentLoaded', function () {
    const emailInput = document.getElementById('email');
    const nifInput = document.getElementById('nif');
    const form = document.getElementById('registroForm');

    // Validación AJAX para comprobar si el email existe

    if (emailInput) {
        emailInput.addEventListener('blur', function () {
            const email = this.value;
            if (email) {
                fetch(`CheckEmailServlet?email=${encodeURIComponent(email)}`)
                    .then(response => response.json())
                    .then(data => {
                        const errorSpan = document.getElementById('email-error-msg');
                        if (data.exists) {
                            if (!errorSpan) {
                                const span = document.createElement('span');
                                span.id = 'email-error-msg';
                                span.style.color = 'red';
                                span.style.fontSize = '0.9em';
                                span.textContent = ' Este email ya está registrado.';
                                emailInput.parentNode.appendChild(span);
                            }
                        } else {
                            if (errorSpan) errorSpan.remove();
                        }
                    })
                    .catch(error => console.error('Error comprobando email:', error));
            }
        });
    }

    // Cálculo automático de la letra del DNI (AJAX)
    
    if (nifInput) {
        nifInput.addEventListener('blur', function () {
            let valor = this.value.replace(/[^0-9]/g, '');

            if (valor.length >= 7 && valor.length <= 8) {
                fetch(`CalculoNifServlet?dni=${valor}`)
                    .then(response => response.json())
                    .then(data => {
                        const infoSpan = document.getElementById('nif-info-msg');
                        if (data.status === 'ok') {
                            if (nifInput.value.toUpperCase() !== data.nifCompleto) {
                                nifInput.value = data.nifCompleto;
                                if (!infoSpan) {
                                    const span = document.createElement('span');
                                    span.id = 'nif-info-msg';
                                    span.style.color = 'green';
                                    span.style.fontSize = '0.9em';
                                    span.textContent = ' Letra calculada autom.';
                                    nifInput.parentNode.appendChild(span);
                                    setTimeout(() => span.remove(), 3000);
                                }
                            }
                        } else {
                            console.error("Error nif:", data.message);
                        }
                    })
                    .catch(error => console.error('Error AJAX NIF:', error));
            }
        });
    }

});
