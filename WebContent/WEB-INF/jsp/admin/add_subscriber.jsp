<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<fmt:message key="add_subscriber.jsp.title" var="title"/>
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
	<section>

		<form class="add_form" action="controller" method="post">
			<input type="hidden" name="command" value="addSubscriber" /> 
			
			<label><fmt:message key="add_subscriber.jsp.full_name"/><span class="required">*</span> </label> 
			<input name="fullName" type="text" required="required"/> 
			
			<label><fmt:message key="add_subscriber.jsp.date_of_birth"/><span class="required">*</span></label> 
			<input placeholder="YYYY-MM-DD" type="text" name="dob" required="required" /> 
			
			<label><fmt:message key="add_subscriber.jsp.address"/><span class="required">*</span></label> 
			<input name="address" type="text" required="required" /> 
			
			<label><fmt:message key="add_subscriber.jsp.passport"/><span class="required">*</span></label> 
			<input name="passport" type="text" required="required" /> 
			
			<label><fmt:message key="add_subscriber.jsp.login"/><span class="required">*</span></label> 
			<input name="login" type="text" required="required" /> 
			
			<label><fmt:message key="add_subscriber.jsp.password"/><span class="required">*</span></label> 
			<input name="password" type="text" required="required"/> 
			
			<input type="submit" value="<fmt:message key="add_subscriber.jsp.submit.add"/>" />

		</form>
	
	</section>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>