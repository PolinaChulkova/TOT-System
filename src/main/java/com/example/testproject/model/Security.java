package com.example.testproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "securities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "sec_id")
    private String secId;
    @Column(name = "reg_number")
    private String regNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "emitent_title")
    private String emitentTitle;
}
