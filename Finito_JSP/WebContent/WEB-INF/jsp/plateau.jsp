
<title>Le plateau de partie</title>
</head>
</body>
Bienvenue dans la partie ! </br>


<c:set var="cases" value="${cases}"/>
<c:set var="position" value="0"/>
Plateau de jeu :
</br>
<table>
	<c:forEach var="i" begin="0" end="5">
		<tr> 
			<c:forEach var="j" begin="0" end="5">
				<c:set var="case" value="${cases[position]}"/>
				<c:set var="position" value="${position +1}"/>
				<td> ${case.numero} |-> ${cases[position].jeton.numero}</td>
			</c:forEach>
		</tr>
	</c:forEach>
</table>

</br>
Mes jetons en main : 
</br>
<c:forEach var="jetonsEnMain" items="${jetonsEnMain}">
	${jetonsEnMain.numero}
</c:forEach>

