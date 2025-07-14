package com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// Address Information Embeddable
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressInfo {

    // Correspondence Address
    @Column(name = "country", nullable = false, columnDefinition = "TEXT")
    private String country;

    @Column(name = "state", columnDefinition = "TEXT")
    private String state;

    @Column(name = "district", columnDefinition = "TEXT")
    private String district;

    @Column(name = "city_taluka", columnDefinition = "TEXT")
    private String cityTaluka;

    @Column(name = "village_town", columnDefinition = "TEXT")
    private String villageTown;

    @Column(name = "address_line1", nullable = false, columnDefinition = "TEXT")
    private String addressLine1;

    @Column(name = "address_line2", columnDefinition = "TEXT")
    private String addressLine2;

    @Column(name = "pincode", nullable = false)
    private String pincode;

    @Column(name = "permanent_address_same_as_correspondence", nullable = false)
    private Boolean permanentAddressSameAsCorrespondence;

    // Permanent Address
    @Column(name = "country_permanent", nullable = false, columnDefinition = "TEXT")
    private String countryPermanent;

    @Column(name = "state_permanent", columnDefinition = "TEXT")
    private String statePermanent;

    @Column(name = "district_permanent", columnDefinition = "TEXT")
    private String districtPermanent;

    @Column(name = "city_taluka_permanent", columnDefinition = "TEXT")
    private String cityTalukaPermanent;

    @Column(name = "village_town_permanent", columnDefinition = "TEXT")
    private String villageTownPermanent;

    @Column(name = "address_line1_permanent", nullable = false, columnDefinition = "TEXT")
    private String addressLine1Permanent;

    @Column(name = "address_line2_permanent", columnDefinition = "TEXT")
    private String addressLine2Permanent;

    @Column(name = "pincode_permanent", nullable = false)
    private String pincodePermanent;
}
