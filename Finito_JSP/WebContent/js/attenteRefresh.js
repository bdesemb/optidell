function refresh() {
	var $request = $.ajax({
		url: "attenteMaj.html",
		type: "post",
	});
	$request.done(function (response, textStatus, xhr) {
		$('#affichage_attente').html(response);
		/*if($('#forward').length>0)
			self.location = "jouer.html";*/
	});
	$request.fail(function (xhr, textStatus, errorThrown) {
		alert(errorThrown);
	});
}

$(function () {
	refresh();
	t = setInterval(refresh, 3000);
});