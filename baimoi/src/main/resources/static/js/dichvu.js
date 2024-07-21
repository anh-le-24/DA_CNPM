function toggleForm(formId) {
    var form = document.getElementById(formId);
    if (form) { // Kiểm tra nếu form tồn tại
        if (form.style.display === "none" || form.style.display === "") {
            form.style.display = "block";
        } else {
            form.style.display = "none";
        }
    } else {
        console.error("Element with ID '" + formId + "' not found.");
    }
}

function quaylai() {
    var themdichvuForm = document.getElementById('themdichvuForm');
    var danhsachDichvu = document.getElementsByClassName('danhsachdichvu')[0];
    if (themdichvuForm) { // Kiểm tra nếu form tồn tại
        themdichvuForm.style.display = 'none';
    } else {
        console.error("Element with ID 'themdichvuForm' not found.");
    }
    if (danhsachDichvu) { // Kiểm tra nếu danh sách tồn tại
        danhsachDichvu.style.display = 'block';
    } else {
        console.error("Element with class 'danhsachdichvu' not found.");
    }
}
