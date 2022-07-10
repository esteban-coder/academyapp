package com.mitocode.exam.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class EnrollmentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEnrollmentDetail;

    @ManyToOne
    @JoinColumn(name = "id_enrollment", nullable = false, foreignKey = @ForeignKey(name = "FK_ENROLLMENT_DETAIL"))
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false, foreignKey = @ForeignKey(name = "FK_ENROLLMENT_COURSE"))
    private Course course;

    @Column(length = 3, nullable = false)
    private String numClassroom;

}
