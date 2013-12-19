function allowDrop(ev) {
	ev.preventDefault();
}

function drag(ev) {
	ev.dataTransfer.setData("Text", ev.target.id);
}

function drop(ev) {
	ev.preventDefault();
	var data = ev.dataTransfer.getData("Text");
	ev.target.appendChild(document.getElementById(data));
}

function refresh() {
	var $request = $.ajax({
		url: "plateauMaj.html",
		type: "post",
	});
	$request.done(function (response, textStatus, xhr) {
		if (response.indexOf("form") == -1 && response.indexOf("vainqueur") == -1) {
			setTimeout(refresh, 200);
		} 
		$('#affichage').html(response);
	});
	$request.fail(function (xhr, textStatus, errorThrown) {
		alert(errorThrown);
	});
}

$(function () {
	refresh();
});