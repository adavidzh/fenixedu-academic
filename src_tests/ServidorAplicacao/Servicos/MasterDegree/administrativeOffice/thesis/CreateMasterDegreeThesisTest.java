package ServidorAplicacao.Servicos.MasterDegree.administrativeOffice.thesis;

import java.util.ArrayList;
import java.util.List;

import DataBeans.InfoExternalPerson;
import DataBeans.InfoStudentCurricularPlan;
import DataBeans.InfoTeacher;
import ServidorAplicacao.Servico.exceptions.ExistingServiceException;
import ServidorAplicacao.Servico.exceptions.FenixServiceException;
import ServidorAplicacao.Servico.exceptions.GuiderAlreadyChosenServiceException;
import ServidorAplicacao.Servico.exceptions.RequiredGuidersServiceException;
import ServidorAplicacao.Servicos.MasterDegree.administrativeOffice.AdministrativeOfficeBaseTest;
import Util.TipoCurso;

/**
 * 
 * @author
 *   - Shezad Anavarali (sana@mega.ist.utl.pt)
 *   - Nadir Tarmahomed (naat@mega.ist.utl.pt)
 */
public class CreateMasterDegreeThesisTest extends AdministrativeOfficeBaseTest {

	/**
	 * @param testName
	 */
	public CreateMasterDegreeThesisTest(String testName) {
		super(testName);
		if (testName.equals("testCreateMasterDegreeThesisWithExistingMasterDegreeThesis")
			|| testName.equals("testCreateMasterDegreeThesisWithExistingDissertationTitle")) {
			this.dataSetFilePath =
				"etc/datasets/servicos/MasterDegree/administrativeOffice/thesis/testCreateMasterDegreeThesisDataSetExistingMasterDegreeThesis.xml";
		} else {
			this.dataSetFilePath = "etc/datasets/servicos/MasterDegree/administrativeOffice/thesis/testCreateMasterDegreeThesisDataSet.xml";
		}
	}

	protected String getNameOfServiceToBeTested() {
		return "CreateMasterDegreeThesis";
	}

	protected Object[] getServiceArgumentsForNotAuthenticatedUser() throws FenixServiceException {
		InfoTeacher infoTeacherGuider = new InfoTeacher();
		infoTeacherGuider.setIdInternal(new Integer(954));
		InfoTeacher infoTeacherAssistent = new InfoTeacher();
		infoTeacherAssistent.setIdInternal(new Integer(955));
		InfoExternalPerson infoExternalPerson = new InfoExternalPerson();
		infoExternalPerson.setIdInternal(new Integer(1));

		List guiders = new ArrayList();
		List assistentGuiders = new ArrayList();
		List externalAssistentGuiders = new ArrayList();

		guiders.add(infoTeacherGuider);
		assistentGuiders.add(infoTeacherAssistent);
		externalAssistentGuiders.add(infoExternalPerson);

		Object[] argsCreateMasterDegreeThesis = { null, null, "some title", guiders, assistentGuiders, externalAssistentGuiders };

		return argsCreateMasterDegreeThesis;
	}

	protected Object[] getServiceArgumentsForNotAuthorizedUser() throws FenixServiceException {
		Object[] argsReadStudentCurricularPlan = { new Integer(209), new TipoCurso(TipoCurso.MESTRADO)};
		InfoStudentCurricularPlan infoStudentCurricularPlan =
			(InfoStudentCurricularPlan) serviceManager.executar(
				userViewNotAuthorized,
				"student.ReadActiveStudentCurricularPlanByNumberAndDegreeType",
				argsReadStudentCurricularPlan);

		InfoTeacher infoTeacherGuider = new InfoTeacher();
		infoTeacherGuider.setIdInternal(new Integer(954));
		InfoTeacher infoTeacherAssistent = new InfoTeacher();
		infoTeacherAssistent.setIdInternal(new Integer(955));
		InfoExternalPerson infoExternalPerson = new InfoExternalPerson();
		infoExternalPerson.setIdInternal(new Integer(1));

		List guiders = new ArrayList();
		List assistentGuiders = new ArrayList();
		List externalAssistentGuiders = new ArrayList();

		guiders.add(infoTeacherGuider);
		assistentGuiders.add(infoTeacherAssistent);
		externalAssistentGuiders.add(infoExternalPerson);

		Object[] argsCreateMasterDegreeThesis =
			{ userViewNotAuthorized, infoStudentCurricularPlan, "some title", guiders, assistentGuiders, externalAssistentGuiders };

		return argsCreateMasterDegreeThesis;
	}

