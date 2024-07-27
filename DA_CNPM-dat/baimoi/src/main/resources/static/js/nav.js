document.addEventListener("DOMContentLoaded", function() {
    var links = document.querySelectorAll('.nav-link');
    var currentPath = window.location.pathname;

    links.forEach(function(link) {
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('active');
        }
    });

    const canhanImage = document.getElementById('canhanImage');
    const thongbaoForm = document.getElementById('thongbaoForm');
    const overlay = document.getElementById('overlay');

    if (canhanImage) {
        canhanImage.addEventListener('click', function() {
            if (overlay) {
                overlay.style.display = 'flex';
            }
        });
    }

    if (overlay) {
        overlay.addEventListener('click', function(event) {
            if (event.target === overlay) {
                overlay.style.display = 'none';
            }
        });
    }
});

function confirmDelete(url) {
    if (confirm("Bạn có chắc chắn muốn hủy lời đăng ký đối tác không?")) {
        window.location.href = url;
    }
}
