<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<c:if test="${message != null}">
	<div id="erreur">${message}</div>
</c:if>
<div id="welcome">Bonjour ${joueur.login}</div>
<div id="listages">
Partie(s) en attente
<form action="jouer.html" method="post">
<c:forEach var="partie" items="${partiesEnAttente}">
<input type="radio" name="radio_partie" value="${partie.id}" id="${partie.id}"/><label for="${partie.id}">"${partie.id}" ${fn:length(partie.plateauEnJeu)}</label><br/>
</c:forEach>
<input type="hidden" name="etat" value="en_attente"/>
<input type="submit" value="Rejoindre"/>
</form>
<br/>
Partie(s) suspendues
<form action="jouer.html" method="post">
<c:forEach var="partie" items="${partiesSuspendues}">
<input type="radio" name="radio_partie" value="${partie.id}" id="${partie.id}"/><label for="${partie.id}">"${partie.id}" ${fn:length(partie.plateauEnJeu)}</label><br/>
</c:forEach>
<input type="hidden" name="etat" value="suspendue"/>
<input type="submit" value="Rejoindre"/>
</form>
</div>
