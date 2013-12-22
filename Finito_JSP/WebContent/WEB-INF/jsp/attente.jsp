<script type="text/javascript" src="js/attenteRefresh.js"></script>
</head>
<body>
	<table id="page_table">
		<tr>
			<td id="page_td"><div id="contenu">
					<div id="affichage_attente"></div>
					<c:choose>
						<c:when test='${donneesDesParties[id_partie].etat=="EN_ATTENTE"}'>
							<div id="barre_attente"></div>
						</c:when>
					</c:choose>
				</div></td>
		</tr>
	</table>