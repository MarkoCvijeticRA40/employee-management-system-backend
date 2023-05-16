package com.mcm.EmployeeManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "skills")
public class SkillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer level;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
