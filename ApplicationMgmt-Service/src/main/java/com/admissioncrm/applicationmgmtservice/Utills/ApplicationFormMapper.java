package com.admissioncrm.applicationmgmtservice.Utills;

import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO.*;
import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;
import org.springframework.stereotype.Component;

@Component
public class ApplicationFormMapper {

    public ApplicationForm mapToEntity(ApplicationFormSubmissionDTO dto) {
        ApplicationForm form = ApplicationForm.builder()
                .idUser(dto.getIdUser())

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

                //Course preference Info
                .couresePreference1(dto.getCoursePreference().getCouresePreference1())
                .couresePreference2(dto.getCoursePreference().getCouresePreference2())
                .couresePreference3(dto.getCoursePreference().getCouresePreference3())
                .couresePreference4(dto.getCoursePreference().getCouresePreference4())

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


    public ApplicationFormSubmissionDTO mapToDTO(ApplicationForm form) {
        return ApplicationFormSubmissionDTO.builder()
                .idUser(form.getIdUser())

                .personalInfo(
                        PersonalInfoDTO.builder()
                                .firstName(form.getFirstName())
                                .middleName(form.getMiddleName())
                                .lastName(form.getLastName())
                                .fullName(form.getFullName())
                                .email(form.getEmail())
                                .studentMobile(form.getStudentMobile())
                                .dob(form.getDob())
                                .gender(form.getGender())
                                .religion(form.getReligion())
                                .nationality(form.getNationality())
                                .casteCategory(form.getCasteCategory())
                                .domicileState(form.getDomicileState())
                                .differentlyAbled(form.getDifferentlyAbled())
                                .disability(form.getDisability())
                                .economicallyBackwardClass(form.getEconomicallyBackwardClass())
                                .build()
                )

                .parentInfo(
                        ParentInfoDTO.builder()
                                .fatherSalutation(form.getFatherSalutation())
                                .fatherName(form.getFatherName())
                                .fatherMobile(form.getFatherMobile())
                                .fatherEmail(form.getFatherEmail())
                                .motherSalutation(form.getMotherSalutation())
                                .motherName(form.getMotherName())
                                .motherMobile(form.getMotherMobile())
                                .motherEmail(form.getMotherEmail())
                                .annualIncome(form.getAnnualIncome())
                                .build()
                )

                .addressInfo(
                        AddressInfoDTO.builder()
                                .country(form.getCountry())
                                .state(form.getState())
                                .district(form.getDistrict())
                                .cityTaluka(form.getCityTaluka())
                                .villageTown(form.getVillageTown())
                                .addressLine1(form.getAddressLine1())
                                .addressLine2(form.getAddressLine2())
                                .pincode(form.getPincode())
                                .permanentAddressSameAsCorrespondence(form.getPermanentAddressSameAsCorrespondence())
                                .countryPermanent(form.getCountryPermanent())
                                .statePermanent(form.getStatePermanent())
                                .districtPermanent(form.getDistrictPermanent())
                                .cityTalukaPermanent(form.getCityTalukaPermanent())
                                .villageTownPermanent(form.getVillageTownPermanent())
                                .addressLine1Permanent(form.getAddressLine1Permanent())
                                .addressLine2Permanent(form.getAddressLine2Permanent())
                                .pincodePermanent(form.getPincodePermanent())
                                .build()
                )

                .academicInfo(
                        AcademicInfoDTO.builder()
                                .udiseNo(form.getUdiseNo())
                                .abcId(form.getAbcId())
                                .qualification(form.getQualification())
                                .twelfthPassoutCountry(form.getTwelfthPassoutCountry())
                                .twelfthPassoutState(form.getTwelfthPassoutState())
                                .twelfthPassoutBoard(form.getTwelfthPassoutBoard())
                                .twelfthSchoolName(form.getTwelfthSchoolName())
                                .twelfthResultStatus(form.getTwelfthResultStatus())
                                .twelfthSeatNumber(form.getTwelfthSeatNumber())
                                .twelfthStream(form.getTwelfthStream())
                                .twelfthPassingDate(form.getTwelfthPassingDate())
                                .twelfthModeOfStudy(form.getTwelfthModeOfStudy())
                                .twelfthMarkingScheme(form.getTwelfthMarkingScheme())
                                .twelfthTotalMarks(form.getTwelfthTotalMarks())
                                .twelfthObtainMarks(form.getTwelfthObtainMarks())
                                .twelfthObtainCgpa(form.getTwelfthObtainCgpa())
                                .courseInstituteName(form.getCourseInstituteName())
                                .courseSeatNumber(form.getCourseSeatNumber())
                                .courseBoardOrUniversity(form.getCourseBoardOrUniversity())
                                .courseDegreeOrBranch(form.getCourseDegreeOrBranch())
                                .courseSpecialization(form.getCourseSpecialization())
                                .coursePassingDate(form.getCoursePassingDate())
                                .courseResultStatus(form.getCourseResultStatus())
                                .courseMarkingScheme(form.getCourseMarkingScheme())
                                .courseMaximumMarks(form.getCourseMaximumMarks())
                                .courseObtainMarks(form.getCourseObtainMarks())
                                .courseObtainCgpaPercentage(form.getCourseObtainCgpaPercentage())
                                .build()
                )

                .entranceExamInfo(
                        EntranceExamInfoDTO.builder()
                                .appearedForEntranceExam(form.getAppearedForEntranceExam())
                                .entranceExam1(form.getEntranceExam1())
                                .entrancePassingDate1(form.getEntrancePassingDate1())
                                .entranceResultStatus1(form.getEntranceResultStatus1())
                                .entranceScoreRankPercentile1(form.getEntranceScoreRankPercentile1())
                                .entranceRollnoApplicationno1(form.getEntranceRollnoApplicationno1())
                                .entranceExam2(form.getEntranceExam2())
                                .entrancePassingDate2(form.getEntrancePassingDate2())
                                .entranceResultStatus2(form.getEntranceResultStatus2())
                                .entranceScoreRankPercentile2(form.getEntranceScoreRankPercentile2())
                                .entranceRollnoApplicationno2(form.getEntranceRollnoApplicationno2())
                                .entranceExam3(form.getEntranceExam3())
                                .entrancePassingDate3(form.getEntrancePassingDate3())
                                .entranceResultStatus3(form.getEntranceResultStatus3())
                                .entranceScoreRankPercentile3(form.getEntranceScoreRankPercentile3())
                                .entranceRollnoApplicationno3(form.getEntranceRollnoApplicationno3())
                                .entranceExam4(form.getEntranceExam4())
                                .entrancePassingDate4(form.getEntrancePassingDate4())
                                .entranceResultStatus4(form.getEntranceResultStatus4())
                                .entranceScoreRankPercentile4(form.getEntranceScoreRankPercentile4())
                                .entranceRollnoApplicationno4(form.getEntranceRollnoApplicationno4())
                                .registeredInAcpcAcpdc(form.getRegisteredInAcpcAcpdc())
                                .acpcMeritNumber(form.getAcpcMeritNumber())
                                .acpcMeritMarks(form.getAcpcMeritMarks())
                                .acpcApplicationNumber(form.getAcpcApplicationNumber())
                                .build()
                )
                .coursePreference(
                        CoursePreferenceDTO.builder()
                                .couresePreference1(form.getCouresePreference1())
                                .couresePreference2(form.getCouresePreference2())
                                .couresePreference3(form.getCouresePreference3())
                                .couresePreference4(form.getCouresePreference4())
                                .build()
                )

                .additionalInfo(
                        AdditionalInfoDTO.builder()
                                .educationLoanRequired(form.getEducationLoanRequired())
                                .hostelAccommodationRequired(form.getHostelAccommodationRequired())
                                .transportationRequired(form.getTransportationRequired())
                                .heardAboutUniversityFrom(form.getHeardAboutUniversityFrom())
                                .studentOfUniversity(form.getStudentOfUniversity())
                                .enrollmentNumber(form.getEnrollmentNumber())
                                .build()
                )

                .build();
    }


    public void updateEntityFromDTO(ApplicationForm existingEntity, ApplicationFormSubmissionDTO dto) {
        // Update Personal Info
        if (dto.getPersonalInfo() != null) {
            existingEntity.setFirstName(dto.getPersonalInfo().getFirstName());
            existingEntity.setMiddleName(dto.getPersonalInfo().getMiddleName());
            existingEntity.setLastName(dto.getPersonalInfo().getLastName());
            existingEntity.setFullName(dto.getPersonalInfo().getFullName());
            existingEntity.setEmail(dto.getPersonalInfo().getEmail());
            existingEntity.setStudentMobile(dto.getPersonalInfo().getStudentMobile());
            existingEntity.setDob(dto.getPersonalInfo().getDob());
            existingEntity.setGender(dto.getPersonalInfo().getGender());
            existingEntity.setReligion(dto.getPersonalInfo().getReligion());
            existingEntity.setNationality(dto.getPersonalInfo().getNationality());
            existingEntity.setCasteCategory(dto.getPersonalInfo().getCasteCategory());
            existingEntity.setDomicileState(dto.getPersonalInfo().getDomicileState());
            existingEntity.setDifferentlyAbled(dto.getPersonalInfo().getDifferentlyAbled());
            existingEntity.setDisability(dto.getPersonalInfo().getDisability());
            existingEntity.setEconomicallyBackwardClass(dto.getPersonalInfo().getEconomicallyBackwardClass());
        }

        // Update Parent Info
        if (dto.getParentInfo() != null) {
            existingEntity.setFatherSalutation(dto.getParentInfo().getFatherSalutation());
            existingEntity.setFatherName(dto.getParentInfo().getFatherName());
            existingEntity.setFatherMobile(dto.getParentInfo().getFatherMobile());
            existingEntity.setFatherEmail(dto.getParentInfo().getFatherEmail());
            existingEntity.setMotherSalutation(dto.getParentInfo().getMotherSalutation());
            existingEntity.setMotherName(dto.getParentInfo().getMotherName());
            existingEntity.setMotherMobile(dto.getParentInfo().getMotherMobile());
            existingEntity.setMotherEmail(dto.getParentInfo().getMotherEmail());
            existingEntity.setAnnualIncome(dto.getParentInfo().getAnnualIncome());
        }

        // Update Address Info
        if (dto.getAddressInfo() != null) {
            existingEntity.setCountry(dto.getAddressInfo().getCountry());
            existingEntity.setState(dto.getAddressInfo().getState());
            existingEntity.setDistrict(dto.getAddressInfo().getDistrict());
            existingEntity.setCityTaluka(dto.getAddressInfo().getCityTaluka());
            existingEntity.setVillageTown(dto.getAddressInfo().getVillageTown());
            existingEntity.setAddressLine1(dto.getAddressInfo().getAddressLine1());
            existingEntity.setAddressLine2(dto.getAddressInfo().getAddressLine2());
            existingEntity.setPincode(dto.getAddressInfo().getPincode());
            existingEntity.setPermanentAddressSameAsCorrespondence(dto.getAddressInfo().getPermanentAddressSameAsCorrespondence());
            existingEntity.setCountryPermanent(dto.getAddressInfo().getCountryPermanent());
            existingEntity.setStatePermanent(dto.getAddressInfo().getStatePermanent());
            existingEntity.setDistrictPermanent(dto.getAddressInfo().getDistrictPermanent());
            existingEntity.setCityTalukaPermanent(dto.getAddressInfo().getCityTalukaPermanent());
            existingEntity.setVillageTownPermanent(dto.getAddressInfo().getVillageTownPermanent());
            existingEntity.setAddressLine1Permanent(dto.getAddressInfo().getAddressLine1Permanent());
            existingEntity.setAddressLine2Permanent(dto.getAddressInfo().getAddressLine2Permanent());
            existingEntity.setPincodePermanent(dto.getAddressInfo().getPincodePermanent());
        }

        // Update Academic Info
        if (dto.getAcademicInfo() != null) {
            existingEntity.setUdiseNo(dto.getAcademicInfo().getUdiseNo());
            existingEntity.setAbcId(dto.getAcademicInfo().getAbcId());
            existingEntity.setQualification(dto.getAcademicInfo().getQualification());
            existingEntity.setTwelfthPassoutCountry(dto.getAcademicInfo().getTwelfthPassoutCountry());
            existingEntity.setTwelfthPassoutState(dto.getAcademicInfo().getTwelfthPassoutState());
            existingEntity.setTwelfthPassoutBoard(dto.getAcademicInfo().getTwelfthPassoutBoard());
            existingEntity.setTwelfthSchoolName(dto.getAcademicInfo().getTwelfthSchoolName());
            existingEntity.setTwelfthResultStatus(dto.getAcademicInfo().getTwelfthResultStatus());
            existingEntity.setTwelfthSeatNumber(dto.getAcademicInfo().getTwelfthSeatNumber());
            existingEntity.setTwelfthStream(dto.getAcademicInfo().getTwelfthStream());
            existingEntity.setTwelfthPassingDate(dto.getAcademicInfo().getTwelfthPassingDate());
            existingEntity.setTwelfthModeOfStudy(dto.getAcademicInfo().getTwelfthModeOfStudy());
            existingEntity.setTwelfthMarkingScheme(dto.getAcademicInfo().getTwelfthMarkingScheme());
            existingEntity.setTwelfthTotalMarks(dto.getAcademicInfo().getTwelfthTotalMarks());
            existingEntity.setTwelfthObtainMarks(dto.getAcademicInfo().getTwelfthObtainMarks());
            existingEntity.setTwelfthObtainCgpa(dto.getAcademicInfo().getTwelfthObtainCgpa());
            existingEntity.setCourseInstituteName(dto.getAcademicInfo().getCourseInstituteName());
            existingEntity.setCourseSeatNumber(dto.getAcademicInfo().getCourseSeatNumber());
            existingEntity.setCourseBoardOrUniversity(dto.getAcademicInfo().getCourseBoardOrUniversity());
            existingEntity.setCourseDegreeOrBranch(dto.getAcademicInfo().getCourseDegreeOrBranch());
            existingEntity.setCourseSpecialization(dto.getAcademicInfo().getCourseSpecialization());
            existingEntity.setCoursePassingDate(dto.getAcademicInfo().getCoursePassingDate());
            existingEntity.setCourseResultStatus(dto.getAcademicInfo().getCourseResultStatus());
            existingEntity.setCourseMarkingScheme(dto.getAcademicInfo().getCourseMarkingScheme());
            existingEntity.setCourseMaximumMarks(dto.getAcademicInfo().getCourseMaximumMarks());
            existingEntity.setCourseObtainMarks(dto.getAcademicInfo().getCourseObtainMarks());
            existingEntity.setCourseObtainCgpaPercentage(dto.getAcademicInfo().getCourseObtainCgpaPercentage());
        }

        // Update Entrance Exam Info
        if (dto.getEntranceExamInfo() != null) {
            existingEntity.setAppearedForEntranceExam(dto.getEntranceExamInfo().getAppearedForEntranceExam());
            existingEntity.setEntranceExam1(dto.getEntranceExamInfo().getEntranceExam1());
            existingEntity.setEntrancePassingDate1(dto.getEntranceExamInfo().getEntrancePassingDate1());
            existingEntity.setEntranceResultStatus1(dto.getEntranceExamInfo().getEntranceResultStatus1());
            existingEntity.setEntranceScoreRankPercentile1(dto.getEntranceExamInfo().getEntranceScoreRankPercentile1());
            existingEntity.setEntranceRollnoApplicationno1(dto.getEntranceExamInfo().getEntranceRollnoApplicationno1());
            existingEntity.setEntranceExam2(dto.getEntranceExamInfo().getEntranceExam2());
            existingEntity.setEntrancePassingDate2(dto.getEntranceExamInfo().getEntrancePassingDate2());
            existingEntity.setEntranceResultStatus2(dto.getEntranceExamInfo().getEntranceResultStatus2());
            existingEntity.setEntranceScoreRankPercentile2(dto.getEntranceExamInfo().getEntranceScoreRankPercentile2());
            existingEntity.setEntranceRollnoApplicationno2(dto.getEntranceExamInfo().getEntranceRollnoApplicationno2());
            existingEntity.setEntranceExam3(dto.getEntranceExamInfo().getEntranceExam3());
            existingEntity.setEntrancePassingDate3(dto.getEntranceExamInfo().getEntrancePassingDate3());
            existingEntity.setEntranceResultStatus3(dto.getEntranceExamInfo().getEntranceResultStatus3());
            existingEntity.setEntranceScoreRankPercentile3(dto.getEntranceExamInfo().getEntranceScoreRankPercentile3());
            existingEntity.setEntranceRollnoApplicationno3(dto.getEntranceExamInfo().getEntranceRollnoApplicationno3());
            existingEntity.setEntranceExam4(dto.getEntranceExamInfo().getEntranceExam4());
            existingEntity.setEntrancePassingDate4(dto.getEntranceExamInfo().getEntrancePassingDate4());
            existingEntity.setEntranceResultStatus4(dto.getEntranceExamInfo().getEntranceResultStatus4());
            existingEntity.setEntranceScoreRankPercentile4(dto.getEntranceExamInfo().getEntranceScoreRankPercentile4());
            existingEntity.setEntranceRollnoApplicationno4(dto.getEntranceExamInfo().getEntranceRollnoApplicationno4());
            existingEntity.setRegisteredInAcpcAcpdc(dto.getEntranceExamInfo().getRegisteredInAcpcAcpdc());
            existingEntity.setAcpcMeritNumber(dto.getEntranceExamInfo().getAcpcMeritNumber());
            existingEntity.setAcpcMeritMarks(dto.getEntranceExamInfo().getAcpcMeritMarks());
            existingEntity.setAcpcApplicationNumber(dto.getEntranceExamInfo().getAcpcApplicationNumber());
        }

        //update Course preference Info
        if(dto.getCoursePreference()!=null)
        {
            existingEntity.setCouresePreference1(dto.getCoursePreference().getCouresePreference1());
            existingEntity.setCouresePreference2(dto.getCoursePreference().getCouresePreference2());
            existingEntity.setCouresePreference3(dto.getCoursePreference().getCouresePreference3());
            existingEntity.setCouresePreference4(dto.getCoursePreference().getCouresePreference4());

        }

        // Update Additional Info
        if (dto.getAdditionalInfo() != null) {
            existingEntity.setEducationLoanRequired(dto.getAdditionalInfo().getEducationLoanRequired());
            existingEntity.setHostelAccommodationRequired(dto.getAdditionalInfo().getHostelAccommodationRequired());
            existingEntity.setTransportationRequired(dto.getAdditionalInfo().getTransportationRequired());
            existingEntity.setHeardAboutUniversityFrom(dto.getAdditionalInfo().getHeardAboutUniversityFrom());
            existingEntity.setStudentOfUniversity(dto.getAdditionalInfo().getStudentOfUniversity());
            existingEntity.setEnrollmentNumber(dto.getAdditionalInfo().getEnrollmentNumber());
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