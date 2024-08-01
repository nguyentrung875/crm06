$(document).ready(function() {
	$("#formLogin").validate({
		onfocusout: false,
		onkeyup: false,
		onclick: false,
		rules: {
			"email": {
				required: true,
				email: true
			},
			"password": {
				required: true,
			}
		},
		messages: {
			"email": {
				required: "Vui lòng nhập Email!",
				email: 'Vui lòng nhập đúng định dạng email!'
			},
			"password": {
				required: "Vui lòng nhập mật khẩu!",
			},

		}
	});
	console.log(window.location)
	$("#btnLogin").click(function(e) {
		e.preventDefault();
		if ($("#formLogin").valid()) {
			$.ajax({
				url: window.location,
				type: "POST",
				data: $("#formLogin").serialize(),
			}).done(function(result) {
				if (result) {
					window.location.href = "";
				} else {
					$.toast({
						heading: 'Đăng nhập thất bại',
						text: 'Vui lòng kiểm tra lại Email và mật khẩu',
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