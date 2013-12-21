<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/lobbyRefresh.js"></script>
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
			<a href="${ deconnexion }">Se d�connecter</a>
		</div>
	</c:if>
<table id="page_table"><tr><td id="page_td"><div id="contenu">
<div id="listages">
	<div id="liste_attentes">
	</div>

	<div id="liste_suspendues">
		<h3>Partie(s) suspendues</h3>
		<form action="rejoindrePartie.html" method="post">
			<table id="table_lobby">
				<thead>
					<tr>
						<th id="th_lobby">Nom de la partie</th>
						<th id="th_lobby">Nombre joueurs</th>
						<th id="th_lobby">S�lectionner</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="partie" items="${partiesSuspendues}">
						<input type="radio" name="radio_partie" value="${partie.id}"
							id="${partie.id}" />
						<label for="${partie.id}">"${partie.id}"
							${partie.nombreJoueursConnectes}</label>
						<br />
					</c:forEach>
				</tbody>
			</table>
			<input type="hidden" name="etat" value="suspendue" />
			<input type="submit" value="Rejoindre" />
		</form>
	</div>
</div>
</div></td></tr></table>