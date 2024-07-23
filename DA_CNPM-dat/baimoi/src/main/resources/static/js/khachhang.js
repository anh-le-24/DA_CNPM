function toggleForm(formId) {
    var form = document.getElementById(formId);
    if (form.style.display === "none" || form.style.display === "") {
        form.style.display = "block";
        if (formId === 'themKhachHangForm') {
            document.getElementById('suaNguoiDungForm').style.display = 'none';
            document.getElementById('chiTietNguoiDungForm').style.display = 'none';
            
        } else {
            form.style.display = "none";
           
        }
    }
}

function quaylaithem(){
    document.getElementById('themKhachHangForm').style.display ='none';
    window.location.href='/nguoidung/tatca';
    
}
function quaylaisua(){
    document.getElementById('suanguoidungform').style.display ='none';
    window.location.href='/nguoidung/tatca';
}
function quaylaiCT(){
    document.getElementById('CTnguoidungform').style.display ='none';
    window.location.href='/nguoidung/tatca';
}
function xoaNguoiDung(mand) {
    if (confirm('Bạn có chắc chắn muốn xóa người dùng này?')) {
        // Gửi yêu cầu xóa người dùng
        fetch('/nguoidung/xoa/' + mand, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                // Reload lại trang sau khi xóa
                window.location.reload();
                window.location.href='/nguoidung/tatca';
            } else {
                alert('Xóa người dùng thất bại.');
            }
        })
        .catch(error => console.error('Error:', error));
    }
}
