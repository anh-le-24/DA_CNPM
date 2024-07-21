function toggleForm(formId) {
    var form = document.getElementById(formId);
    if (form.style.display === "none" || form.style.display === "") {
        form.style.display = "block";
    } else {
        form.style.display = "none";
    }
}

function xemChiTiet(madt) {
    // Gửi AJAX request để lấy chi tiết đối tác và người dùng liên quan dựa trên madt
    $.ajax({
        type: "GET",
        url: "/admin/chitiet/" + madt,
        success: function(response) {
            // Hiển thị form chi tiết
            $('#chitietdonxindangky').show();

            // Đổ dữ liệu vào các trường input
            $('#id').val(response.id);
            $('#tennhahang').val(response.tenNhaHang);
            $('#sonha').val(response.soNha);
            $('#duong').val(response.duong);
            $('#quan').val(response.quan);
            $('#thanhpho').val(response.thanhPho);
            // Đổ dữ liệu người dùng liên quan (nếu có)
            $('#hotenChiTiet').val(response.nguoiDung.ten);
            $('#sdtChiTiet').val(response.nguoiDung.sdt);
            $('#emailChiTiet').val(response.nguoiDung.email);
        },
        error: function() {
            alert('Không thể lấy thông tin chi tiết đối tác.');
        }
    });
}



function quaylai() {
    $('#chitietdonxindangky').hide();
    $('.danhsachdangky').show();
}

function xacnhandk(mand) {
    // Gửi AJAX POST request đến endpoint /doitac/sua với mand làm tham số
    $.ajax({
        type: "POST",
        url: "/admin/sua",
        data: { mand: mand },
        success: function(response) {
            // Xử lý khi thành công
            alert("Đã xác nhận thành công đối tác");
            // Reload lại trang để cập nhật danh sách
            location.reload();
        },
        error: function(e) {
            // Xử lý khi có lỗi
            alert("Đã xảy ra lỗi: " + e.responseText);
        }
    });
}


function huyxacnhan(madt) {
    if (confirm('Bạn có chắc chắn muốn hủy đối tác này?')) {
        // Gửi yêu cầu xóa người dùng
        fetch('/admin/xoa/' + madt, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                // Reload lại trang sau khi xóa
                window.location.reload();
            } else {
                alert('Xóa người dùng thất bại.');
            }
        })
        .catch(error => console.error('Error:', error));
    }
}