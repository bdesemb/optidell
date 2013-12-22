<title>Finitooine - #${ id_partie }</title>
</head>
<body>

	<c:if test="${message != null}">
		<div id="erreur">${message}</div>
	</c:if>

	<c:if test="${ joueur != null}">
		<div id="entete">
			Connect�: ${joueur.login}
			<c:url var="deconnexion" value="deconnexion.html" />
			<a href="${deconnexion}">Se d�connecter</a>
		</div>
	</c:if>

	<%-- <c:forEach var="i" begin="0" end="1">
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
	</c:forEach> --%>
	<!-- fin forEach plateau -->
<table id="page_table">
  <tr>
   <td id="page_td"><div id="contenu">
	<c:forEach var="plateau" items="${plateaux}">
		<c:set var="position" value="0" />
		<c:set var="cases" value="${mapPlateaux_idCases[plateau.id]}" />
		<div class="plateauJoueur">
		Joueur :  "${plateau.joueur.login}" <c:choose><c:when test="${mapPlateaux_idScore[plateau.id]==12}">(Gagnant)</c:when></c:choose><br>
		Serie : ${mapPlateaux_idScore[plateau.id]}
		<table class="plateauScore">
			<c:forEach var="y" begin="0" end="5">
				<tr>
					<c:forEach var="j" begin="0" end="5">
						<c:set var="case" value="${cases[position]}" />
						<td><div class="div1">
								${case.numero}
								<c:choose>
									<c:when test="${case.jeton != null}">
										<div class="drag" width="40"
											height="40">${case.jeton.numero}</div>
									</c:when>
								</c:choose>
							</div></td>
						<c:set var="position" value="${position +1}" />
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
		<br><br>
		</div>
		<br>
		<br>
		<c:set var="position" value="0" />
	</c:forEach>
	<form id="form" action="quitter_partie.html" method="get">
	<input type="submit" value="Lobby"> 
</form>
</div></td></tr></table>

