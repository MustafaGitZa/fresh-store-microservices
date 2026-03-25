// Auto hide alerts after 5 seconds
document.addEventListener('DOMContentLoaded', function () {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.transition = 'opacity 0.5s';
            alert.style.opacity = '0';
            setTimeout(() => alert.remove(), 500);
        }, 5000);
    });
});

// Confirm before placing order
function confirmOrder(productName, quantity, price) {
    return confirm(`Place order for ${quantity}x ${productName} at R${price}?`);
}

// Update total price when quantity changes
function updateTotal(price, inputId, totalId) {
    const input = document.getElementById(inputId);
    const total = document.getElementById(totalId);
    if (input && total) {
        input.addEventListener('input', function () {
            const qty = parseInt(this.value) || 0;
            total.value = (qty * price).toFixed(2);
        });
    }
}