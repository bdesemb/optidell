<title>Bienvenue sur Finitooine</title>
</head>
<body>
<c:if test="${message != null}">
	<div id="erreur">${message}</div>
</c:if>
<c:url var="connexion" value="index.html"/>
<form method="post" action="${connexion}">
	<label for="pseudo">Votre pseudo :</label><input type="text" id="pseudo" name="pseudo"/>
	<label for="password">Votre mot de passe :</label><input type="text" id="password" name="password"/>
	<br>
	<input type="submit" value="Se connecter"/>
</form>
<c:url var="inscription" value="inscription.html" />
<a href="${inscription}">S'inscrire</a>