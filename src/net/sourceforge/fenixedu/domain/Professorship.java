package net.sourceforge.fenixedu.domain;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.fenixedu.applicationTier.Servico.teacher.professorship.ResponsibleForValidator;
import net.sourceforge.fenixedu.applicationTier.Servico.teacher.professorship.ResponsibleForValidator.InvalidCategory;
import net.sourceforge.fenixedu.applicationTier.Servico.teacher.professorship.ResponsibleForValidator.MaxResponsibleForExceed;
import net.sourceforge.fenixedu.domain.credits.event.ICreditsEventOriginator;
import net.sourceforge.fenixedu.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.domain.inquiries.InquiryResponsePeriod;
import net.sourceforge.fenixedu.domain.inquiries.StudentInquiriesTeachingResult;
import net.sourceforge.fenixedu.domain.inquiries.teacher.InquiryResponsePeriodType;
import net.sourceforge.fenixedu.domain.teacher.DegreeTeachingService;

import org.apache.commons.beanutils.BeanComparator;

import pt.ist.fenixWebFramework.services.Service;

/**
 * @author Jo�o Mota
 */
public class Professorship extends Professorship_Base implements ICreditsEventOriginator {

    public static final Comparator<Professorship> COMPARATOR_BY_PERSON_NAME = new BeanComparator("person.name", Collator
	    .getInstance());

    public Professorship() {
	super();
	setRootDomainObject(RootDomainObject.getInstance());
    }

    public boolean belongsToExecutionPeriod(ExecutionSemester executionSemester) {
	return this.getExecutionCourse().getExecutionPeriod().equals(executionSemester);
    }

    public static Professorship create(Boolean responsibleFor, ExecutionCourse executionCourse, Teacher teacher, Double hours)
	    throws MaxResponsibleForExceed, InvalidCategory {

	for (final Professorship otherProfessorship : executionCourse.getProfessorshipsSet()) {
	    if (teacher == otherProfessorship.getTeacher()) {
		throw new DomainException("error.teacher.already.associated.to.professorship");
	    }
	}

	if (responsibleFor == null || executionCourse == null || teacher == null)
	    throw new NullPointerException();

	Professorship professorShip = new Professorship();
	professorShip.setHours((hours == null) ? new Double(0.0) : hours);
	professorShip.setExecutionCourse(executionCourse);
	professorShip.setPerson(teacher.getPerson());

	professorShip.setResponsibleFor(responsibleFor);
	executionCourse.moveSummariesFromTeacherToProfessorship(teacher, professorShip);

	return professorShip;
    }

    @Service
    public static Professorship create(Boolean responsibleFor, ExecutionCourse executionCourse, Person person, Double hours)
	    throws MaxResponsibleForExceed, InvalidCategory {

	for (final Professorship otherProfessorship : executionCourse.getProfessorshipsSet()) {
	    if (person == otherProfessorship.getPerson()) {
		throw new DomainException("error.teacher.already.associated.to.professorship");
	    }
	}

	if (responsibleFor == null || executionCourse == null || person == null)
	    throw new NullPointerException();

	Professorship professorShip = new Professorship();
	professorShip.setHours((hours == null) ? new Double(0.0) : hours);
	professorShip.setExecutionCourse(executionCourse);
	professorShip.setPerson(person);

	if (responsibleFor.booleanValue() && professorShip.getPerson().getTeacher() != null) {
	    ResponsibleForValidator.getInstance().validateResponsibleForList(professorShip.getPerson().getTeacher(),
		    professorShip.getExecutionCourse(), professorShip);
	    professorShip.setResponsibleFor(Boolean.TRUE);
	} else {
	    professorShip.setResponsibleFor(Boolean.FALSE);
	}
	if (person.getTeacher() != null) {
	    executionCourse.moveSummariesFromTeacherToProfessorship(person.getTeacher(), professorShip);
	}

	return professorShip;
    }

