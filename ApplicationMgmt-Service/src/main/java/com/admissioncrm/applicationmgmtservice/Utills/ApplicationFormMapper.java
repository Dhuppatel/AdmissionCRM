package com.admissioncrm.applicationmgmtservice.Utills;

import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO.*;
import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm.*;
import org.springframework.stereotype.Component;

@Component
public class ApplicationFormMapper {

    public ApplicationForm mapToEntity(ApplicationFormSubmissionDTO dto) {
        ApplicationForm form = ApplicationForm.builder()
                .idUser(dto.getIdUser())
                .email(dto.getPersonalInfo().getEmail())
                .studentMobile(dto.getPersonalInfo().getStudentMobile())
                // Embedded Personal Info
                .personalInfo(
                        PersonalInfo.builder()
                                .firstName(dto.getPersonalInfo().getFirstName())
                                .middleName(dto.getPersonalInfo().getMiddleName())
                                .lastName(dto.getPersonalInfo().getLastName())
                                .fullName(dto.getPersonalInfo().getFullName())
                                .dob(dto.getPersonalInfo().getDob())
                                .gender(dto.getPersonalInfo().getGender())
                                .religion(dto.getPersonalInfo().getReligion())
                                .nationality(dto.getPersonalInfo().getNationality())
                                .casteCategory(dto.getPersonalInfo().getCasteCategory())
                                .domicileState(dto.getPersonalInfo().getDomicileState())
                                .differentlyAbled(dto.getPersonalInfo().getDifferentlyAbled())
                                .disability(dto.getPersonalInfo().getDisability())
                                .economicallyBackwardClass(dto.getPersonalInfo().getEconomicallyBackwardClass())
                                .build()
                )
                // Embedded Parents Info
                .parentsInfo(
                        ParentsInfo.builder()
                                .fatherSalutation(dto.getParentInfo().getFatherSalutation())
                                .fatherName(dto.getParentInfo().getFatherName())
                                .fatherMobile(dto.getParentInfo().getFatherMobile())
                                .fatherEmail(dto.getParentInfo().getFatherEmail())
                                .motherSalutation(dto.getParentInfo().getMotherSalutation())
                                .motherName(dto.getParentInfo().getMotherName())
                                .motherMobile(dto.getParentInfo().getMotherMobile())
                                .motherEmail(dto.getParentInfo().getMotherEmail())
                                .annualIncome(dto.getParentInfo().getAnnualIncome())
                                .build()
                )
                // Embedded Address Info
                .addressInfo(
                        AddressInfo.builder()
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
                                .build()
                )
                // Embedded Academic Info
                .academicInfo(
                        AcademicInfo.builder()
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
                                .build()
                )
                // Embedded Entrance Exam Info
                .entranceExamInfo(
                        EntranceExamInfo.builder()
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
                                .build()
                )
                // Embedded Course Preference Info
                .coursePreferenceInfo(
                        CoursePreferenceInfo.builder()
                                .couresePreference1(dto.getCoursePreference().getCouresePreference1())
                                .couresePreference2(dto.getCoursePreference().getCouresePreference2())
                                .couresePreference3(dto.getCoursePreference().getCouresePreference3())
                                .couresePreference4(dto.getCoursePreference().getCouresePreference4())
                                .build()
                )
                // Embedded Additional Info
                .additionalInfo(
                        AdditionalInfo.builder()
                                .educationLoanRequired(dto.getAdditionalInfo().getEducationLoanRequired())
                                .hostelAccommodationRequired(dto.getAdditionalInfo().getHostelAccommodationRequired())
                                .transportationRequired(dto.getAdditionalInfo().getTransportationRequired())
                                .heardAboutUniversityFrom(dto.getAdditionalInfo().getHeardAboutUniversityFrom())
                                .studentOfUniversity(dto.getAdditionalInfo().getStudentOfUniversity())
                                .enrollmentNumber(dto.getAdditionalInfo().getEnrollmentNumber())
                                .build()
                )
                .build();

        return form;
    }


