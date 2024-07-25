
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
//xóa đối tác chưa đang xin gia nhập
    document.addEventListener('DOMContentLoaded', function() {
        const deleteButtons = document.querySelectorAll('.btn-xoa');

        deleteButtons.forEach(button => {
            button.addEventListener('click', function(event) {
                const confirmed = confirm('Bạn có chắc chắn muốn xóa đối tác này không?');
                if (!confirmed) {
                    event.preventDefault();
                }
            });
        });
    });

