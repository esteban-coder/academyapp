package com.mitocode.exam.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollmentDetailDTO {

    private Integer idEnrollmentDetail;

    @JsonIgnore
    //@NotNull
    private EnrollmentDTO enrollment;

    @NotNull
    private CourseDTO course;

    @NotNull
    @Size(max = 3)
    private String numClassroom;

}