    public ApplicationFormSubmissionDTO mapToDTO(ApplicationForm form) {
        return ApplicationFormSubmissionDTO.builder()
                .idUser(form.getIdUser())
                .personalInfo(
                        PersonalInfoDTO.builder()
                                .firstName(form.getPersonalInfo() != null ? form.getPersonalInfo().getFirstName() : null)
                                .middleName(form.getPersonalInfo() != null ? form.getPersonalInfo().getMiddleName() : null)
                                .lastName(form.getPersonalInfo() != null ? form.getPersonalInfo().getLastName() : null)
                                .fullName(form.getPersonalInfo() != null ? form.getPersonalInfo().getFullName() : null)
                                .email(form.getPersonalInfo() != null ? form.getEmail() : null)
                                .studentMobile(form.getPersonalInfo() != null ? form.getStudentMobile() : null)
                                .dob(form.getPersonalInfo() != null ? form.getPersonalInfo().getDob() : null)
                                .gender(form.getPersonalInfo() != null ? form.getPersonalInfo().getGender() : null)
                                .religion(form.getPersonalInfo() != null ? form.getPersonalInfo().getReligion() : null)
                                .nationality(form.getPersonalInfo() != null ? form.getPersonalInfo().getNationality() : null)
                                .casteCategory(form.getPersonalInfo() != null ? form.getPersonalInfo().getCasteCategory() : null)
                                .domicileState(form.getPersonalInfo() != null ? form.getPersonalInfo().getDomicileState() : null)
                                .differentlyAbled(form.getPersonalInfo() != null ? form.getPersonalInfo().getDifferentlyAbled() : null)
                                .disability(form.getPersonalInfo() != null ? form.getPersonalInfo().getDisability() : null)
                                .economicallyBackwardClass(form.getPersonalInfo() != null ? form.getPersonalInfo().getEconomicallyBackwardClass() : null)
                                .build()
                )
                .parentInfo(
                        ParentInfoDTO.builder()
                                .fatherSalutation(form.getParentsInfo() != null ? form.getParentsInfo().getFatherSalutation() : null)
                                .fatherName(form.getParentsInfo() != null ? form.getParentsInfo().getFatherName() : null)
                                .fatherMobile(form.getParentsInfo() != null ? form.getParentsInfo().getFatherMobile() : null)
                                .fatherEmail(form.getParentsInfo() != null ? form.getParentsInfo().getFatherEmail() : null)
                                .motherSalutation(form.getParentsInfo() != null ? form.getParentsInfo().getMotherSalutation() : null)
                                .motherName(form.getParentsInfo() != null ? form.getParentsInfo().getMotherName() : null)
                                .motherMobile(form.getParentsInfo() != null ? form.getParentsInfo().getMotherMobile() : null)
                                .motherEmail(form.getParentsInfo() != null ? form.getParentsInfo().getMotherEmail() : null)
                                .annualIncome(form.getParentsInfo() != null ? form.getParentsInfo().getAnnualIncome() : null)
                                .build()
                )
                .addressInfo(
                        AddressInfoDTO.builder()
                                .country(form.getAddressInfo() != null ? form.getAddressInfo().getCountry() : null)
                                .state(form.getAddressInfo() != null ? form.getAddressInfo().getState() : null)
                                .district(form.getAddressInfo() != null ? form.getAddressInfo().getDistrict() : null)
                                .cityTaluka(form.getAddressInfo() != null ? form.getAddressInfo().getCityTaluka() : null)
                                .villageTown(form.getAddressInfo() != null ? form.getAddressInfo().getVillageTown() : null)
                                .addressLine1(form.getAddressInfo() != null ? form.getAddressInfo().getAddressLine1() : null)
                                .addressLine2(form.getAddressInfo() != null ? form.getAddressInfo().getAddressLine2() : null)
                                .pincode(form.getAddressInfo() != null ? form.getAddressInfo().getPincode() : null)
                                .permanentAddressSameAsCorrespondence(form.getAddressInfo() != null ? form.getAddressInfo().getPermanentAddressSameAsCorrespondence() : null)
                                .countryPermanent(form.getAddressInfo() != null ? form.getAddressInfo().getCountryPermanent() : null)
                                .statePermanent(form.getAddressInfo() != null ? form.getAddressInfo().getStatePermanent() : null)
                                .districtPermanent(form.getAddressInfo() != null ? form.getAddressInfo().getDistrictPermanent() : null)
                                .cityTalukaPermanent(form.getAddressInfo() != null ? form.getAddressInfo().getCityTalukaPermanent() : null)
                                .villageTownPermanent(form.getAddressInfo() != null ? form.getAddressInfo().getVillageTownPermanent() : null)
                                .addressLine1Permanent(form.getAddressInfo() != null ? form.getAddressInfo().getAddressLine1Permanent() : null)
                                .addressLine2Permanent(form.getAddressInfo() != null ? form.getAddressInfo().getAddressLine2Permanent() : null)
                                .pincodePermanent(form.getAddressInfo() != null ? form.getAddressInfo().getPincodePermanent() : null)
                                .build()
                )

                .academicInfo(
                        AcademicInfoDTO.builder()
                                .udiseNo(form.getAcademicInfo() != null ? form.getAcademicInfo().getUdiseNo() : null)
                                .abcId(form.getAcademicInfo() != null ? form.getAcademicInfo().getAbcId() : null)
                                .qualification(form.getAcademicInfo() != null ? form.getAcademicInfo().getQualification() : null)
                                .twelfthPassoutCountry(form.getAcademicInfo() != null ? form.getAcademicInfo().getTwelfthPassoutCountry() : null)
                                .twelfthPassoutState(form.getAcademicInfo() != null ? form.getAcademicInfo().getTwelfthPassoutState() : null)
                                .twelfthPassoutBoard(form.getAcademicInfo() != null ? form.getAcademicInfo().getTwelfthPassoutBoard() : null)
                                .twelfthSchoolName(form.getAcademicInfo() != null ? form.getAcademicInfo().getTwelfthSchoolName() : null)
                                .twelfthResultStatus(form.getAcademicInfo() != null ? form.getAcademicInfo().getTwelfthResultStatus() : null)
                                .twelfthSeatNumber(form.getAcademicInfo() != null ? form.getAcademicInfo().getTwelfthSeatNumber() : null)
                                .twelfthStream(form.getAcademicInfo() != null ? form.getAcademicInfo().getTwelfthStream() : null)
                                .twelfthPassingDate(form.getAcademicInfo() != null ? form.getAcademicInfo().getTwelfthPassingDate() : null)
                                .twelfthModeOfStudy(form.getAcademicInfo() != null ? form.getAcademicInfo().getTwelfthModeOfStudy() : null)
                                .twelfthMarkingScheme(form.getAcademicInfo() != null ? form.getAcademicInfo().getTwelfthMarkingScheme() : null)
                                .twelfthTotalMarks(form.getAcademicInfo() != null ? form.getAcademicInfo().getTwelfthTotalMarks() : null)
                                .twelfthObtainMarks(form.getAcademicInfo() != null ? form.getAcademicInfo().getTwelfthObtainMarks() : null)
                                .twelfthObtainCgpa(form.getAcademicInfo() != null ? form.getAcademicInfo().getTwelfthObtainCgpa() : null)
                                .courseInstituteName(form.getAcademicInfo() != null ? form.getAcademicInfo().getCourseInstituteName() : null)
                                .courseSeatNumber(form.getAcademicInfo() != null ? form.getAcademicInfo().getCourseSeatNumber() : null)
                                .courseBoardOrUniversity(form.getAcademicInfo() != null ? form.getAcademicInfo().getCourseBoardOrUniversity() : null)
                                .courseDegreeOrBranch(form.getAcademicInfo() != null ? form.getAcademicInfo().getCourseDegreeOrBranch() : null)
                                .courseSpecialization(form.getAcademicInfo() != null ? form.getAcademicInfo().getCourseSpecialization() : null)
                                .coursePassingDate(form.getAcademicInfo() != null ? form.getAcademicInfo().getCoursePassingDate() : null)
                                .courseResultStatus(form.getAcademicInfo() != null ? form.getAcademicInfo().getCourseResultStatus() : null)
                                .courseMarkingScheme(form.getAcademicInfo() != null ? form.getAcademicInfo().getCourseMarkingScheme() : null)
                                .courseMaximumMarks(form.getAcademicInfo() != null ? form.getAcademicInfo().getCourseMaximumMarks() : null)
                                .courseObtainMarks(form.getAcademicInfo() != null ? form.getAcademicInfo().getCourseObtainMarks() : null)
                                .courseObtainCgpaPercentage(form.getAcademicInfo() != null ? form.getAcademicInfo().getCourseObtainCgpaPercentage() : null)
                                .build()
                )

                .entranceExamInfo(
                        EntranceExamInfoDTO.builder()
                                .appearedForEntranceExam(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getAppearedForEntranceExam() : null)
                                .entranceExam1(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceExam1() : null)
                                .entrancePassingDate1(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntrancePassingDate1() : null)
                                .entranceResultStatus1(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceResultStatus1() : null)
                                .entranceScoreRankPercentile1(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceScoreRankPercentile1() : null)
                                .entranceRollnoApplicationno1(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceRollnoApplicationno1() : null)
                                .entranceExam2(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceExam2() : null)
                                .entrancePassingDate2(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntrancePassingDate2() : null)
                                .entranceResultStatus2(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceResultStatus2() : null)
                                .entranceScoreRankPercentile2(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceScoreRankPercentile2() : null)
                                .entranceRollnoApplicationno2(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceRollnoApplicationno2() : null)
                                .entranceExam3(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceExam3() : null)
                                .entrancePassingDate3(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntrancePassingDate3() : null)
                                .entranceResultStatus3(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceResultStatus3() : null)
                                .entranceScoreRankPercentile3(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceScoreRankPercentile3() : null)
                                .entranceRollnoApplicationno3(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceRollnoApplicationno3() : null)
                                .entranceExam4(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceExam4() : null)
                                .entrancePassingDate4(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntrancePassingDate4() : null)
                                .entranceResultStatus4(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceResultStatus4() : null)
                                .entranceScoreRankPercentile4(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceScoreRankPercentile4() : null)
                                .entranceRollnoApplicationno4(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getEntranceRollnoApplicationno4() : null)
                                .registeredInAcpcAcpdc(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getRegisteredInAcpcAcpdc() : null)
                                .acpcMeritNumber(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getAcpcMeritNumber() : null)
                                .acpcMeritMarks(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getAcpcMeritMarks() : null)
                                .acpcApplicationNumber(form.getEntranceExamInfo() != null ? form.getEntranceExamInfo().getAcpcApplicationNumber() : null)
                                .build()
                )
                .coursePreference(
                        CoursePreferenceDTO.builder()
                                .couresePreference1(form.getCoursePreferenceInfo() != null ? form.getCoursePreferenceInfo().getCouresePreference1() : null)
                                .couresePreference2(form.getCoursePreferenceInfo() != null ? form.getCoursePreferenceInfo().getCouresePreference2() : null)
                                .couresePreference3(form.getCoursePreferenceInfo() != null ? form.getCoursePreferenceInfo().getCouresePreference3() : null)
                                .couresePreference4(form.getCoursePreferenceInfo() != null ? form.getCoursePreferenceInfo().getCouresePreference4() : null)
                                .build()
                )
                .additionalInfo(
                        AdditionalInfoDTO.builder()
                                .educationLoanRequired(form.getAdditionalInfo() != null ? form.getAdditionalInfo().getEducationLoanRequired() : null)
                                .hostelAccommodationRequired(form.getAdditionalInfo() != null ? form.getAdditionalInfo().getHostelAccommodationRequired() : null)
                                .transportationRequired(form.getAdditionalInfo() != null ? form.getAdditionalInfo().getTransportationRequired() : null)
                                .heardAboutUniversityFrom(form.getAdditionalInfo() != null ? form.getAdditionalInfo().getHeardAboutUniversityFrom() : null)
                                .studentOfUniversity(form.getAdditionalInfo() != null ? form.getAdditionalInfo().getStudentOfUniversity() : null)
                                .enrollmentNumber(form.getAdditionalInfo() != null ? form.getAdditionalInfo().getEnrollmentNumber() : null)
                                .build()
                )

                .build();
    }


