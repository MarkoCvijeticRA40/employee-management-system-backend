package com.mcm.EmployeeManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "registration_requests")
public class RegistrationRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AddressEntity address;
    private String phoneNum;
    private String title;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private RoleEntity role;
    private RegistrationRequestStatus status;
    private LocalDateTime sendTime;
}
