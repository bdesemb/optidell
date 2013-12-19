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
				type="submit" value="Créer une partie" />
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
						<td>Sélectionner</td>
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
