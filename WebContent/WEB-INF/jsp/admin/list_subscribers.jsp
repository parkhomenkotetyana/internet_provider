<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<fmt:message key="list_subscribers.jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page" />

<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%@ include file="/WEB-INF/jspf/admin_menu.jspf"%>

	<section>

	<div id="firsttable">
			<table class="left_part">
				<thead>
					<tr>
						<th>№</th>
						<th><fmt:message key="list_subscribers.jsp.full_name"/></th>
						<th><fmt:message key="list_subscribers.jsp.date_of_birth"/></th>
						<th><fmt:message key="list_subscribers.jsp.address"/></th>
						<th><fmt:message key="list_subscribers.jsp.passport"/></th>
						<th><fmt:message key="list_subscribers.jsp.login"/></th>
						<th><fmt:message key="list_subscribers.jsp.password"/></th>
					</tr>
				</thead>
				<tbody>
					<c:set var="k" value="0" />
					<c:forEach var="item" items="${subscribers}">
						<c:set var="k" value="${k+1}" />
						<tr>
							<td><c:out value="${k}" /></td>
							<td>${item.fullName}</td>
							<td>${item.dateOfBirth}</td>
							<td>${item.address}</td>
							<td>${item.passport}</td>
							<td>${item.login}</td>
							<td>${item.password}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

</div>
<div id="secondtable">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="block" />
			<table class="right_part">
				<thead>
					<tr/>
				</thead>
				<tbody>
					<c:set var="k" value="0" />
					<c:forEach var="item" items="${subscribers}">
						<c:set var="k" value="${k+1}" />
						<tr>
							<td><input class="checkBox" type="checkbox" onclick="myFunction('block')" name="block" value="${item.id}" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<input id="block" type="submit" value="<fmt:message key="list_subscribers.jsp.submit.block"/>">
		</form>
</div> 
<div id="thirdtable">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="unblock" />
			<table class="right_part">
				<thead>
					<tr/>
				</thead>
				<tbody>
					<c:set var="k" value="0" />
					<c:forEach var="item" items="${subscribers}">
						<c:set var="k" value="${k+1}" />
						<tr>
							<td><input class="checkBox" type="checkbox" onclick="myFunction('unblock')" name="unblock" value="${item.id}" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<input id="unblock" type="submit" value="<fmt:message key="list_subscribers.jsp.submit.unblock"/>">
		</form>
		</div>

	</section>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

<script type="text/javascript">
  function myFunction(s){
    var inputs = document.getElementsByTagName("input");
    for (i = 0; i < (inputs.length + 0); i++) { 
      if (inputs[i].getAttribute("name") == s) {
        continue;
      } else {
        inputs[i].checked = false;
      }
    }
  }
</script>

</body>