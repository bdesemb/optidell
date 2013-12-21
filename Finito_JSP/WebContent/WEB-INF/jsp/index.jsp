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
			<td id="page_td"><div id="contenu">
					<c:choose>
						<c:when test="${ sessionScope.joueur != null }">
							<c:url var="lobby" value="lobby.html" />
							<a href="${ lobby }">Rejoindre lobby</a>
						</c:when>
						<c:otherwise>
							<div id="formulaire">
								<h3>Connexion</h3>
								<c:url var="connexion" value="connexion.html" />
								<form method="post" action="${connexion}">
									<label for="pseudo">Votre pseudo :</label>
									<input type="text" id="pseudo" name="pseudo" class=".depth"/> <br> 
									<label for="password">Votre mot de passe :</label>
									<input type="password" id="password" name="password" class=".depth"/> <br> <br>
									<input class="" type="submit" value="Connexion" />
								</form>
								<c:url var="inscription" value="inscription.html" />
								<a href="${inscription}" class="inline-link-2">S'inscrire</a>
							</div>
						</c:otherwise>
					</c:choose>
				</div></td>
		</tr>
	</table>