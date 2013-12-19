
function refresh() {
	var $request = $.ajax({
		url: "plateau.html",
		type: "post",
	});
	$request.done(function (response, textStatus, xhr) {
		if (response.indexOf("form") == -1 && response.indexOf("vainqueur") == -1) {
			setTimeout(refresh, 200);
		} 
		$('#result').html(response);
	});
	$request.fail(function (xhr, textStatus, errorThrown) {
		alert(errorThrown);
	});
}

$(function () {
	refresh();
});