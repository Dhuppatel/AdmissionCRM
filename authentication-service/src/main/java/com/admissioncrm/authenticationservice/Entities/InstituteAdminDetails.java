package com.admissioncrm.authenticationservice.Entities;

import com.admissioncrm.authenticationservice.Entities.CoreEntities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class InstituteAdminDetails {
    @Id
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private String instituteId;
}
