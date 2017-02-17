<%@tag pageEncoding="UTF-8"%>
<%@tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="contract" required="true"
	type="ua.nure.parkhomenko.SummaryTask4.db.entity.Contract"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<td>${contract.servicesTariffs.tariff.name}</td>
<td>${contract.servicesTariffs.service.name}</td>
<td>${contract.servicesTariffs.price}</td>
<td>${contract.servicesTariffs.description}</td>
<td>${contract.date}</td>


