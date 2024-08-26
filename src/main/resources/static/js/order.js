function updateOrderStatus(orderId, status) {
    fetch(`/order/update-status?orderId=${orderId}&status=${status}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            location.reload();
        })
        .catch(error => {
            console.error('Error:', error);
            alert(`Failed to ${status} order`);
        });
}
document.addEventListener('DOMContentLoaded', function() {
    const links = document.querySelectorAll('.nav-link-order');
    const sections = document.querySelectorAll('.content-section');
    links.forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            const target = link.getAttribute('data-target');
            sections.forEach(section => {
                section.style.display = 'none';
            });
            document.getElementById(target).style.display = 'block';
        });
    });
    document.getElementById('pending-orders').style.display = 'block';
});