document.addEventListener("DOMContentLoaded", function() {
    var links = document.querySelectorAll('.nav-link');
    var currentPath = window.location.pathname;

    links.forEach(function(link) {
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('active');
        }
    });
});

document.addEventListener('DOMContentLoaded', function() {
    const canhanImage = document.getElementById('canhanImage');
    const thongbaoForm = document.getElementById('thongbaoForm');
    const overlay = document.getElementById('overlay');

    canhanImage.addEventListener('click', function() {
        overlay.style.display = 'flex';
    });

    overlay.addEventListener('click', function(event) {
        if (event.target === overlay) {
            overlay.style.display = 'none';
        }
    });
});


