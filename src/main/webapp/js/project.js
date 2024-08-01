jQuery.validator.addMethod("greaterThan", function(value, element, param) {
	if ($(param).val() != '') {
		if (!/Invalid|NaN/.test(new Date(value))) {
			return new Date(value) > new Date($(param).val());
		}
		return isNaN(value) && isNaN($(param).val()) || (Number(value) > Number($(param).val()));
	};
	return true;
}, 'Ngày kết thúc phải lớn hơn ngày bắt đầu!');

/*Chức năng validate, thêm và sửa project*/
$().ready(function() {
	$("#formProject").validate({
		onfocusout: false,
		onkeyup: false,
		onclick: false,
		rules: {
			"projectName": {
				required: true,
			},
			"projectStartDate": {
				required: true,
				dateISO: true,
			},
			"projectEndDate": {
				required: true,
				dateISO: true,
				greaterThan: "#startDate"
			}
		},
		messages: {
			"projectName": {
				required: "Tên dự án không được bỏ trống!"
			},
			"projectStartDate": {
				required: "Ngày bắt đầu không được bỏ trống!",
				dateISO: "Ngày bắt đầu không hợp lệ!",
			},
			"projectEndDate": {
				required: "Ngày kết thúc không được bỏ trống!",
				dateISO: "Ngày kết thúc không hợp lệ!",
			}
		},
	});

	$("#btnProjectSubmit").click(function(e) {
		e.preventDefault();
		if ($("#formProject").valid()) {
			$.ajax({
				url: window.location,
				type: "POST",
				data: $("#formProject").serialize(),
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
						heading: 'Thông báo',
						text: title + ' thất bại!',
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
		if (confirm("Tất cả công việc của dự án này cũng sẽ bị xóa. Bạn có chắc muốn xóa dự án? ")) {
			$.ajax({
				method: "POST",
				url: `http://localhost:8080/crm06/project-delete?id=${id}`,
			})
				.done(function(result) {
					if (result) {
						$("#example").DataTable()
							.row(This.parents('tr'))
							.remove()
							.draw(false);
						$.toast({
							heading: 'Thông báo',
							text: 'Xóa dự án thành công!',
							showHideTransition: 'slide',
							icon: 'success',
							position: 'top-center',
							loader: false
						})
					} else {
						$.toast({
							heading: 'Thông báo',
							text: 'Không thể xóa dự án!',
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