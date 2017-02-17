<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<html>

<fmt:message key="login.jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page" />

<%@ include file="/WEB-INF/jspf/head.jspf" %>
	
<body id="login">
	
<c:if test="${not empty param.error}">
	<div id="error">
		<fmt:message key="jsp.error.invalid_data" var="error"/>
		<c:out value="${error}"></c:out>
	</div>
</c:if>

	<section class="login">
		<form id="login" action="controller" method="post">
			<input type="hidden" name="command" value="login"/>
			
				<fmt:message key="login.jsp.input.login" var="Login"/>						
				<input type="text" name="login" placeholder="${Login}" required="required"/><br/>

				<fmt:message key="login.jsp.input.password" var="Password"/>	
				<input type="password" placeholder="${Password}" name="password" required="required"/><br/>

			<div style="margin:0 auto; width:22%" class="g-recaptcha" data-sitekey="6LfEmhIUAAAAAB642P6nD2CoFnN6FegoaW42kZL_"></div>

			<input type="submit" value="<fmt:message key="login.jsp.submit.login"/>">								
		</form>
		
	<hr id="login_hr">

	<div class="fb">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="facebook"/>

			<button id="facebook" type="submit"><fmt:message key="login.jsp.button.fb"/></button>								
		</form>			
	</div>	

	</section>
	
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>