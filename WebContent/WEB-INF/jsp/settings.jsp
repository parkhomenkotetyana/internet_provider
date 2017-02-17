<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<fmt:message key="settings.jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page" />

<%@ include file="/WEB-INF/jspf/head.jspf"%>


<body>

	<c:if test="${not empty param.error}">
		<div id="error">
		<fmt:message key="jsp.error.invalid_data" var="error"/>
		<c:out value="${error}"></c:out>
		</div>
	</c:if>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<c:if test="${userRole.name == 'admin' }">
		<%@ include file="/WEB-INF/jspf/admin_menu.jspf"%>
	</c:if>
	<c:if test="${userRole.name == 'subscriber' }">
		<%@ include file="/WEB-INF/jspf/subscriber_menu.jspf"%>
	</c:if>

	<section>	
			<form class="add_form" action="controller" method="post">
			<input type="hidden" name="command" value="updateSettings" />

				<label><fmt:message key="settings.jsp.login"/></label>
				<input name="login" type="text" value="${login}" required="required" />

				<label><fmt:message key="settings.jsp.password"/></label>
				<input name="password" type="text" value="${password}" required="required" />

			<select name="language">
				<c:set var="k" value="0" />
				<c:forEach var="item" items="${locales}">
					<c:set var="k" value="${k+1}" />
					<option value="${item}">${item}</option>
				</c:forEach>
			</select> 

				 <input type="submit" value="<fmt:message key="settings.jsp.submit.change"/>" />
		</form>
	
	</section>
	
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	
</body>
</html>