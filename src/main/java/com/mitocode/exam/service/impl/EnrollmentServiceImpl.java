package com.mitocode.exam.service.impl;

import com.mitocode.exam.service.IEnrollmentService;
import com.mitocode.exam.model.Enrollment;
import com.mitocode.exam.model.EnrollmentDetail;
import com.mitocode.exam.repo.IEnrollmentRepo;
import com.mitocode.exam.repo.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class EnrollmentServiceImpl extends CRUDImpl<Enrollment, Integer> implements IEnrollmentService {

    @Autowired
    private IEnrollmentRepo repo;

    @Override
    protected IGenericRepo<Enrollment, Integer> getRepo() {
        return repo;
    }

    @Transactional
    @Override
    public Enrollment saveTransactional(Enrollment enrollment, List<EnrollmentDetail> details) {
        enrollment.getDetails().forEach(detail -> detail.setEnrollment(enrollment));
        enrollment.setDetails(details);

        return repo.save(enrollment);
    }

    @Override
    public Map<String, List<String>> findStudentsGroupByCourse(){

        Map<String, List<EnrollmentDetail>> mapCourseEnrollmentDetails = repo.findAll().stream()
                .map(enrollment -> enrollment.getDetails())
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(d -> d.getCourse().getName()));

        Map<String, List<String>> mapCourseStudents = mapCourseEnrollmentDetails.entrySet()
                .stream()
                .collect(toMap(
                        e -> e.getKey(),
                        e -> e.getValue().stream().map(
                                ed -> ed.getEnrollment().getStudent().getFirstName() + " " + ed.getEnrollment().getStudent().getLastName()).collect(toList()))
                );

        return mapCourseStudents;
    }
}
