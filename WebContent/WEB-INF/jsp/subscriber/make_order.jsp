<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<fmt:message key="make_order.jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page" />

<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%@ include file="/WEB-INF/jspf/subscriber_menu.jspf"%>
	
	<section>
	<div class="select_tariff">
		<form action="controller">
			<input type="hidden" name="command" value="makeOrder" />
			<table id="left_table">

				<thead>
					<tr>
						<th>№</th>
						<th><fmt:message key="make_order.jsp.tariff"/></th>
					</tr>
				</thead>
				<tbody>

					<c:set var="k" value="0" />
					<c:forEach var="tariff" items="${tariffs}">
						<c:set var="k" value="${k+1}" />
						<tr>
							<td><c:out value="${k}" /></td>
							<td>${tariff.name}</td>
							<td><input type="radio" name="tariffID"
								value="${tariff.id }"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<input id="leftTableSubmit" type="submit" value="<fmt:message key="make_order.jsp.submit.select"/>">
		</form>
	</div>
<c:if test="${not empty servicesTariffs}">
	<div class="select_service">
			<form class="make_order" action="controller" method="post">
				<input type="hidden" name="command" value="makeOrder" />
				<table id="right_table">
								<thead>
					<tr>
						<th>№</th>
						<th><fmt:message key="make_order.jsp.service"/></th>
						<th><fmt:message key="make_order.jsp.price"/></th>
						<th><fmt:message key="make_order.jsp.description"/></th>
					</tr>
				</thead>
				<tbody>
					<c:set var="k" value="0" />
					<c:forEach var="item" items="${servicesTariffs}">
						<c:set var="k" value="${k+1}" />
						<tr>
							<td><c:out value="${k}" /></td>
							<td>${item.service.name}</td>
							<td>${item.price}</td>
							<td>${item.description}</td>
	
							<td><input type="checkbox" name="serviceID"
								value="${item.service.id}" /></td>
						</tr>
					</c:forEach>
				</tbody>

				</table>
				<input id="rightTableSubmit" type="submit" value="<fmt:message key="make_order.jsp.submit.order"/>">
			</form>
	</div>
</c:if>

	</section>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>