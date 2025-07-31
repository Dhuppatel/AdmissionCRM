package com.admission_crm.lead_management.Entity.LeadManagement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LeadSource {
    WEBSITE, REFERRAL, SOCIAL_MEDIA, ADVERTISEMENT, PHONE_CALL, WALK_IN, EMAIL_CAMPAIGN, OTHER;

    @JsonValue
    public String getValue() {
        return name();
    }

    @JsonCreator
    public static LeadSource fromValue(String value) {
        for (LeadSource source : LeadSource.values()) {
            if (source.name().equalsIgnoreCase(value)) {
                return source;
            }
        }
        throw new IllegalArgumentException("Invalid LeadSource value: " + value);
    }
}
