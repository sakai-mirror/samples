<!-- sample-tool-jsf main.jsp -->
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>

<%-- this helps keep our (dynamic) pages from being cached --%>
<%
	response.setContentType("text/html; charset=UTF-8");
	response.addDateHeader("Expires", System.currentTimeMillis() - (1000L * 60L * 60L * 24L * 365L));
	response.addDateHeader("Last-Modified", System.currentTimeMillis());
	response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
	response.addHeader("Pragma", "no-cache");
%>

<f:view>
	<sakai:view_container title="#{msgs.main_title}">
		<sakai:view_content>
			<h:form id="main">

				<%-- The in-page view title --%>
				<sakai:view_title value="#{msgs.main_view_title}" />

				<%-- start with a tool bar --%>
				<sakai:tool_bar>
					<sakai:tool_bar_item action="#{JsfSampleBean.processToolBarItem}" value="#{msgs.main_toolbar_item}" />
					<sakai:tool_bar_item action="#{JsfSampleBean.processToolBarItem}" value="#{msgs.main_toolbar_item}" />
					<sakai:tool_bar_item action="#{JsfSampleBean.processToolBarItem}" value="#{msgs.main_toolbar_item}" />
				</sakai:tool_bar>

				<%-- some instructions or other information to the user to the user --%>
				<sakai:doc_section>
					<sakai:messages />
					<h:graphicImage value="/sakai.jpg" />
					<sakai:instruction_message value="#{msgs.main_info}" />
				</sakai:doc_section>

				<%-- a list of some things that can be drilled down into --%>
				<h:dataTable id="sample" value="#{JsfSampleBean.items}" var="item" styleClass="listHier">

					<h:column>
						<f:facet name="header">
							<h:outputText value="#{msgs.item_id}" />
						</f:facet>
						<h:commandLink action="#{item.processClick}">
							<h:outputText value="#{item.id}" />
						</h:commandLink>
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="#{msgs.item_a}" />
						</f:facet>
						<h:outputText value="#{item.a}" />
					</h:column>
						
					<h:column>
						<f:facet name="header">
							<h:outputText value="#{msgs.item_b}" />
						</f:facet>
						<h:outputText value="#{item.b}" />
					</h:column>
	
					<h:column>
						<f:facet name="header">
							<h:outputText value="#{msgs.item_c}" />
						</f:facet>
						<h:outputText value="#{item.c}" />
					</h:column>
					
					<h:column>
						<f:facet name="header">
							<h:outputText value="#{msgs.select}" />
						</f:facet>
						<h:selectBooleanCheckbox id="select" value="#{item.selected}"/>
					</h:column>

				</h:dataTable>

				<%-- end with a button bar --%>
				<sakai:button_bar>
					<sakai:button_bar_item action="list" value="#{msgs.goto_list}" accesskey="l" />
					<sakai:button_bar_item action="list" value="#{msgs.goto_list}" />
					<sakai:button_bar_item action="list" value="#{msgs.goto_list}" />
				</sakai:button_bar>

			</h:form>
		</sakai:view_content>
	</sakai:view_container>
</f:view>
