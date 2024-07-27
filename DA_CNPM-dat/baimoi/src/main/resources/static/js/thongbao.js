function xoaThongBao(matb) {
    if (confirm("Bạn có chắc chắn muốn xóa thông báo này không?")) {
        $.ajax({
            url: '/thongbao/' + matb,
            type: 'DELETE',
            success: function(result) {
                alert('Thông báo đã được xóa.');
                location.reload();
            },
            error: function(err) {
                console.error('Lỗi khi xóa thông báo:', err);
                alert('Không thể xóa thông báo. Vui lòng thử lại.');
            }
        });
    }
}
