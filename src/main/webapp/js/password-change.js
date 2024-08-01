jQuery.validator.addMethod("notEqual", function(value, element, param) {
 return this.optional(element) || value != $(param).val();
}, "Vui lòng nhập khác mật khẩu cũ!");


$(document).ready(function() {
	/*Chức năng validate user form*/
	$("#formPassword").validate({
		onfocusout: false,
		onkeyup: false,
		onclick: false,
		rules: {
			"oldPassword": {
				required: true,
			},
			"newPassword": {
				required: true,
				notEqual: "#oldPassword",
			},
			"confirmPassword": {
				equalTo: "#newPassword"
			},
		},
		messages: {
			"oldPassword": {
				required: "Mật khẩu cũ không được để trống!",
			},
			"newPassword": {
				required: "Mật khẩu mới không được để trống!",
			},
			"confirmPassword": {
				equalTo: "Vui lòng nhập giống mật khẩu mới!",
			},
		}
	});

	/*Chức năng thêm, sửa user*/
	$("#btnPasswordSubmit").click(function(e) {
		e.preventDefault();
		if ($("#formPassword").valid()) {
			$.ajax({
				url: window.location,
				type: "POST",
				data: $("#formPassword").serialize(),
			}).done(function(result) {
				var title = $('.page-title').text();
				if (result) {
					$.toast({
						heading: 'Thông báo',
						text: title + ' thành công!',
						showHideTransition: 'slide',
						icon: 'success',
						position: 'top-center',
						loader: false
					})
				} else {
					$.toast({
						heading: title + ' thất bại!',
						text: 'Mật khẩu cũ không chính xác. Vui lòng nhập lại!',
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


$(document).ready(function() {
	/*Chức năng delete user ko cần load lại trang*/
	$("#example").on("click", ".btn-xoa", function(e) {
		e.preventDefault();
		var id = $(this).attr('id')
		var This = $(this)
		if (confirm("Bạn có chắc muốn xóa người dùng có id là: " + id) == true) {
			$.ajax({
				method: "POST",
				data: { idXoa: id },
				url: `http://localhost:8080/crm06/user-delete?id=${id}`,
			}).done(function(result) {
					if (result) {
						$("#example").DataTable()
							.row(This.parents('tr'))
							.remove()
							.draw(false);
						$.toast({
							heading: 'Thông báo',
							text: 'Xóa người dùng thành công!',
							showHideTransition: 'slide',
							icon: 'success',
							position: 'top-center',
							loader: false
						})
					} else {
						$.toast({
							heading: 'Không thể xóa người dùng!',
							text: 'Cần chuyển tất cả công việc người này phụ trách cho người khác trước khi xóa',
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