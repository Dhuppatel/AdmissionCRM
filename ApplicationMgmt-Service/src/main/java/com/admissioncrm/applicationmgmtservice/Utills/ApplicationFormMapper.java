package com.admissioncrm.applicationmgmtservice.Utills;

import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO.ApplicationFormSubmissionDTO;
import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;
import org.springframework.stereotype.Component;

@Component
public class ApplicationFormMapper {

    public ApplicationForm mapToEntity(ApplicationFormSubmissionDTO dto) {
        ApplicationForm form = ApplicationForm.builder()
                .idUser(dto.getIdUser())
                .instituteCourseId(dto.getCoursePreference().getInstituteCourseId())

                // Personal Info
                .firstName(dto.getPersonalInfo().getFirstName())
                .middleName(dto.getPersonalInfo().getMiddleName())
                .lastName(dto.getPersonalInfo().getLastName())
                .fullName(dto.getPersonalInfo().getFullName())
                .email(dto.getPersonalInfo().getEmail())
                .studentMobile(dto.getPersonalInfo().getStudentMobile())
                .dob(dto.getPersonalInfo().getDob())
                .gender(dto.getPersonalInfo().getGender())
                .religion(dto.getPersonalInfo().getReligion())
                .nationality(dto.getPersonalInfo().getNationality())
                .casteCategory(dto.getPersonalInfo().getCasteCategory())
                .domicileState(dto.getPersonalInfo().getDomicileState())
                .differentlyAbled(dto.getPersonalInfo().getDifferentlyAbled())
                .disability(dto.getPersonalInfo().getDisability())
                .economicallyBackwardClass(dto.getPersonalInfo().getEconomicallyBackwardClass())

                // Parent Info
                .fatherSalutation(dto.getParentInfo().getFatherSalutation())
                .fatherName(dto.getParentInfo().getFatherName())
                .fatherMobile(dto.getParentInfo().getFatherMobile())
                .fatherEmail(dto.getParentInfo().getFatherEmail())
                .motherSalutation(dto.getParentInfo().getMotherSalutation())
                .motherName(dto.getParentInfo().getMotherName())
                .motherMobile(dto.getParentInfo().getMotherMobile())
                .motherEmail(dto.getParentInfo().getMotherEmail())
                .annualIncome(dto.getParentInfo().getAnnualIncome())

                // Address Info
                .country(dto.getAddressInfo().getCountry())
                .state(dto.getAddressInfo().getState())
                .district(dto.getAddressInfo().getDistrict())
                .cityTaluka(dto.getAddressInfo().getCityTaluka())
                .villageTown(dto.getAddressInfo().getVillageTown())
                .addressLine1(dto.getAddressInfo().getAddressLine1())
                .addressLine2(dto.getAddressInfo().getAddressLine2())
                .pincode(dto.getAddressInfo().getPincode())
                .permanentAddressSameAsCorrespondence(dto.getAddressInfo().getPermanentAddressSameAsCorrespondence())
                .countryPermanent(dto.getAddressInfo().getCountryPermanent())
                .statePermanent(dto.getAddressInfo().getStatePermanent())
                .districtPermanent(dto.getAddressInfo().getDistrictPermanent())
                .cityTalukaPermanent(dto.getAddressInfo().getCityTalukaPermanent())
                .villageTownPermanent(dto.getAddressInfo().getVillageTownPermanent())
                .addressLine1Permanent(dto.getAddressInfo().getAddressLine1Permanent())
                .addressLine2Permanent(dto.getAddressInfo().getAddressLine2Permanent())
                .pincodePermanent(dto.getAddressInfo().getPincodePermanent())

                // Academic Info
                .udiseNo(dto.getAcademicInfo().getUdiseNo())
                .abcId(dto.getAcademicInfo().getAbcId())
                .qualification(dto.getAcademicInfo().getQualification())
                .twelfthPassoutCountry(dto.getAcademicInfo().getTwelfthPassoutCountry())
                .twelfthPassoutState(dto.getAcademicInfo().getTwelfthPassoutState())
                .twelfthPassoutBoard(dto.getAcademicInfo().getTwelfthPassoutBoard())
                .twelfthSchoolName(dto.getAcademicInfo().getTwelfthSchoolName())
                .twelfthResultStatus(dto.getAcademicInfo().getTwelfthResultStatus())
                .twelfthSeatNumber(dto.getAcademicInfo().getTwelfthSeatNumber())
                .twelfthStream(dto.getAcademicInfo().getTwelfthStream())
                .twelfthPassingDate(dto.getAcademicInfo().getTwelfthPassingDate())
                .twelfthModeOfStudy(dto.getAcademicInfo().getTwelfthModeOfStudy())
                .twelfthMarkingScheme(dto.getAcademicInfo().getTwelfthMarkingScheme())
                .twelfthTotalMarks(dto.getAcademicInfo().getTwelfthTotalMarks())
                .twelfthObtainMarks(dto.getAcademicInfo().getTwelfthObtainMarks())
                .twelfthObtainCgpa(dto.getAcademicInfo().getTwelfthObtainCgpa())
                .courseInstituteName(dto.getAcademicInfo().getCourseInstituteName())
                .courseSeatNumber(dto.getAcademicInfo().getCourseSeatNumber())
                .courseBoardOrUniversity(dto.getAcademicInfo().getCourseBoardOrUniversity())
                .courseDegreeOrBranch(dto.getAcademicInfo().getCourseDegreeOrBranch())
                .courseSpecialization(dto.getAcademicInfo().getCourseSpecialization())
                .coursePassingDate(dto.getAcademicInfo().getCoursePassingDate())
                .courseResultStatus(dto.getAcademicInfo().getCourseResultStatus())
                .courseMarkingScheme(dto.getAcademicInfo().getCourseMarkingScheme())
                .courseMaximumMarks(dto.getAcademicInfo().getCourseMaximumMarks())
                .courseObtainMarks(dto.getAcademicInfo().getCourseObtainMarks())
                .courseObtainCgpaPercentage(dto.getAcademicInfo().getCourseObtainCgpaPercentage())

                // Entrance Exam Info
                .appearedForEntranceExam(dto.getEntranceExamInfo().getAppearedForEntranceExam())
                .entranceExam1(dto.getEntranceExamInfo().getEntranceExam1())
                .entrancePassingDate1(dto.getEntranceExamInfo().getEntrancePassingDate1())
                .entranceResultStatus1(dto.getEntranceExamInfo().getEntranceResultStatus1())
                .entranceScoreRankPercentile1(dto.getEntranceExamInfo().getEntranceScoreRankPercentile1())
                .entranceRollnoApplicationno1(dto.getEntranceExamInfo().getEntranceRollnoApplicationno1())
                .entranceExam2(dto.getEntranceExamInfo().getEntranceExam2())
                .entrancePassingDate2(dto.getEntranceExamInfo().getEntrancePassingDate2())
                .entranceResultStatus2(dto.getEntranceExamInfo().getEntranceResultStatus2())
                .entranceScoreRankPercentile2(dto.getEntranceExamInfo().getEntranceScoreRankPercentile2())
                .entranceRollnoApplicationno2(dto.getEntranceExamInfo().getEntranceRollnoApplicationno2())
                .entranceExam3(dto.getEntranceExamInfo().getEntranceExam3())
                .entrancePassingDate3(dto.getEntranceExamInfo().getEntrancePassingDate3())
                .entranceResultStatus3(dto.getEntranceExamInfo().getEntranceResultStatus3())
                .entranceScoreRankPercentile3(dto.getEntranceExamInfo().getEntranceScoreRankPercentile3())
                .entranceRollnoApplicationno3(dto.getEntranceExamInfo().getEntranceRollnoApplicationno3())
                .entranceExam4(dto.getEntranceExamInfo().getEntranceExam4())
                .entrancePassingDate4(dto.getEntranceExamInfo().getEntrancePassingDate4())
                .entranceResultStatus4(dto.getEntranceExamInfo().getEntranceResultStatus4())
                .entranceScoreRankPercentile4(dto.getEntranceExamInfo().getEntranceScoreRankPercentile4())
                .entranceRollnoApplicationno4(dto.getEntranceExamInfo().getEntranceRollnoApplicationno4())
                .registeredInAcpcAcpdc(dto.getEntranceExamInfo().getRegisteredInAcpcAcpdc())
                .acpcMeritNumber(dto.getEntranceExamInfo().getAcpcMeritNumber())
                .acpcMeritMarks(dto.getEntranceExamInfo().getAcpcMeritMarks())
                .acpcApplicationNumber(dto.getEntranceExamInfo().getAcpcApplicationNumber())

                // Additional Info
                .educationLoanRequired(dto.getAdditionalInfo().getEducationLoanRequired())
                .hostelAccommodationRequired(dto.getAdditionalInfo().getHostelAccommodationRequired())
                .transportationRequired(dto.getAdditionalInfo().getTransportationRequired())
                .heardAboutUniversityFrom(dto.getAdditionalInfo().getHeardAboutUniversityFrom())
                .studentOfUniversity(dto.getAdditionalInfo().getStudentOfUniversity())
                .enrollmentNumber(dto.getAdditionalInfo().getEnrollmentNumber())

                .build();

        return form;
    }
}