	public void testSuccessfulCreateMasterDegreeThesis() {
		try {
			Object[] argsReadStudentCurricularPlan = { new Integer(209), new TipoCurso(TipoCurso.MESTRADO)};
			InfoStudentCurricularPlan infoStudentCurricularPlan =
				(InfoStudentCurricularPlan) serviceManager.executar(
					userView,
					"student.ReadActiveStudentCurricularPlanByNumberAndDegreeType",
					argsReadStudentCurricularPlan);

			Object[] argsSearch = { "Xxx" };
			List infoExternalPersons = (List) serviceManager.executar(userView, "SearchExternalPersonsByName", argsSearch);

			InfoTeacher infoTeacherGuider = new InfoTeacher();
			infoTeacherGuider.setIdInternal(new Integer(954));
			InfoTeacher infoTeacherAssistent = new InfoTeacher();
			infoTeacherAssistent.setIdInternal(new Integer(955));
			InfoExternalPerson infoExternalPerson = new InfoExternalPerson();
			infoExternalPerson.setIdInternal(new Integer(1));

			List guiders = new ArrayList();
			List assistentGuiders = new ArrayList();
			List externalAssistentGuiders = new ArrayList();

			guiders.add(infoTeacherGuider);
			assistentGuiders.add(infoTeacherAssistent);
			externalAssistentGuiders.add(infoExternalPerson);

			Object[] argsCreateMasterDegreeThesis =
				{ userView, infoStudentCurricularPlan, "some title", guiders, assistentGuiders, externalAssistentGuiders };

			serviceManager.executar(this.userView, getNameOfServiceToBeTested(), argsCreateMasterDegreeThesis);
			compareDataSetUsingExceptedDataSetTableColumns("etc/datasets/servicos/MasterDegree/administrativeOffice/thesis/testExpectedCreateMasterDegreeThesisDataSet.xml");
			//ok

		} catch (Exception ex) {
			ex.printStackTrace();
			fail("testSuccessfulCreateMasterDegreeThesis" + ex.getMessage());
		}
	}

	public void testCreateMasterDegreeThesisWithExistingMasterDegreeThesis() {
		try {
			Object[] argsReadStudentCurricularPlan = { new Integer(142), new TipoCurso(TipoCurso.MESTRADO)};
			InfoStudentCurricularPlan infoStudentCurricularPlan =
				(InfoStudentCurricularPlan) serviceManager.executar(
					userView,
					"student.ReadActiveStudentCurricularPlanByNumberAndDegreeType",
					argsReadStudentCurricularPlan);

			InfoTeacher infoTeacherGuider = new InfoTeacher();
			infoTeacherGuider.setIdInternal(new Integer(954));
			InfoTeacher infoTeacherAssistent = new InfoTeacher();
			infoTeacherAssistent.setIdInternal(new Integer(955));
			InfoExternalPerson infoExternalPerson = new InfoExternalPerson();
			infoExternalPerson.setIdInternal(new Integer(1));

			List guiders = new ArrayList();
			List assistentGuiders = new ArrayList();
			List externalAssistentGuiders = new ArrayList();

			guiders.add(infoTeacherGuider);
			assistentGuiders.add(infoTeacherAssistent);
			externalAssistentGuiders.add(infoExternalPerson);

			Object[] argsCreateMasterDegreeThesis =
				{ userView, infoStudentCurricularPlan, "Existing Title", guiders, assistentGuiders, externalAssistentGuiders };

			serviceManager.executar(this.userView, getNameOfServiceToBeTested(), argsCreateMasterDegreeThesis);
			fail("testCreateMasterDegreeThesisWithExistingMasterDegreeThesis: Service did not throw ExistingServiceException");

		} catch (ExistingServiceException ex) {
			//test passed
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("testCreateMasterDegreeThesisWithExistingMasterDegreeThesis" + ex.getMessage());
		}
	}

