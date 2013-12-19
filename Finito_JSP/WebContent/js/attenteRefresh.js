function refresh() {
	var $request = $.ajax({
		url: "attenteMaj.html",
		type: "post",
	});
	$request.done(function (response, textStatus, xhr) {
		$('#affichage').html(response);
	});
	$request.fail(function (xhr, textStatus, errorThrown) {
		alert(errorThrown);
	});
}

$(function () {
	refresh();
	t = setInterval(refresh, 5000);
});