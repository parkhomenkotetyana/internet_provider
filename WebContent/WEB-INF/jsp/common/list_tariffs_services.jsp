<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<fmt:message key="list_tariffs_services.jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page" />

<%@ include file="/WEB-INF/jspf/head.jspf"%>


<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<c:if test="${userRole.name == 'admin' }">
		<%@ include file="/WEB-INF/jspf/admin_menu.jspf"%>
	</c:if>
	<c:if test="${userRole.name == 'subscriber' }">
		<%@ include file="/WEB-INF/jspf/subscriber_menu.jspf"%>
	</c:if>

	<section>
	<div class="content">
	<div class="left_div">
		<table>
			<thead>
				<tr>
					<th>№</th>
					<th><fmt:message key="list_tariffs_services.jsp.tariff"/></th>
					<th><fmt:message key="list_tariffs_services.jsp.service"/></th>
					<th><fmt:message key="list_tariffs_services.jsp.price"/></th>
					<th><fmt:message key="list_tariffs_services.jsp.description"/></th>
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
		
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		<div class="right_div">

		<form action="controller">
			<input type="hidden" name="command" value="listTariffsServices" /> <input
				type="hidden" name="sort" value="price" />
			<button type="submit"><fmt:message key="list_tariffs_services.jsp.sort.price"/></button>
		</form>
		<form action="controller">
			<input type="hidden" name="command" value="listTariffsServices" /> <input
				type="hidden" name="sort" value="nameAZ" />
			<button type="submit" style="font-size: 15px"><fmt:message key="list_tariffs_services.jsp.sort.name.AZ"/></button>
		</form>
		<form action="controller">
			<input type="hidden" name="command" value="listTariffsServices" /> <input
				type="hidden" name="sort" value="nameZA" />
			<button type="submit" style="font-size: 15px"><fmt:message key="list_tariffs_services.jsp.sort.name.ZA"/></button>
		</form>

		<c:if test="${userRole.name == 'subscriber' }">
			<form action="controller">
				<input type="hidden" name="command" value="downloadPDF" />
				<button type="submit"><fmt:message key="list_tariffs_services.jsp.download"/></button>
			</form>
			<form action="controller">
				<input type="hidden" name="command" value="makeOrder" />
				<button type="submit"><fmt:message key="list_tariffs_services.jsp.make_order"/></button>
			</form>
		</c:if>
	</div>
	</div>
	</section>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>