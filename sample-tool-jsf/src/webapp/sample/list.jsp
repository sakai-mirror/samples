<!-- sample-tool-jsf list.jsp -->
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>

<%
	response.setContentType("text/html; charset=UTF-8");
	response.addDateHeader("Expires", System.currentTimeMillis() - (1000L * 60L * 60L * 24L * 365L));
	response.addDateHeader("Last-Modified", System.currentTimeMillis());
	response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
	response.addHeader("Pragma", "no-cache");
%>

<f:view>
	<sakai:view_container title="#{msgs.list_title}">

		<h:form id="helloForm">
			<h:messages /><br />

			<h:outputText value="#{msgs.list_info}" /><br />

			<h:commandLink action="main">
				<h:outputText value="#{msgs.goto_main}"/>
			</h:commandLink>
		</h:form>

	</sakai:view_container>
</f:view>
		