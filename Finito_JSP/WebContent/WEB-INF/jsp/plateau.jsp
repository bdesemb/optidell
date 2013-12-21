<script type="text/javascript" src="js/plateau.js"></script>
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
		</div>
	</c:if>
	<c:set var="id_joueur" value="${joueur.id}"/>
	<table id="page_table">
		<tr>
			<td id="page_td"><div id="contenu">
					<div id="affichage_plateau"></div>
					<c:set var="trouve" value="non"/>
					<c:forEach var="id_joueur_verif" items="${donneesDesParties[id_partie].joueurs}">
					<c:choose>
						<c:when test="${id_joueur_verif==id_joueur}">
							<c:set var="trouve" value="oui"/>
						</c:when>
					</c:choose>
					</c:forEach>
					<c:choose>
						<c:when test="${trouve=='non'}">
						Temps restant : <br>
						<div id="barre_temps"></div>
						</c:when>
					</c:choose>
				</div></td>
		</tr>
	</table>