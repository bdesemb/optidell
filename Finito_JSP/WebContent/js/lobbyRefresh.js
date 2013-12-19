function refresh() {
	var $request = $.ajax({
		url: "lobbyMaj.html",
		type: "post",
	});
	$request.done(function (response, textStatus, xhr) {
		$('#liste_attentes').html(response);
	});
	$request.fail(function (xhr, textStatus, errorThrown) {
		alert(errorThrown);
	});
}

$(function () {
	refresh();
	t = setInterval(refresh, 5000);
});