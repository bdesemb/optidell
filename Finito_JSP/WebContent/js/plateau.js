var nb = 0;

function allowDrop(ev) {
	ev.preventDefault();
}

function drag(ev) {
	ev.dataTransfer.setData("Text", ev.target.id);
}

function drop(ev) {
	ev.preventDefault();
	var data = ev.dataTransfer.getData("Text");
	if($(ev.target).attr("occupe") != "true"){
		ev.target.appendChild(document.getElementById(data));
		var $jetonJoue = $("#plateau").find($(".drag[draggable='true']"));
		alert($jetonJoue.parent().attr('id')+""+$jetonJoue.parent().text());
		$('#numeroJeton').val($jetonJoue.text());
		var $case = $(ev.target);
		$('#idCase').val($case.attr("id"));
		$("#form").submit();
	}
}

function refresh() {
	if(nb==0 || $("#refresh").attr("refresh") == "true"){
		var $request = $.ajax({
			url: "plateauMaj.html",
			type: "post",
		});
		$request.done(function (response, textStatus, xhr) {
			if (response.indexOf("form") == -1 && response.indexOf("vainqueur") == -1) {
				setTimeout(refresh, 1000);
			} 
			$('#affichage').html(response);
		});
		$request.fail(function (xhr, textStatus, errorThrown) {
			alert(errorThrown);
		});
		nb++;
	}
}

$(function () {
	refresh();
});