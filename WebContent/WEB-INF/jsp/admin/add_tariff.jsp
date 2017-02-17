<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<fmt:message key="add_tariff.jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page" />

<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	
<c:if test="${not empty param.error}">
	<div id="error">
		<fmt:message key="jsp.error.invalid_data" var="error"/>
		<c:out value="${error}"></c:out>
	</div>
</c:if>

	<%@ include file="/WEB-INF/jspf/admin_menu.jspf"%>	

	<%-- CONTENT --%>
	<section>
		<form class="add_form" action="controller" method="post">
			<input type="hidden" name="command" value="addTariff" />
			 
			<label><fmt:message key="add_tariff.jsp.name"/><span class="required">*</span></label> 
			<input type="text" name="name" required="required"/> 
			
			<label><fmt:message key="add_tariff.jsp.service"/><span class="required">*</span></label> 
			<select name="serviceType">
				<c:set var="k" value="0" />
				<c:forEach var="item" items="${services}">
					<c:set var="k" value="${k+1}" />
					<option value="${item.id}">${item.name}</option>
				</c:forEach>
			</select> 
			
			<label><fmt:message key="add_tariff.jsp.price"/><span class="required">*</span></label> 
			<input type="text" name="price" required="required" />

			<label><fmt:message key="add_tariff.jsp.description"/> <span class="required">*</span></label> 
			<input type="text" name="description" required="required"/> 
			
			<input type="submit" value="<fmt:message key="add_tariff.jsp.submit.add"/>" />
		</form>
		<%-- CONTENT --%>
	</section>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>