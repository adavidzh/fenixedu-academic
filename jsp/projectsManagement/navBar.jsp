<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<br />
<br />
<bean:define id="code" value="" />
<logic:present name="infoCostCenter" scope="request">
	<bean:define id="cc" name="infoCostCenter" property="code" scope="request" />
	<bean:define id="code" value="<%="&amp;costCenter="+cc.toString()%>" />
</logic:present>
<p><strong><bean:message key="label.listByProject" /></strong></p>
<ul>
	<li><html:link page="<%="/projectReport.do?method=prepareShowReport&amp;reportType=expensesReport"+code%>">
		<bean:message key="link.expenses" />
	</html:link></li>
	<li><html:link page="<%="/projectReport.do?method=prepareShowReport&amp;reportType=completeExpensesReport"+code%>">
		<bean:message key="link.completeExpenses" />
	</html:link></li>
	<li><html:link page="<%="/projectReport.do?method=prepareShowReport&amp;reportType=revenueReport"+code%>">
		<bean:message key="link.revenue" />
	</html:link></li>
	<li><html:link page="<%="/projectReport.do?method=prepareShowReport&amp;reportType=cabimentosReport"+code%>">
		<bean:message key="label.cabimentos" />
	</html:link></li>
	<li><html:link page="<%="/projectReport.do?method=prepareShowReport&amp;reportType=adiantamentosReport"+code%>">
		<bean:message key="label.adiantamentos" />
	</html:link></li>
	<li><html:link page="<%="/projectReport.do?method=prepareShowReport&amp;reportType=projectBudgetaryBalanceReport"+code%>">
		<bean:message key="link.budgetaryBalance" />
	</html:link></li>
	<li><html:link page="<%="/projectReport.do?method=prepareShowReport&amp;reportType=openingProjectFileReport"+code%>">
		<bean:message key="link.openingProjectFile" />
	</html:link></li>
</ul>
<br />
<br />
<logic:present name="infoCostCenter" scope="request">
	<p><strong><bean:message key="label.listByCostCenter" /></strong></p>
</logic:present>
<logic:notPresent name="infoCostCenter" scope="request">
	<p><strong><bean:message key="label.listByCoordinator" /></strong></p>
</logic:notPresent>
<ul>
	<li><html:link page="<%="/projectReport.do?method=prepareShowReport&amp;reportType=summaryReport"+code%>">
		<bean:message key="link.summary" />
	</html:link></li>
	<%--
		<li><html:link
			page="/projectReport.do?method=getReport&amp;reportType=coordinatorBudgetaryBalanceReport">
			<bean:message key="link.budgetaryBalance" />
		</html:link></li>
		--%>
</ul>
<logic:present name="infoCostCenter" scope="request">
	<br />
	<br />
	<p><strong><bean:message key="label.overheadsList" /></strong></p>
	<ul>
		<li><html:link page="<%="/overheadReport.do?method=getReport&amp;reportType=generatedOverheadsReport"+code%>">
			<bean:message key="link.generatedOverheads" />
		</html:link></li>
		<li><html:link page="<%="/overheadReport.do?method=getReport&amp;reportType=transferedOverheadsReport"+code%>">
			<bean:message key="link.transferedOverheads" />
		</html:link></li>
		<li><html:link page="<%="/overheadReport.do?method=getReport&amp;reportType=overheadsSummaryReport"+code%>">
			<bean:message key="link.summary" />
		</html:link></li>

	</ul>
</logic:present>
<br />
<br />
<p><strong><bean:message key="label.help" /></strong></p>
<ul>
	<li><html:link page="<%="/projectReport.do?method=showHelp&amp;helpPage=listHelp"+code%>">
		<bean:message key="link.listHelp" />
	</html:link></li>
	<li><html:link page="<%="/projectReport.do?method=showHelp&amp;helpPage=explorationUnitsRubric"+code%>">
		<bean:message key="link.explorationUnitsRubric" />
	</html:link></li>
	<li><html:link page="<%="/projectReport.do?method=showHelp&amp;helpPage=expensesRubric"+code%>">
		<bean:message key="link.expensesRubric" />
	</html:link></li>
	<li><html:link page="<%="/projectReport.do?method=showHelp&amp;helpPage=revenueRubric"+code%>">
		<bean:message key="link.revenueRubric" />
	</html:link></li>
	<li><html:link page="<%="/projectReport.do?method=showHelp&amp;helpPage=projectTypesRubric"+code%>">
		<bean:message key="link.projectTypesRubric" />
	</html:link></li>
	<logic:present name="infoCostCenter" scope="request">
		<li><html:link page="<%="/projectReport.do?method=showHelp&amp;helpPage=overheadTypesRubric"+code%>">
			<bean:message key="link.overheadTypesRubric" />
		</html:link></li>
	</logic:present>
</ul>
<br />
<br />
<p><strong><bean:message key="title.accessDelegation" /></strong></p>
<ul>
	<li><html:link page="<%="/projectAccess.do?method=choosePerson"+code%>">
		<bean:message key="link.delegateAccess" />
	</html:link></li>
	<li><html:link page="<%="/projectAccess.do?method=showProjectsAccesses"+code%>">
		<bean:message key="link.showAccesses" />
	</html:link></li>
</ul>
<br />
<br />