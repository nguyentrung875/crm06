/*Chức năng validate, thêm và sửa project*/
$().ready(function() {
	$("#formRole").validate({
		onfocusout: false,
		onkeyup: false,
		onclick: false,
		rules: {
			"roleName": {
				required: true,
			},
		},
		messages: {
			"roleName": {
				required: "Tên quyền không được bỏ trống!"
			},
		},
	});

	$("#btnRoleSubmit").click(function(e) {
		e.preventDefault();
		if ($("#formRole").valid()) {
			$.ajax({
				url: window.location,
				type: "POST",
				data: $("#formRole").serialize(),
			}).done(function(result) {
				var title = $('.page-title').text();
				if (result) {
					$.toast({
						heading: 'Thông báo',
						text: title + 'thành công!',
						showHideTransition: 'slide',
						icon: 'success',
						position: 'top-center',
						loader: false
					})
				} else {
					$.toast({
						heading: title + ' thất bại!',
						text: 'Tên quyền đã tồn tại.\r\nVui lòng nhập tên khác!',
						showHideTransition: 'slide',
						icon: 'error',
						position: 'top-center',
						loader: false
					})
				}
			});
		}
	})
});

/*Chức năng xóa project trên table*/
$(document).ready(function() {
	$("#example").on("click", ".btn-xoa", function(e) {
		e.preventDefault();
		var id = $(this).attr('id')
		var This = $(this)
		if (confirm("Bạn có chắc muốn xóa Quyền?") == true) {
			$.ajax({
				method: "POST",
				url: `http://localhost:8080/crm06/role-delete?id=${id}`,
			}).done(function(result) {
				if (result) {
					$("#example").DataTable()
						.row(This.parents('tr'))
						.remove()
						.draw(false);
					$.toast({
						heading: 'Thông báo',
						text: 'Xóa quyền thành công!',
						showHideTransition: 'slide',
						icon: 'success',
						position: 'top-center',
						loader: false
					})
				} else {
					$.toast({
						heading: 'Thông báo',
						text: 'Không thể xóa quyền vì có người dùng đang thuộc quyền này!',
						showHideTransition: 'slide',
						icon: 'error',
						position: 'top-center',
						loader: false
					})
				}
			});
		}
	})
})