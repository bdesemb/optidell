function refresh() {
	var $request = $.ajax({
		url: "refresh.html",
		type: "post",
	});
	$request.done(function (response, textStatus, xhr) {
		$('#result').html(response);
	});
	$request.fail(function (xhr, textStatus, errorThrown) {
		alert(errorThrown);
	});
}

$(function () {
	refresh();
	t = setInterval(refresh, 5000);
});