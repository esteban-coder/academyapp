package com.mitocode.exam.service.impl;

import com.mitocode.exam.service.ICourseService;
import com.mitocode.exam.model.Course;
import com.mitocode.exam.repo.ICourseRepo;
import com.mitocode.exam.repo.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends CRUDImpl<Course, Integer> implements ICourseService{

    @Autowired
    private ICourseRepo repo;

    @Override
    protected IGenericRepo<Course, Integer> getRepo() {
        return repo;
    }
}
