<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<fmt:message key="edit_tariff.jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page" />

<%@ include file="/WEB-INF/jspf/head.jspf"%>


<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%@ include file="/WEB-INF/jspf/admin_menu.jspf"%>

	<section>

		<form class ="add_form" action="controller" method="post">
			<input type="hidden" name="command" value="editTariff" />
			<input type="hidden" name="serviceTariffId" value="${id}" />

				<label><fmt:message key="edit_tariff.jsp.name"/><span class="required">*</span> </label>
				<input name="tariffName" value="${tariffName}" type="text" required="required"/>

				<label><fmt:message key="edit_tariff.jsp.price"/><span class="required">*</span> </label>
				<input name="price" value="${price}" type="text" required="required"/>

				<label><fmt:message key="edit_tariff.jsp.description"/><span class="required">*</span> </label>
				<input name="description" value="${description}" type="text" required="required"/>

				 <input type="submit" value="<fmt:message key="edit_tariff.jsp.submit.edit"/>" />
		</form>

	</section>	
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>