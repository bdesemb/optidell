
<c:choose>
	<c:when test="${ sessionScope.joueur != null }">
		<c:url var="lobby" value="lobby.html" />
		<a href="${ lobby }">Rejoindre lobby</a>
	</c:when>
	<c:otherwise>
	<div id="titre"><h1>Finito</h1></div>
	<div id="formulaire">
		<h3>Connexion</h3>
		<c:url var="connexion" value="connexion.html" />
		<form method="post" action="${connexion}">
			<label for="pseudo">Votre pseudo :</label><input type="text"
				id="pseudo" name="pseudo" /> <br>
				<label for="password">Votre mot
				de passe :</label><input type="password" id="password" name="password" /> <br><br>
			<input class="bouton" type="submit" value="Connexion" />
		</form>
		<c:url var="inscription" value="inscription.html" />
		<a href="${inscription}">S'inscrire</a>
		</div>
	</c:otherwise>
</c:choose>
</div>