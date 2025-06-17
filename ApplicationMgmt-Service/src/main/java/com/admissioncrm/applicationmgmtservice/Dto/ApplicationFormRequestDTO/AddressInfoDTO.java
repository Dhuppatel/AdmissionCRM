package com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressInfoDTO {

    // Correspondence Address
    @NotBlank(message = "Country is required")
    private String country;

    private String state;
    private String district;
    private String cityTaluka;
    private String villageTown;

    @NotBlank(message = "Address line 1 is required")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be 6 digits")
    private String pincode;

    @NotNull(message = "Permanent address same as correspondence flag is required")
    private Boolean permanentAddressSameAsCorrespondence;

    // Permanent Address
    @NotBlank(message = "Permanent country is required")
    private String countryPermanent;

    private String statePermanent;
    private String districtPermanent;
    private String cityTalukaPermanent;
    private String villageTownPermanent;

    @NotBlank(message = "Permanent address line 1 is required")
    private String addressLine1Permanent;

    private String addressLine2Permanent;

    @NotBlank(message = "Permanent pincode is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "Permanent pincode must be 6 digits")
    private String pincodePermanent;
}