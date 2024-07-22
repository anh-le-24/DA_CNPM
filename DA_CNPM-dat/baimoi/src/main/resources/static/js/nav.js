document.addEventListener("DOMContentLoaded", function() {
    var links = document.querySelectorAll('.nav-link');
    var currentPath = window.location.pathname;

    links.forEach(function(link) {
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('active');
        }
    });
});
