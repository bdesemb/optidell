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
			<a href="${ deconnexion }">Se déconnecter</a>
		</div>
	</c:if>
<table id="page_table"><tr><td id="page_td"><div id="contenu">
</br>
Bienvenue dans la partie !
</br>


<c:set var="cases" value="${cases}" />
<c:set var="position" value="0" />
Plateau de jeu :
</br>
<table id="plateau">
	<c:forEach var="i" begin="0" end="5">
		<tr>
			<c:forEach var="j" begin="0" end="5">
				<c:set var="case" value="${cases[position]}" />
				<c:set var="position" value="${position +1}" />
				<td><div id="case_${case.id}" class="div1" ondrop="drop(event)"
						ondragover="allowDrop(event)">
						<b>${case.numero}</b>
					</div>
				</td>
			</c:forEach>
		</tr>
	</c:forEach>
</table>

</br>
Mes jetons en main :
</br>
<table align="center">
	<tr>
		<c:forEach var="jetonsEnMain" items="${jetonsEnMain}">
			<td><div id="jeton_${jetonsEnMain.id}" class="drag" draggable="true"
					ondragstart="drag(event)" width="40" height="40">
					<b>${jetonsEnMain.numero}</b>
				</div></td>
		</c:forEach>
	</tr>
</table>
</div></td></tr></table>


