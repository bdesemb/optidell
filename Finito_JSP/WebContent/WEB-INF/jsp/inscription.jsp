<title>Inscription</title>
</head>
<body>

<c:if test="${message != null}">
	<div id="erreur">${message}</div>
</c:if>

<c:url var="inscription" value="inscription.html"/>
<form method="post" action="${inscription}">
	<label for="pseudo">Votre nom :</label><input type="text" id="nom" name="nom"/><br>
	<label for="pseudo">Votre prenom :</label><input type="text" id="prenom" name="prenom"/><br>
	<label for="pseudo">Votre mail :</label><input type="text" id="mail" name="mail"/><br>
	<label for="pseudo">Votre pseudo :</label><input type="text" id="pseudo" name="pseudo"/><br>
	<label for="password">Votre mot de passe :</label><input type="password" id="password" name="password"/><br>
	
	<input type="submit" value="S'inscrire"/>
</form>

