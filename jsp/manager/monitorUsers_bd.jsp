<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %><%@ taglib uri="/WEB-INF/taglibs-datetime.tld" prefix="dt" %><h2><bean:message key="manager.monitor.users.title"/></h2><br /><logic:present name="userLoggingIsOn">		<logic:equal name="userLoggingIsOn" value="true">			<span class="error">				Users monitorring is activated.			</span>			<br /><br />			<html:link page="/monitorUsers.do?method=deactivateMonotoring">				<bean:message key="manager.monitor.users.deactivate-monitorring"/>			</html:link>			<html:link page="/monitorUsers.do?method=clearUserLogs">				<bean:message key="manager.monitor.users.clear-user-logs"/>			</html:link>			<html:link page="/monitorUsers.do?method=monitor">				<bean:message key="manager.monitor.users.refresh"/>			</html:link>			<br /><br />			<logic:present name="userLogs">				<span class="error">					<strong>Nota:</strong> Este g�nero de monitoriza��o pode consumir grandes quantidades de mem�ria!				</span>				<table>						<tr>							<td class="listClasses-header">								<bean:message key="manager.monitor.users.username"/>							</td>							<td class="listClasses-header">								<bean:message key="manager.monitor.users.service.footPrint"/>							</td>							<td class="listClasses-header">								<bean:message key="manager.monitor.users.service.accessTime"/>							</td>						</tr>					<logic:iterate id="userLog" name="userLogs">						<bean:size id="numberServices" name="userLog" property="value.mapServicesLog"/>						<logic:iterate id="serviceLog" name="userLog" property="value.mapServicesLog" length="1">							<bean:size id="numberDates" name="serviceLog" property="value"/>							<logic:iterate id="serviceCallTime" name="serviceLog" property="value" length="1">								<tr>									<td class="listClasses" rowspan='<%= "" + (((Integer) pageContext.findAttribute("numberServices")).intValue() * ((Integer) pageContext.findAttribute("numberDates")).intValue()) %>'>										<bean:write name="userLog" property="value.userView.utilizador"/>									</td>									<td class="listClasses" rowspan='<%= pageContext.findAttribute("numberDates").toString() %>'>										<bean:write name="serviceLog" property="key"/>									</td>									<td class="listClasses">										<dt:format pattern="yyyy/MM/dd HH:mm:ss">											<bean:write name="serviceCallTime" property="timeInMillis"/>										</dt:format>									</td>								</tr>							</logic:iterate>							<logic:iterate id="serviceCallTime" name="serviceLog" property="value" offset="1">								<tr>									<td class="listClasses">										<dt:format pattern="yyyy/MM/dd HH:mm:ss">											<bean:write name="serviceCallTime" property="timeInMillis"/>										</dt:format>									</td>								</tr>							</logic:iterate>						</logic:iterate>						<logic:iterate id="serviceLog" name="userLog" property="value.mapServicesLog" offset="1">							<bean:size id="numberDates" name="serviceLog" property="value"/>							<logic:iterate id="serviceCallTime" name="serviceLog" property="value" length="1">								<tr>									<td class="listClasses" rowspan='<%= pageContext.findAttribute("numberDates").toString() %>'>										<bean:write name="serviceLog" property="key"/>									</td>									<td class="listClasses">										<dt:format pattern="yyyy/MM/dd HH:mm:ss">											<bean:write name="serviceCallTime" property="timeInMillis"/>										</dt:format>									</td>								</tr>							</logic:iterate>							<logic:iterate id="serviceCallTime" name="serviceLog" property="value" offset="1">								<tr>									<td class="listClasses">										<dt:format pattern="yyyy/MM/dd HH:mm:ss">											<bean:write name="serviceCallTime" property="timeInMillis"/>										</dt:format>									</td>								</tr>							</logic:iterate>						</logic:iterate>					</logic:iterate>				</table>			</logic:present>		</logic:equal>		<logic:equal name="userLoggingIsOn" value="false">			<span class="error">				User monitorring is deactivated.			</span>			<br /><br />			<html:link page="/monitorUsers.do?method=activateMonotoring">				<bean:message key="manager.monitor.users.activate-monitorring"/>			</html:link>		</logic:equal></logic:present><logic:notPresent name="userLoggingIsOn">	<span class="error">		Error obtaining log information.	</span></logic:notPresent>