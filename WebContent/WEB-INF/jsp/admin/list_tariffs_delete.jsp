<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<fmt:message key="list_tariffs_delete.jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page" />

<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%@ include file="/WEB-INF/jspf/admin_menu.jspf"%>

	<section>
		<form action="controller" method="post">
			<input type="hidden" name="command" value="deleteTariff" />
			<table>
				<thead>
					<tr>
						<th>№</th>
						<th><fmt:message key="list_tariffs_delete.jsp.tariff"/></th>
						<th><fmt:message key="list_tariffs_delete.jsp.service"/></th>
						<th><fmt:message key="list_tariffs_delete.jsp.price"/></th>
						<th><fmt:message key="list_tariffs_delete.jsp.description"/></th>
					</tr>
				</thead>
				<tbody>
					<c:set var="k" value="0" />
					<c:forEach var="item" items="${tariffsServices}">
						<c:set var="k" value="${k+1}" />
						<tr>
							<td><c:out value="${k}" /></td>
							<td>${item.tariff.name}</td>
							<td>${item.service.name}</td>
							<td>${item.price}</td>
							<td>${item.description}</td>							

							<td><input type="checkbox" name="itemId" value="${item.id}" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<input id="tableSubmit" type="submit" value="<fmt:message key="list_tariffs_delete.jsp.submit.delete"/>" />
		</form>
	</section>
	<%-- CONTENT --%>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>