    public void delete() {
	if (canBeDeleted()) {
	    removeExecutionCourse();
	    removePerson();
	    removeRootDomainObject();
	    deleteDomainObject();
	}
    }

    public boolean canBeDeleted() {
	if (hasAnyAssociatedSummaries())
	    throw new DomainException("error.remove.professorship");
	if (hasAnyAssociatedShiftProfessorship())
	    throw new DomainException("error.remove.professorship");
	if (hasAnySupportLessons())
	    throw new DomainException("error.remove.professorship");
	if (hasAnyDegreeTeachingServices())
	    throw new DomainException("error.remove.professorship");
	if (hasAnyTeacherMasterDegreeServices())
	    throw new DomainException("error.remove.professorship");
	if (hasTeachingInquiry())
	    throw new DomainException("error.remove.professorship");
	if (hasAnyStudentInquiriesTeachingResults())
	    throw new DomainException("error.remove.professorship");
	if (hasAnyAssociatedShiftProfessorship())
	    throw new DomainException("error.remove.professorship");
	return true;
    }

    public boolean isResponsibleFor() {
	return getResponsibleFor().booleanValue();
    }

    public static List<Professorship> readByDegreeCurricularPlanAndExecutionYear(DegreeCurricularPlan degreeCurricularPlan,
	    ExecutionYear executionYear) {

	Set<Professorship> professorships = new HashSet<Professorship>();
	for (CurricularCourse curricularCourse : degreeCurricularPlan.getCurricularCourses()) {
	    for (ExecutionCourse executionCourse : curricularCourse.getExecutionCoursesByExecutionYear(executionYear)) {
		professorships.addAll(executionCourse.getProfessorships());
	    }
	}
	return new ArrayList<Professorship>(professorships);
    }

    public static List<Professorship> readByDegreeCurricularPlanAndExecutionYearAndBasic(
	    DegreeCurricularPlan degreeCurricularPlan, ExecutionYear executionYear, Boolean basic) {

	Set<Professorship> professorships = new HashSet<Professorship>();
	for (CurricularCourse curricularCourse : degreeCurricularPlan.getCurricularCourses()) {
	    if (curricularCourse.getBasic().equals(basic)) {
		for (ExecutionCourse executionCourse : curricularCourse.getExecutionCoursesByExecutionYear(executionYear)) {
		    professorships.addAll(executionCourse.getProfessorships());
		}
	    }
	}
	return new ArrayList<Professorship>(professorships);
    }

    public static List<Professorship> readByDegreeCurricularPlanAndExecutionPeriod(DegreeCurricularPlan degreeCurricularPlan,
	    ExecutionSemester executionSemester) {

	Set<Professorship> professorships = new HashSet<Professorship>();
	for (CurricularCourse curricularCourse : degreeCurricularPlan.getCurricularCourses()) {
	    for (ExecutionCourse executionCourse : curricularCourse.getExecutionCoursesByExecutionPeriod(executionSemester)) {
		professorships.addAll(executionCourse.getProfessorships());
	    }
	}
	return new ArrayList<Professorship>(professorships);
    }

    public static List<Professorship> readByDegreeCurricularPlansAndExecutionYearAndBasic(
	    List<DegreeCurricularPlan> degreeCurricularPlans, ExecutionYear executionYear, Boolean basic) {

	Set<Professorship> professorships = new HashSet<Professorship>();
	for (DegreeCurricularPlan degreeCurricularPlan : degreeCurricularPlans) {
	    for (CurricularCourse curricularCourse : degreeCurricularPlan.getCurricularCourses()) {
		if (curricularCourse.getBasic() == null || curricularCourse.getBasic().equals(basic)) {
		    if (executionYear != null) {
			for (ExecutionCourse executionCourse : curricularCourse.getExecutionCoursesByExecutionYear(executionYear)) {
			    professorships.addAll(executionCourse.getProfessorships());
			}
		    } else {
			for (ExecutionCourse executionCourse : curricularCourse.getAssociatedExecutionCourses()) {
			    professorships.addAll(executionCourse.getProfessorships());
			}
		    }
		}
	    }
	}
	return new ArrayList<Professorship>(professorships);
    }

