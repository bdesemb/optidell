<script type="text/javascript" src="js/plateau.js"></script>
<title>Bienvenue sur Finitooine</title>
</head>
<body>

	<c:if test="${message != null}">
		<div id="erreur">${message}</div>
	</c:if>

	<c:if test="${ sessionScope.joueur != null}">
		<div id="entete">
			Connect�: ${ sessionScope.joueur.login }
			<c:url var="deconnexion" value="deconnexion.html" />
		</div>
	</c:if>
	<table id="page_table">
		<tr>
			<td id="page_td"><div id="contenu">
					<br /> Bienvenue dans la partie ! <br />
					<div id="affichage_plateau"></div>
					Temps restant : <br>
					<div id="barre_temps"></div>

				</div></td>
		</tr>
	</table>