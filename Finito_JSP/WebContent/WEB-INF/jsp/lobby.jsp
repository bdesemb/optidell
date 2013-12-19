
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
		<h3>Partie(s) en attente</h3>
		<form action="rejoindrePartie.html" method="post">
			<c:forEach var="partie" items="${partiesEnAttente}">
				<input type="radio" name="radio_partie" value="${partie.id}"
					id="${partie.id}" />
				<label for="${partie.id}">"${partie.id}"
					${partie.nombreJoueursConnectes}</label>
				<br />
			</c:forEach>
			<input type="hidden" name="etat" value="en_attente" /> <input
				type="submit" value="Rejoindre" />
		</form>
		<form action="creerPartie.html" method="post">
			<input type="hidden" name="etat" value="en_attente" /> <input
				type="submit" value="Cr�er une partie" />
		</form>
		<br />
	</div>

	<div id="liste_suspendues">
		<h3>Partie(s) suspendues</h3>
		<form action="rejoindrePartie.html" method="post">
			<table>
				<thead>
					<tr>
						<td>Nom de la partie</td>
						<td>Nombre joueurs</td>
						<td>S�lectionner</td>
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
					<input type="hidden" name="etat" value="suspendue" />
					<input type="submit" value="Rejoindre" />
				</tbody>
			</table>
		</form>
	</div>
</div>
</div></td></tr></table>