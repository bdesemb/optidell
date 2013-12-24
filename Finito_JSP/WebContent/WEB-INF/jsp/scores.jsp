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