	public void testCreateMasterDegreeThesisWithExistingDissertationTitle() {
		try {
			Object[] argsReadStudentCurricularPlan = { new Integer(209), new TipoCurso(TipoCurso.MESTRADO)};
			InfoStudentCurricularPlan infoStudentCurricularPlan =
				(InfoStudentCurricularPlan) serviceManager.executar(
					userView,
					"student.ReadActiveStudentCurricularPlanByNumberAndDegreeType",
					argsReadStudentCurricularPlan);

			Object[] argsSearch = { "Xxx" };
			List infoExternalPersons = (List) serviceManager.executar(userView, "SearchExternalPersonsByName", argsSearch);

			InfoTeacher infoTeacherGuider = new InfoTeacher();
			infoTeacherGuider.setIdInternal(new Integer(954));
			InfoTeacher infoTeacherAssistent = new InfoTeacher();
			infoTeacherAssistent.setIdInternal(new Integer(955));
			InfoExternalPerson infoExternalPerson = new InfoExternalPerson();
			infoExternalPerson.setIdInternal(new Integer(1));

			List guiders = new ArrayList();
			List assistentGuiders = new ArrayList();
			List externalAssistentGuiders = new ArrayList();

			guiders.add(infoTeacherGuider);
			assistentGuiders.add(infoTeacherAssistent);
			externalAssistentGuiders.add(infoExternalPerson);

			Object[] argsCreateMasterDegreeThesis =
				{ userView, infoStudentCurricularPlan, "Existing Title", guiders, assistentGuiders, externalAssistentGuiders };
			serviceManager.executar(this.userView, getNameOfServiceToBeTested(), argsCreateMasterDegreeThesis);

			fail("testCreateMasterDegreeThesisWithExistingDissertationTitle did not throw ExistingServiceException");

		} catch (ExistingServiceException ex) {
			//test passed
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("testCreateMasterDegreeThesisWithExistingDissertationTitle" + ex.getMessage());
		}
	}

	public void testCreateMasterDegreeThesisWithoutGuiders() {
		try {
			Object[] argsReadStudentCurricularPlan = { new Integer(209), new TipoCurso(TipoCurso.MESTRADO)};
			InfoStudentCurricularPlan infoStudentCurricularPlan =
				(InfoStudentCurricularPlan) serviceManager.executar(
					userView,
					"student.ReadActiveStudentCurricularPlanByNumberAndDegreeType",
					argsReadStudentCurricularPlan);

			InfoTeacher infoTeacherAssistent = new InfoTeacher();
			infoTeacherAssistent.setIdInternal(new Integer(955));
			InfoExternalPerson infoExternalPerson = new InfoExternalPerson();
			infoExternalPerson.setIdInternal(new Integer(1));

			List guiders = new ArrayList();
			List assistentGuiders = new ArrayList();
			List externalAssistentGuiders = new ArrayList();

			assistentGuiders.add(infoTeacherAssistent);
			externalAssistentGuiders.add(infoExternalPerson);

			Object[] argsCreateMasterDegreeThesis =
				{ userView, infoStudentCurricularPlan, "some title", guiders, assistentGuiders, externalAssistentGuiders };

			serviceManager.executar(this.userView, getNameOfServiceToBeTested(), argsCreateMasterDegreeThesis);

			fail("testCreateMasterDegreeThesisWithoutGuiders did not throw RequiredGuidersServiceException");

		} catch (RequiredGuidersServiceException e) {
			//ok

		} catch (Exception ex) {
			ex.printStackTrace();
			fail("testCreateMasterDegreeThesisWithoutGuiders" + ex.getMessage());
		}
	}

	public void testCreateMasterDegreeThesisWithTeacherBeingGuiderAndAssistentGuider() {
		try {
			Object[] argsReadStudentCurricularPlan = { new Integer(209), new TipoCurso(TipoCurso.MESTRADO)};
			InfoStudentCurricularPlan infoStudentCurricularPlan =
				(InfoStudentCurricularPlan) serviceManager.executar(
					userView,
					"student.ReadActiveStudentCurricularPlanByNumberAndDegreeType",
					argsReadStudentCurricularPlan);

			InfoTeacher infoTeacherGuider = new InfoTeacher();
			infoTeacherGuider.setIdInternal(new Integer(954));
			InfoTeacher infoTeacherAssistent = new InfoTeacher();
			infoTeacherAssistent.setIdInternal(new Integer(955));
			InfoExternalPerson infoExternalPerson = new InfoExternalPerson();
			infoExternalPerson.setIdInternal(new Integer(1));

			List guiders = new ArrayList();
			List assistentGuiders = new ArrayList();
			List externalAssistentGuiders = new ArrayList();

			guiders.add(infoTeacherGuider);
			assistentGuiders.add(infoTeacherAssistent);
			assistentGuiders.add(infoTeacherGuider);
			externalAssistentGuiders.add(infoExternalPerson);

			Object[] argsCreateMasterDegreeThesis =
				{ userView, infoStudentCurricularPlan, "some title", guiders, assistentGuiders, externalAssistentGuiders };

			serviceManager.executar(this.userView, getNameOfServiceToBeTested(), argsCreateMasterDegreeThesis);

			fail("testCreateMasterDegreeThesisWithTeacherBeingGuiderAndAssistentGuider did not throw GuiderAlreadyChosenServiceException");

		} catch (GuiderAlreadyChosenServiceException e) {
			//ok
			
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("testCreateMasterDegreeThesisWithTeacherBeingGuiderAndAssistentGuider" + ex.getMessage());
		}
	}

}
