
function xoaDoiTac(id) {
    if (confirm('Bạn có chắc chắn muốn xóa đối tác này không?')) {
        $.ajax({
            url: '/admin/xoa/' + id,
            type: 'DELETE',
            success: function(result) {
                alert('Xóa thành công');
                location.reload();
                window.location.href = '/admin/tatcadoitac';
            },
            error: function(xhr, status, error) {
                alert('Xóa thất bại: ' + error);
            }
        });
    }
}

