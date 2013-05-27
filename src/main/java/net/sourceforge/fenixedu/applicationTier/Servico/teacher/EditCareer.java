package net.sourceforge.fenixedu.applicationTier.Servico.teacher;


import net.sourceforge.fenixedu.applicationTier.Filtro.teacher.EditCareerTeacherAuthorizationFilter;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.NotAuthorizedException;
import net.sourceforge.fenixedu.dataTransferObject.teacher.InfoCareer;
import net.sourceforge.fenixedu.dataTransferObject.teacher.InfoProfessionalCareer;
import net.sourceforge.fenixedu.dataTransferObject.teacher.InfoTeachingCareer;
import net.sourceforge.fenixedu.domain.RootDomainObject;
import net.sourceforge.fenixedu.domain.Teacher;
import net.sourceforge.fenixedu.domain.personnelSection.contracts.ProfessionalCategory;
import net.sourceforge.fenixedu.domain.teacher.ProfessionalCareer;
import net.sourceforge.fenixedu.domain.teacher.TeachingCareer;
import pt.ist.fenixWebFramework.services.Service;

public class EditCareer {

    protected void run(Integer careerId, InfoCareer infoCareer) {
        if (infoCareer instanceof InfoTeachingCareer) {
            editCareer(careerId, (InfoTeachingCareer) infoCareer);
        } else if (infoCareer instanceof InfoProfessionalCareer) {
            editCareer(careerId, (InfoProfessionalCareer) infoCareer);
        }
    }

    private void editCareer(Integer careerId, InfoTeachingCareer infoTeachingCareer) {
        TeachingCareer teachingCareer = (TeachingCareer) RootDomainObject.getInstance().readCareerByOID(careerId);
        ProfessionalCategory category =
                RootDomainObject.getInstance().readProfessionalCategoryByOID(infoTeachingCareer.getInfoCategory().getIdInternal());

        // If it doesn't exist in the database, a new one has to be created
        if (teachingCareer == null) {
            Teacher teacher = RootDomainObject.getInstance().readTeacherByOID(infoTeachingCareer.getInfoTeacher().getIdInternal());
            teachingCareer = new TeachingCareer(teacher, category.getName(), infoTeachingCareer);
        } else {
            teachingCareer.edit(infoTeachingCareer, category.getName());
        }
    }

    private void editCareer(Integer careerId, InfoProfessionalCareer infoProfessionalCareer) {
        ProfessionalCareer professionalCareer = (ProfessionalCareer) RootDomainObject.getInstance().readCareerByOID(careerId);

        // If it doesn't exist in the database, a new one has to be created
        if (professionalCareer == null) {
            Teacher teacher = RootDomainObject.getInstance().readTeacherByOID(infoProfessionalCareer.getInfoTeacher().getIdInternal());
            professionalCareer = new ProfessionalCareer(teacher, infoProfessionalCareer);
        } else {
            professionalCareer.edit(infoProfessionalCareer);
        }
    }

    // Service Invokers migrated from Berserk

    private static final EditCareer serviceInstance = new EditCareer();

    @Service
    public static void runEditCareer(Integer careerId, InfoCareer infoCareer) throws NotAuthorizedException {
        EditCareerTeacherAuthorizationFilter.instance.execute(careerId, infoCareer);
        serviceInstance.run(careerId, infoCareer);
    }

}