package com.mitocode.exam.service.impl;

import com.mitocode.exam.service.IStudentService;
import com.mitocode.exam.model.Student;
import com.mitocode.exam.repo.IGenericRepo;
import com.mitocode.exam.repo.IStudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl extends CRUDImpl<Student, Integer> implements IStudentService {

    @Autowired
    private IStudentRepo repo;

    @Override
    protected IGenericRepo<Student, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<Student> findAllOrderByAgeDesc() {
        List<Student> studentList = this.repo.findAll();

        Comparator<Student> inverse = (o1, o2) -> Double.valueOf(o2.getAge()).intValue() - Double.valueOf(o1.getAge()).intValue();
        return studentList.stream().sorted(inverse).collect(Collectors.toList());
    }


}
