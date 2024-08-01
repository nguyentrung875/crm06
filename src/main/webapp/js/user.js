
$(document).ready(function() {
	/*Chức năng validate user form*/
	$("#formUser").validate({
		onfocusout: false,
		onkeyup: false,
		onclick: false,
		rules: {
			"firstName": {
				required: true,
			},
			"lastName": {
				required: true,
			},
			"email": {
				required: true,
				email: true
			},
			"password": {
				required: true,
			},
			"phone": {
				required: true,
				number: true,
				rangelength: [10, 10],
			}
		},
		messages: {
			"firstName": {
				required: "Họ không được để trống!",
			},
			"lastName": {
				required: "Tên không được để trống!",
			},
			"email": {
				required: "Email không được để trống!",
				email: "Vui lòng nhập đúng định dạng Email"
			},
			"password": {
				required: "Mật khẩu không được để trống!",
			},
			"phone": {
				required: "Số điện thoại không được để trống!",
				number: "Số điện thoại bao gồm 10 số",
				rangelength: "Số điện thoại bao gồm 10 số",
			}

		}
	});

	/*Chức năng thêm, sửa user*/
	$("#btnUserSubmit").click(function(e) {
		e.preventDefault();
		if ($("#formUser").valid()) {
			$.ajax({
				url: window.location,
				type: "POST",
				data: $("#formUser").serialize(),
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
						text: 'Email đã đăng ký!',
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
		if (confirm("Bạn có chắc muốn xóa người dùng?") == true) {
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
							text: 'Xóa thành viên thành công!',
							showHideTransition: 'slide',
							icon: 'success',
							position: 'top-center',
							loader: false
						})
					} else {
						$.toast({
							heading: 'Không thể xóa người dùng!',
							text: 'Bạn cần chuyển tất cả dự án và công việc thành viên này phụ trách cho thành viên khác trước khi xóa!',
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