    public void updateEntityFromDTO(ApplicationForm existingEntity, ApplicationFormSubmissionDTO dto) {

        if(existingEntity != null) {
            existingEntity.setEmail(dto.getPersonalInfo().getEmail());
            existingEntity.setStudentMobile(dto.getPersonalInfo().getStudentMobile());

        }
        // Update Personal Info
        if (dto.getPersonalInfo() != null) {
            PersonalInfo pi = existingEntity.getPersonalInfo();
            if (pi == null) pi = new PersonalInfo();
            pi.setFirstName(dto.getPersonalInfo().getFirstName());
            pi.setMiddleName(dto.getPersonalInfo().getMiddleName());
            pi.setLastName(dto.getPersonalInfo().getLastName());
            pi.setFullName(dto.getPersonalInfo().getFullName());

            pi.setDob(dto.getPersonalInfo().getDob());
            pi.setGender(dto.getPersonalInfo().getGender());
            pi.setReligion(dto.getPersonalInfo().getReligion());
            pi.setNationality(dto.getPersonalInfo().getNationality());
            pi.setCasteCategory(dto.getPersonalInfo().getCasteCategory());
            pi.setDomicileState(dto.getPersonalInfo().getDomicileState());
            pi.setDifferentlyAbled(dto.getPersonalInfo().getDifferentlyAbled());
            pi.setDisability(dto.getPersonalInfo().getDisability());
            pi.setEconomicallyBackwardClass(dto.getPersonalInfo().getEconomicallyBackwardClass());
            existingEntity.setPersonalInfo(pi);
        }
        // Update Parent Info
        if (dto.getParentInfo() != null) {
            ParentsInfo pinfo = existingEntity.getParentsInfo();
            if (pinfo == null) pinfo = new ParentsInfo();
            pinfo.setFatherSalutation(dto.getParentInfo().getFatherSalutation());
            pinfo.setFatherName(dto.getParentInfo().getFatherName());
            pinfo.setFatherMobile(dto.getParentInfo().getFatherMobile());
            pinfo.setFatherEmail(dto.getParentInfo().getFatherEmail());
            pinfo.setMotherSalutation(dto.getParentInfo().getMotherSalutation());
            pinfo.setMotherName(dto.getParentInfo().getMotherName());
            pinfo.setMotherMobile(dto.getParentInfo().getMotherMobile());
            pinfo.setMotherEmail(dto.getParentInfo().getMotherEmail());
            pinfo.setAnnualIncome(dto.getParentInfo().getAnnualIncome());
            existingEntity.setParentsInfo(pinfo);
        }
        // Update Address Info
        if (dto.getAddressInfo() != null) {
            AddressInfo ai = existingEntity.getAddressInfo();
            if (ai == null) ai = new AddressInfo();
            ai.setCountry(dto.getAddressInfo().getCountry());
            ai.setState(dto.getAddressInfo().getState());
            ai.setDistrict(dto.getAddressInfo().getDistrict());
            ai.setCityTaluka(dto.getAddressInfo().getCityTaluka());
            ai.setVillageTown(dto.getAddressInfo().getVillageTown());
            ai.setAddressLine1(dto.getAddressInfo().getAddressLine1());
            ai.setAddressLine2(dto.getAddressInfo().getAddressLine2());
            ai.setPincode(dto.getAddressInfo().getPincode());
            ai.setPermanentAddressSameAsCorrespondence(dto.getAddressInfo().getPermanentAddressSameAsCorrespondence());
            ai.setCountryPermanent(dto.getAddressInfo().getCountryPermanent());
            ai.setStatePermanent(dto.getAddressInfo().getStatePermanent());
            ai.setDistrictPermanent(dto.getAddressInfo().getDistrictPermanent());
            ai.setCityTalukaPermanent(dto.getAddressInfo().getCityTalukaPermanent());
            ai.setVillageTownPermanent(dto.getAddressInfo().getVillageTownPermanent());
            ai.setAddressLine1Permanent(dto.getAddressInfo().getAddressLine1Permanent());
            ai.setAddressLine2Permanent(dto.getAddressInfo().getAddressLine2Permanent());
            ai.setPincodePermanent(dto.getAddressInfo().getPincodePermanent());
            existingEntity.setAddressInfo(ai);
        }
        // Update Academic Info
        if (dto.getAcademicInfo() != null) {
            AcademicInfo aci = existingEntity.getAcademicInfo();
            if (aci == null) aci = new AcademicInfo();
            aci.setUdiseNo(dto.getAcademicInfo().getUdiseNo());
            aci.setAbcId(dto.getAcademicInfo().getAbcId());
            aci.setQualification(dto.getAcademicInfo().getQualification());
            aci.setTwelfthPassoutCountry(dto.getAcademicInfo().getTwelfthPassoutCountry());
            aci.setTwelfthPassoutState(dto.getAcademicInfo().getTwelfthPassoutState());
            aci.setTwelfthPassoutBoard(dto.getAcademicInfo().getTwelfthPassoutBoard());
            aci.setTwelfthSchoolName(dto.getAcademicInfo().getTwelfthSchoolName());
            aci.setTwelfthResultStatus(dto.getAcademicInfo().getTwelfthResultStatus());
            aci.setTwelfthSeatNumber(dto.getAcademicInfo().getTwelfthSeatNumber());
            aci.setTwelfthStream(dto.getAcademicInfo().getTwelfthStream());
            aci.setTwelfthPassingDate(dto.getAcademicInfo().getTwelfthPassingDate());
            aci.setTwelfthModeOfStudy(dto.getAcademicInfo().getTwelfthModeOfStudy());
            aci.setTwelfthMarkingScheme(dto.getAcademicInfo().getTwelfthMarkingScheme());
            aci.setTwelfthTotalMarks(dto.getAcademicInfo().getTwelfthTotalMarks());
            aci.setTwelfthObtainMarks(dto.getAcademicInfo().getTwelfthObtainMarks());
            aci.setTwelfthObtainCgpa(dto.getAcademicInfo().getTwelfthObtainCgpa());
            aci.setCourseInstituteName(dto.getAcademicInfo().getCourseInstituteName());
            aci.setCourseSeatNumber(dto.getAcademicInfo().getCourseSeatNumber());
            aci.setCourseBoardOrUniversity(dto.getAcademicInfo().getCourseBoardOrUniversity());
            aci.setCourseDegreeOrBranch(dto.getAcademicInfo().getCourseDegreeOrBranch());
            aci.setCourseSpecialization(dto.getAcademicInfo().getCourseSpecialization());
            aci.setCoursePassingDate(dto.getAcademicInfo().getCoursePassingDate());
            aci.setCourseResultStatus(dto.getAcademicInfo().getCourseResultStatus());
            aci.setCourseMarkingScheme(dto.getAcademicInfo().getCourseMarkingScheme());
            aci.setCourseMaximumMarks(dto.getAcademicInfo().getCourseMaximumMarks());
            aci.setCourseObtainMarks(dto.getAcademicInfo().getCourseObtainMarks());
            aci.setCourseObtainCgpaPercentage(dto.getAcademicInfo().getCourseObtainCgpaPercentage());
            existingEntity.setAcademicInfo(aci);
        }
        // Update Entrance Exam Info
        if (dto.getEntranceExamInfo() != null) {
            EntranceExamInfo eei = existingEntity.getEntranceExamInfo();
            if (eei == null) eei = new EntranceExamInfo();
            eei.setAppearedForEntranceExam(dto.getEntranceExamInfo().getAppearedForEntranceExam());
            eei.setEntranceExam1(dto.getEntranceExamInfo().getEntranceExam1());
            eei.setEntrancePassingDate1(dto.getEntranceExamInfo().getEntrancePassingDate1());
            eei.setEntranceResultStatus1(dto.getEntranceExamInfo().getEntranceResultStatus1());
            eei.setEntranceScoreRankPercentile1(dto.getEntranceExamInfo().getEntranceScoreRankPercentile1());
            eei.setEntranceRollnoApplicationno1(dto.getEntranceExamInfo().getEntranceRollnoApplicationno1());
            eei.setEntranceExam2(dto.getEntranceExamInfo().getEntranceExam2());
            eei.setEntrancePassingDate2(dto.getEntranceExamInfo().getEntrancePassingDate2());
            eei.setEntranceResultStatus2(dto.getEntranceExamInfo().getEntranceResultStatus2());
            eei.setEntranceScoreRankPercentile2(dto.getEntranceExamInfo().getEntranceScoreRankPercentile2());
            eei.setEntranceRollnoApplicationno2(dto.getEntranceExamInfo().getEntranceRollnoApplicationno2());
            eei.setEntranceExam3(dto.getEntranceExamInfo().getEntranceExam3());
            eei.setEntrancePassingDate3(dto.getEntranceExamInfo().getEntrancePassingDate3());
            eei.setEntranceResultStatus3(dto.getEntranceExamInfo().getEntranceResultStatus3());
            eei.setEntranceScoreRankPercentile3(dto.getEntranceExamInfo().getEntranceScoreRankPercentile3());
            eei.setEntranceRollnoApplicationno3(dto.getEntranceExamInfo().getEntranceRollnoApplicationno3());
            eei.setEntranceExam4(dto.getEntranceExamInfo().getEntranceExam4());
            eei.setEntrancePassingDate4(dto.getEntranceExamInfo().getEntrancePassingDate4());
            eei.setEntranceResultStatus4(dto.getEntranceExamInfo().getEntranceResultStatus4());
            eei.setEntranceScoreRankPercentile4(dto.getEntranceExamInfo().getEntranceScoreRankPercentile4());
            eei.setEntranceRollnoApplicationno4(dto.getEntranceExamInfo().getEntranceRollnoApplicationno4());
            eei.setRegisteredInAcpcAcpdc(dto.getEntranceExamInfo().getRegisteredInAcpcAcpdc());
            eei.setAcpcMeritNumber(dto.getEntranceExamInfo().getAcpcMeritNumber());
            eei.setAcpcMeritMarks(dto.getEntranceExamInfo().getAcpcMeritMarks());
            eei.setAcpcApplicationNumber(dto.getEntranceExamInfo().getAcpcApplicationNumber());
            existingEntity.setEntranceExamInfo(eei);
        }
        // Update Course Preference Info
        if (dto.getCoursePreference() != null) {
            CoursePreferenceInfo cpi = existingEntity.getCoursePreferenceInfo();
            if (cpi == null) cpi = new CoursePreferenceInfo();
            cpi.setCouresePreference1(dto.getCoursePreference().getCouresePreference1());
            cpi.setCouresePreference2(dto.getCoursePreference().getCouresePreference2());
            cpi.setCouresePreference3(dto.getCoursePreference().getCouresePreference3());
            cpi.setCouresePreference4(dto.getCoursePreference().getCouresePreference4());
            existingEntity.setCoursePreferenceInfo(cpi);
        }
        // Update Additional Info
        if (dto.getAdditionalInfo() != null) {
            AdditionalInfo adi = existingEntity.getAdditionalInfo();
            if (adi == null) adi = new AdditionalInfo();
            adi.setEducationLoanRequired(dto.getAdditionalInfo().getEducationLoanRequired());
            adi.setHostelAccommodationRequired(dto.getAdditionalInfo().getHostelAccommodationRequired());
            adi.setTransportationRequired(dto.getAdditionalInfo().getTransportationRequired());
            adi.setHeardAboutUniversityFrom(dto.getAdditionalInfo().getHeardAboutUniversityFrom());
            adi.setStudentOfUniversity(dto.getAdditionalInfo().getStudentOfUniversity());
            adi.setEnrollmentNumber(dto.getAdditionalInfo().getEnrollmentNumber());
            existingEntity.setAdditionalInfo(adi);
        }
        // Update user ID if provided
        if (dto.getIdUser() != null) {
            existingEntity.setIdUser(dto.getIdUser());
        }

        // NOTE: These fields are NOT updated to preserve data integrity:
        // - applicationFormId (Primary key)
        // - referenceId (Should remain constant)
        // - createdAt (Original creation time)
        // - deletedAt (Soft delete marker)
        // - updatedAt (Will be set by the service layer)
        // - applicationStatus (Business logic should handle this)
        // - version (Optimistic locking field)
    }


}