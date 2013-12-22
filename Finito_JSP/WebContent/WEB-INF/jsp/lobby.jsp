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
			Connecté: ${ sessionScope.joueur.login }
			<c:url var="deconnexion" value="deconnexion.html" />
			<a href="${ deconnexion }">Se déconnecter</a>
		</div>
	</c:if>
	<table id="page_table">
		<tr>
			<td id="page_td">
				<div id="liste_attentes"></div>
			</td>
			<td id="page_td">
				<div id="liste_suspendues">
					<h3>Partie(s) suspendue(s)</h3>
					<br/>
					<form action="rejoindrePartie.html" method="post">
						<table id="table_lobby">
							<thead>
								<tr>
									<th id="th_lobby">Nom de la partie</th>
									<th id="th_lobby">Nombre joueurs</th>
									<th id="th_lobby">Sélectionner</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="partie" items="${partiesSuspendues}">
								<tr>
									<td id="td_lobby"><label for="${partie.id}">${donneesDesParties[partie.id].nomPartie}</label></td>
									<td id="td_lobby"><label for="${partie.id}">${donneesDesParties[partie.id].nombreJoueurs}</label></td>
									<td id="td_lobby"><input type="radio" name="radio_partie"
									value="${partie.id}" id="${partie.id}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<input type="hidden" name="etat" value="suspendue" /> <input
							type="submit" value="Rejoindre" />
					</form>
				</div>
			</td>
		</tr>
	</table>