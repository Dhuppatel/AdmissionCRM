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


    public void updateEntityFromDTO(ApplicationForm existing, ApplicationFormSubmissionDTO dto) {

        if (existing == null || dto == null) {
            return;
        }

        // Update root-level simple fields
        if (dto.getPersonalInfo() != null) {
            existing.setEmail(dto.getPersonalInfo().getEmail());
            existing.setStudentMobile(dto.getPersonalInfo().getStudentMobile());
        }


//     PERSONAL INFO

        if (dto.getPersonalInfo() != null) {
            PersonalInfo pi = existing.getPersonalInfo();
            if (pi == null) {
                pi = new PersonalInfo();
            }

            PersonalInfoDTO source = dto.getPersonalInfo();

            pi.setFirstName(source.getFirstName());
            pi.setMiddleName(source.getMiddleName());
            pi.setLastName(source.getLastName());
            pi.setFullName(source.getFullName());
            pi.setDob(source.getDob());
            pi.setGender(source.getGender());
            pi.setReligion(source.getReligion());
            pi.setNationality(source.getNationality());
            pi.setCasteCategory(source.getCasteCategory());
            pi.setDomicileState(source.getDomicileState());
            pi.setDifferentlyAbled(source.getDifferentlyAbled());
            pi.setDisability(source.getDisability());
            pi.setEconomicallyBackwardClass(source.getEconomicallyBackwardClass());

            existing.setPersonalInfo(pi);
        }


//     PARENTS INFO

        if (dto.getParentInfo() != null) {
            ParentsInfo p = existing.getParentsInfo();
            if (p == null) {
                p = new ParentsInfo();
            }

            ParentInfoDTO source = dto.getParentInfo();

            p.setFatherSalutation(source.getFatherSalutation());
            p.setFatherName(source.getFatherName());
            p.setFatherMobile(source.getFatherMobile());
            p.setFatherEmail(source.getFatherEmail());
            p.setMotherSalutation(source.getMotherSalutation());
            p.setMotherName(source.getMotherName());
            p.setMotherMobile(source.getMotherMobile());
            p.setMotherEmail(source.getMotherEmail());
            p.setAnnualIncome(source.getAnnualIncome());

            existing.setParentsInfo(p);
        }


//     ADDRESS INFO

        if (dto.getAddressInfo() != null) {
            AddressInfo ai = existing.getAddressInfo();
            if (ai == null) {
                ai = new AddressInfo();
            }

            AddressInfoDTO source = dto.getAddressInfo();

            ai.setCountry(source.getCountry());
            ai.setState(source.getState());
            ai.setDistrict(source.getDistrict());
            ai.setCityTaluka(source.getCityTaluka());
            ai.setVillageTown(source.getVillageTown());
            ai.setAddressLine1(source.getAddressLine1());
            ai.setAddressLine2(source.getAddressLine2());
            ai.setPincode(source.getPincode());
            ai.setPermanentAddressSameAsCorrespondence(source.getPermanentAddressSameAsCorrespondence());

            ai.setCountryPermanent(source.getCountryPermanent());
            ai.setStatePermanent(source.getStatePermanent());
            ai.setDistrictPermanent(source.getDistrictPermanent());
            ai.setCityTalukaPermanent(source.getCityTalukaPermanent());
            ai.setVillageTownPermanent(source.getVillageTownPermanent());
            ai.setAddressLine1Permanent(source.getAddressLine1Permanent());
            ai.setAddressLine2Permanent(source.getAddressLine2Permanent());
            ai.setPincodePermanent(source.getPincodePermanent());

            existing.setAddressInfo(ai);
        }


//     ACADEMIC INFO

        if (dto.getAcademicInfo() != null) {
            AcademicInfo ac = existing.getAcademicInfo();
            if (ac == null) {
                ac = new AcademicInfo();
            }

            AcademicInfoDTO source = dto.getAcademicInfo();

            ac.setUdiseNo(source.getUdiseNo());
            ac.setAbcId(source.getAbcId());
            ac.setQualification(source.getQualification());

            ac.setTwelfthPassoutCountry(source.getTwelfthPassoutCountry());
            ac.setTwelfthPassoutState(source.getTwelfthPassoutState());
            ac.setTwelfthPassoutBoard(source.getTwelfthPassoutBoard());
            ac.setTwelfthSchoolName(source.getTwelfthSchoolName());
            ac.setTwelfthResultStatus(source.getTwelfthResultStatus());
            ac.setTwelfthSeatNumber(source.getTwelfthSeatNumber());
            ac.setTwelfthStream(source.getTwelfthStream());
            ac.setTwelfthPassingDate(source.getTwelfthPassingDate());
            ac.setTwelfthModeOfStudy(source.getTwelfthModeOfStudy());
            ac.setTwelfthMarkingScheme(source.getTwelfthMarkingScheme());
            ac.setTwelfthTotalMarks(source.getTwelfthTotalMarks());
            ac.setTwelfthObtainMarks(source.getTwelfthObtainMarks());
            ac.setTwelfthObtainCgpa(source.getTwelfthObtainCgpa());

            ac.setCourseInstituteName(source.getCourseInstituteName());
            ac.setCourseSeatNumber(source.getCourseSeatNumber());
            ac.setCourseBoardOrUniversity(source.getCourseBoardOrUniversity());
            ac.setCourseDegreeOrBranch(source.getCourseDegreeOrBranch());
            ac.setCourseSpecialization(source.getCourseSpecialization());
            ac.setCoursePassingDate(source.getCoursePassingDate());
            ac.setCourseResultStatus(source.getCourseResultStatus());
            ac.setCourseMarkingScheme(source.getCourseMarkingScheme());
            ac.setCourseMaximumMarks(source.getCourseMaximumMarks());
            ac.setCourseObtainMarks(source.getCourseObtainMarks());
            ac.setCourseObtainCgpaPercentage(source.getCourseObtainCgpaPercentage());

            existing.setAcademicInfo(ac);
        }

//     ENTRANCE EXAM INFO

        if (dto.getEntranceExamInfo() != null) {
            EntranceExamInfo ee = existing.getEntranceExamInfo();
            if (ee == null) {
                ee = new EntranceExamInfo();
            }

            EntranceExamInfoDTO source = dto.getEntranceExamInfo();

            ee.setAppearedForEntranceExam(source.getAppearedForEntranceExam());
            ee.setEntranceExam1(source.getEntranceExam1());
            ee.setEntrancePassingDate1(source.getEntrancePassingDate1());
            ee.setEntranceResultStatus1(source.getEntranceResultStatus1());
            ee.setEntranceScoreRankPercentile1(source.getEntranceScoreRankPercentile1());
            ee.setEntranceRollnoApplicationno1(source.getEntranceRollnoApplicationno1());

            ee.setEntranceExam2(source.getEntranceExam2());
            ee.setEntrancePassingDate2(source.getEntrancePassingDate2());
            ee.setEntranceResultStatus2(source.getEntranceResultStatus2());
            ee.setEntranceScoreRankPercentile2(source.getEntranceScoreRankPercentile2());
            ee.setEntranceRollnoApplicationno2(source.getEntranceRollnoApplicationno2());

            ee.setEntranceExam3(source.getEntranceExam3());
            ee.setEntrancePassingDate3(source.getEntrancePassingDate3());
            ee.setEntranceResultStatus3(source.getEntranceResultStatus3());
            ee.setEntranceScoreRankPercentile3(source.getEntranceScoreRankPercentile3());
            ee.setEntranceRollnoApplicationno3(source.getEntranceRollnoApplicationno3());

            ee.setEntranceExam4(source.getEntranceExam4());
            ee.setEntrancePassingDate4(source.getEntrancePassingDate4());
            ee.setEntranceResultStatus4(source.getEntranceResultStatus4());
            ee.setEntranceScoreRankPercentile4(source.getEntranceScoreRankPercentile4());
            ee.setEntranceRollnoApplicationno4(source.getEntranceRollnoApplicationno4());

            ee.setRegisteredInAcpcAcpdc(source.getRegisteredInAcpcAcpdc());
            ee.setAcpcMeritNumber(source.getAcpcMeritNumber());
            ee.setAcpcMeritMarks(source.getAcpcMeritMarks());
            ee.setAcpcApplicationNumber(source.getAcpcApplicationNumber());

            existing.setEntranceExamInfo(ee);
        }

    /*
     =========================
     COURSE PREFERENCE
     =========================
     */
        if (dto.getCoursePreference() != null) {
            CoursePreferenceInfo cp = existing.getCoursePreferenceInfo();
            if (cp == null) {
                cp = new CoursePreferenceInfo();
            }

            CoursePreferenceDTO source = dto.getCoursePreference();

            cp.setCouresePreference1(source.getCouresePreference1());
            cp.setCouresePreference2(source.getCouresePreference2());
            cp.setCouresePreference3(source.getCouresePreference3());
            cp.setCouresePreference4(source.getCouresePreference4());

            existing.setCoursePreferenceInfo(cp);
        }


//     ADDITIONAL INFO

        if (dto.getAdditionalInfo() != null) {
            AdditionalInfo ai = existing.getAdditionalInfo();
            if (ai == null) {
                ai = new AdditionalInfo();
            }

            AdditionalInfoDTO source = dto.getAdditionalInfo();

            ai.setEducationLoanRequired(source.getEducationLoanRequired());
            ai.setHostelAccommodationRequired(source.getHostelAccommodationRequired());
            ai.setTransportationRequired(source.getTransportationRequired());
            ai.setHeardAboutUniversityFrom(source.getHeardAboutUniversityFrom());
            ai.setStudentOfUniversity(source.getStudentOfUniversity());
            ai.setEnrollmentNumber(source.getEnrollmentNumber());

            existing.setAdditionalInfo(ai);
        }

        // Update user if provided
        if (dto.getIdUser() != null) {
            existing.setIdUser(dto.getIdUser());
        }

        // DO NOT TOUCH:
        // - applicationId
        // - referenceId
        // - createdAt
        // - version
        // - status (handled in service layer)
    }



}