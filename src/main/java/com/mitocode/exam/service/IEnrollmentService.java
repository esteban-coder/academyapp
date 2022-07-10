package com.mitocode.exam.service;

import com.mitocode.exam.model.Enrollment;
import com.mitocode.exam.model.EnrollmentDetail;

import java.util.List;
import java.util.Map;

public interface IEnrollmentService extends ICRUD<Enrollment, Integer> {

    Enrollment saveTransactional(Enrollment enrollment, List<EnrollmentDetail> details);

    Map<String, List<String>> findStudentsGroupByCourse();
}
