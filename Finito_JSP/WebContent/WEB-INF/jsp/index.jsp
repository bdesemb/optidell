<c:choose>
	<c:when test="${ sessionScope.joueur != null }">
		<c:url var="${ lobby }" value="lobby.html" />
		<a href="${ lobby }">Rejoindre lobby</a>
	</c:when>
	<c:otherwise>
		<c:url var="connexion" value="connexion.html" />
		<form method="post" action="${connexion}">
			<label for="pseudo">Votre pseudo :</label><input type="text"
				id="pseudo" name="pseudo" /> <label for="password">Votre mot
				de passe :</label><input type="password" id="password" name="password" /> <br>
			<input type="submit" value="Se connecter" />
		</form>
		<c:url var="inscription" value="inscription.html" />
		<a href="${inscription}">S'inscrire</a>
	</c:otherwise>
</c:choose>