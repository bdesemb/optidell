<title>Finitooine - #${ id_partie }</title>
</head>
<body>

	<c:if test="${message != null}">
		<div id="erreur">${message}</div>
	</c:if>

	<c:if test="${ joueur != null}">
		<div id="entete">
			Connecté: ${joueur.login}
			<c:url var="deconnexion" value="deconnexion.html" />
			<a href="${deconnexion}">Se déconnecter</a>
		</div>
	</c:if>

	<c:forEach var="i" begin="0" end="1">
		<c:set var="idPlateau" value="plateau${i}" />
		<div>
			Le joueur ${ joueursDeLaPartie[i].joueur.prenom} "${joueursDeLaPartie[i].joueur.login}"
			${joueursDeLaPartie[i].joueur.nom} a fait une suite de
			<div id="score${i}">${joueursDeLaPartie[i].score}nombre(s).</div>
		</div>
		<c:if test="${ joueursDeLaPartie.score == '12' }">
			<div id="win">Vainqueur</div>
		</c:if>
		<table id="${idPlateau }">
			<c:forEach var="i" begin="0" end="5">
				<tr>
					<c:set var="ligneCase" value="${joueursDeLaPartie[i].case[i]}" />
					<c:set var="ligneJeton" value="${joueursDeLaPartie[i].jeton[i]}" />
					<c:forEach var="j" begin="0" end="5">
						<c:set var="case" value="${ligneCase[j]}" />
						<c:set var="jeton" value="${ligneJeton[j]}" />
						<td>
							${ case } : ${ jeton }
						</td>
					</c:forEach>
					<!-- fin forEach colonne -->
				</tr>
			</c:forEach>
			<!-- fin forEach ligne -->
		</table>
	</c:forEach>
	<!-- fin forEach plateau -->

</body>
</html>