function allowDrop(ev) {
	ev.preventDefault();
}

function drag(ev) {
	ev.dataTransfer.setData("Text", ev.target.id);
	ev.dataTransfer.setData("joue", "true");
}

function drop(ev) {
	ev.preventDefault();
	var data = ev.dataTransfer.getData("Text");
	if ($(ev.target).attr("occupe") != "true") {
		ev.target.appendChild(document.getElementById(data));
		var $jetonJoue = $(document.getElementById(data));
		$('#numeroJeton').val($jetonJoue.attr("id"));
		var $case = $(ev.target);
		$('#idCase').val($case.attr("id"));
		$("#form").submit();
	}

}

function refresh() {
	var $request = $.ajax({
		url : "plateauMaj.html",
		type : "post",
	});
	$request.done(function(response, textStatus, xhr) {
		setTimeout(refresh, 1000);
		$('#affichage_plateau').html(response);
		if($('#forward').length>0){
			window.location.href="terminer_partie.html";
		}
	});
	$request.fail(function(xhr, textStatus, errorThrown) {
		alert(errorThrown);
	});
}

$(function() {
	refresh();
	$(".drag").draggable();
});