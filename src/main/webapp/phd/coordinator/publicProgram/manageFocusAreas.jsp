<%--

    Copyright © 2002 Instituto Superior Técnico

    This file is part of FenixEdu Academic.

    FenixEdu Academic is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FenixEdu Academic is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ page isELIgnored="true"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html:xhtml/>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>

<h2><bean:message key="label.phd.focusAreas" bundle="PHD_RESOURCES" /></h2>
<style>th+th{width:35%}</style>
<fr:view name="focusAreas">
	<fr:schema bundle="PHD_RESOURCES" type="org.fenixedu.academic.domain.phd.PhdProgramFocusArea">
		<fr:slot name="name.content" key="label.phd.institution.public.candidacy.focus.area">
		</fr:slot>
	</fr:schema>
	<fr:layout name="tabular">
		<fr:property name="linkGroupSeparator" value="&nbsp&nbsp|&nbsp&nbsp" />	
	
		<fr:property name="classes" value="tstyle2 thlight mtop15" />
		
		<fr:property name="order(thesisSubjects)" value="1"/>
		<fr:property name="linkFormat(thesisSubjects)" value="/candidacies/phdProgramCandidacyProcess.do?method=manageThesisSubjects&focusAreaId=${externalId}"/>
		<fr:property name="key(thesisSubjects)" value="link.phd.thesisSubjects"/>
		<fr:property name="bundle(thesisSubjects)" value="PHD_RESOURCES"/>
				
		<fr:property name="order(activeFocus)" value="2"/>
		<fr:property name="visibleIf(activeFocus)" value="active" />
		<fr:property name="linkFormat(activeFocus)" value="/candidacies/phdProgramCandidacyProcess.do?method=toggleFocusAreaAction&focusAreaId=${externalId}"/>
		<fr:property name="key(activeFocus)" value="link.phd.focusArea.deactivate"/>
		<fr:property name="bundle(activeFocus)" value="PHD_RESOURCES"/>
		
		<fr:property name="order(inactiveFocus)" value="2"/>
		<fr:property name="visibleIfNot(inactiveFocus)" value="active" />
		<fr:property name="linkFormat(inactiveFocus)" value="/candidacies/phdProgramCandidacyProcess.do?method=toggleFocusAreaAction&focusAreaId=${externalId}"/>
		<fr:property name="key(inactiveFocus)" value="link.phd.focusArea.activate"/>
		<fr:property name="bundle(inactiveFocus)" value="PHD_RESOURCES"/>		
	</fr:layout>
</fr:view>
