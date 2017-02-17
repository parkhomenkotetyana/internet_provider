<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ taglib prefix="subscriber" tagdir="/WEB-INF/tags"%>
<html>

<fmt:message key="account.jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page" />

<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%@ include file="/WEB-INF/jspf/subscriber_menu.jspf"%>
	
	<section>
		<p id="balance"><fmt:message key="account.jsp.balance" />: ${money} <fmt:message key="account.jsp.hrivnas" />!</p>
	<div id="payment">
		<form class="add_form" action="controller" method="post">	
			<input type="hidden" name="command" value="accountPay" /> 
			
			<label><fmt:message key="account.jsp.amount"/> <span class="required">*</span></label>
			<input type="text" name="sum" size="5px" required="required" style="text-align: center;" />
			<label><fmt:message key="account.jsp.card_number"/> <span class="required">*</span> </label>
			<input type="text" name="cardNumber" size="20px" required="required" maxlength="16" style="text-align: center;" /> 
			<label>CVV2/CVC2 <span class="required">*</span></label>
			<input type="password" name="code" size="3" placeholder="XXX" required="required" maxlength="3" style="text-align: center;" />
			<br /> 			
			<input type="submit" value="<fmt:message key="account.jsp.submit.pay"/> " />			
		</form>
	</div>
<c:if test="${not empty contracts}">
		
	<p id="contracts"><fmt:message key="account.jsp.contracts"/>:</p> 

	<table> 	
		<thead> 
			<tr>
				<th><fmt:message key="account.jsp.tariff"/></th>
				<th><fmt:message key="account.jsp.service"/></th>
				<th><fmt:message key="account.jsp.price"/></th>
				<th><fmt:message key="account.jsp.description"/></th>
				<th><fmt:message key="account.jsp.date"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="contract" items="${contracts}">
			<tr>
				<subscriber:contracts contract="${contract}"/>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</c:if>
	</section>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>