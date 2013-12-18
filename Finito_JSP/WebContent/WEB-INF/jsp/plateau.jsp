<%@page import="be.ipl.finito.domaine.Jeton"%>
<%@page import="be.ipl.finito.domaine.Case"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<title>Le plateau de partie</title>
</head>
</body>
Bienvenue dans la partie !
<% List<Case> cases = (ArrayList<Case>) session.getAttribute("cases");%>
<% int position = 0;%>
<table>
	<% for (int i=0; i<6; i++) { %>
		<% for (int y=0; y<6; y++) { %>
			<tr><td>Login : <%=cases.get(position).getNumero()%> |-> <%=cases.get(position).getJeton().getNumero() %></td></tr>
		<% } position++;%>
	<% } %>
</table>
<% List<Jeton> jetonsEnMain = (ArrayList<Jeton>) session.getAttribute("jetonsEnMain");%>
<table>
	<% for (int y=0; y<jetonsEnMain.size(); y++) { %>
		<tr><td>Login : <%=jetonsEnMain.get(position++).getNumero()%></td></tr>
	<% } %>
</table>
