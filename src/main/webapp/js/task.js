jQuery.validator.addMethod("greaterThan", function(value, element, param) {
	if ($(param).val() != '') {
		if (!/Invalid|NaN/.test(new Date(value))) {
			return new Date(value) > new Date($(param).val());
		}
		return isNaN(value) && isNaN($(param).val()) || (Number(value) > Number($(param).val()));
	};
	return true;
}, 'Ngày kết thúc phải lớn hơn ngày bắt đầu!');
/*Chức năng validate, thêm và sửa task*/
$(document).ready(function() {
	$("#formTask").validate({
		onfocusout: false,
		onkeyup: false,
		onclick: false,
		rules: {
			"taskName": {
				required: true,
			},
			"startDate": {
				required: true,
				dateISO: true,
			},
			"endDate": {
				required: true,
				dateISO: true,
				greaterThan: "#startDate"
			}
		},
		messages: {
			"taskName": {
				required: "Tên dự án không được bỏ trống!"
			},
			"startDate": {
				required: "Ngày bắt đầu không được bỏ trống!",
				dateISO: "Ngày bắt đầu không hợp lệ!",

			},
			"endDate": {
				required: "Ngày kết thúc không được bỏ trống!",
				dateISO: "Ngày kết thúc không hợp lệ!",

			}
		},
	});

	$("#btnTaskSubmit").click(function(e) {
		e.preventDefault();
		if ($("#formTask").valid()) {
			$.ajax({
				url: window.location,
				type: "POST",
				data: $("#formTask").serialize(),
			}).done(function(result) {
				var title = $('.page-title').text();
				if (result) {
					$.toast({
						heading: 'Thành công',
						text: title + ' thành công!',
						showHideTransition: 'slide',
						icon: 'success',
						position: 'top-center',
					})
				} else {
					$.toast({
						heading: 'Thất bại',
						text: title + ' thất bại!',
						showHideTransition: 'slide',
						icon: 'error',
						position: 'top-center',
					})
				}
				});
		}
	})
});

/*Chức năng xóa task trên table*/
$(document).ready(function() {
	$("#example").on("click", ".btn-xoa", function(e) {
		e.preventDefault();
		var id = $(this).attr('id')
		var This = $(this)
		if (confirm("Bạn có chắc muốn xóa công việc?") == true) {
			$.ajax({
				method: "POST",
				url: `http://localhost:8080/crm06/task-delete?id=${id}`,
			}).done(function(result) {
					if (result) {
						$("#example").DataTable()
							.row(This.parents('tr'))
							.remove()
							.draw(false);
						$.toast({
							heading: 'Thông báo',
							text: 'Xóa công việc thành công!',
							showHideTransition: 'slide',
							icon: 'success',
							position: 'top-center',
							loader: false
						})
					} else {
						$.toast({
							heading: 'Thông báo',
							text: 'Không thể xóa công việc!',
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