    public static List<Professorship> readByDegreeCurricularPlansAndExecutionYear(
	    List<DegreeCurricularPlan> degreeCurricularPlans, ExecutionYear executionYear) {

	Set<Professorship> professorships = new HashSet<Professorship>();
	for (DegreeCurricularPlan degreeCurricularPlan : degreeCurricularPlans) {
	    for (CurricularCourse curricularCourse : degreeCurricularPlan.getCurricularCourses()) {
		if (executionYear != null) {
		    for (ExecutionCourse executionCourse : curricularCourse.getExecutionCoursesByExecutionYear(executionYear)) {
			professorships.addAll(executionCourse.getProfessorships());
		    }
		} else {
		    for (ExecutionCourse executionCourse : curricularCourse.getAssociatedExecutionCourses()) {
			professorships.addAll(executionCourse.getProfessorships());
		    }
		}
	    }
	}
	return new ArrayList<Professorship>(professorships);
    }

    public SortedSet<DegreeTeachingService> getDegreeTeachingServicesOrderedByShift() {
	final SortedSet<DegreeTeachingService> degreeTeachingServices = new TreeSet<DegreeTeachingService>(
		DegreeTeachingService.DEGREE_TEACHING_SERVICE_COMPARATOR_BY_SHIFT);
	degreeTeachingServices.addAll(getDegreeTeachingServicesSet());
	return degreeTeachingServices;
    }
    
    public DegreeTeachingService getDegreeTeachingServiceByShift(Shift shift) {
	for (DegreeTeachingService degreeTeachingService : getDegreeTeachingServicesSet()) {
	    if (degreeTeachingService.getShift() == shift) {
		return degreeTeachingService;
	    }
	}
	return null;
    }

    public SortedSet<SupportLesson> getSupportLessonsOrderedByStartTimeAndWeekDay() {
	final SortedSet<SupportLesson> supportLessons = new TreeSet<SupportLesson>(
		SupportLesson.SUPPORT_LESSON_COMPARATOR_BY_HOURS_AND_WEEK_DAY);
	supportLessons.addAll(getSupportLessonsSet());
	return supportLessons;
    }

    public boolean isTeachingInquiriesToAnswer() {
	final ExecutionCourse executionCourse = this.getExecutionCourse();
	final InquiryResponsePeriod responsePeriod = executionCourse.getExecutionPeriod().getInquiryResponsePeriod(
		InquiryResponsePeriodType.TEACHING);
	if (responsePeriod == null || !responsePeriod.isOpen() || !executionCourse.getAvailableForInquiries()
		|| executionCourse.getStudentInquiriesCourseResults().isEmpty()
		|| (!isResponsibleFor() && !hasAssociatedLessonsInTeachingServices())) {
	    return false;
	}

	return true;
    }

    public StudentInquiriesTeachingResult getStudentInquiriesTeachingResult(final ExecutionDegree executionDegree,
	    final ShiftType shiftType) {
	for (StudentInquiriesTeachingResult result : getStudentInquiriesTeachingResults()) {
	    if (result.getExecutionDegree() == executionDegree && result.getShiftType() == shiftType) {
		return result;
	    }
	}
	return null;
    }

    public boolean hasAssociatedLessonsInTeachingServices() {
	for (final DegreeTeachingService degreeTeachingService : getDegreeTeachingServicesSet()) {
	    if (!degreeTeachingService.getShift().getAssociatedLessons().isEmpty()) {
		return true;
	    }
	}
	return false;
    }

    public Teacher getTeacher() {
	return getPerson().getTeacher();
    }

    public void setTeacher(Teacher teacher) {
	setPerson(teacher.getPerson());
    }

    public boolean hasTeacher() {
	return hasPerson() && getPerson().hasTeacher();
    }

    public void removeTeacher() {
	removePerson();
    }

}
