package com.mitocode.exam.controller;

import com.mitocode.exam.service.ICourseService;
import com.mitocode.exam.dto.CourseDTO;
import com.mitocode.exam.exceptions.ModelNotFoundException;
import com.mitocode.exam.model.Course;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private ICourseService service;

    @Autowired
    @Qualifier("courseMapper")
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> readAll(){
        List<CourseDTO> list = service.readAll().stream().map(course -> mapper.map(course, CourseDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> readById(@PathVariable("id") Integer id){
        Course course = service.readById(id);
        if(course == null)
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        CourseDTO dto = mapper.map(course, CourseDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> create(@Valid @RequestBody CourseDTO courseDTO){
        Course course = service.create(mapper.map(courseDTO, Course.class));
        CourseDTO dto = mapper.map(course, CourseDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CourseDTO> update(@Valid @RequestBody CourseDTO courseDTO){
        Course course = service.readById(courseDTO.getIdCourse());
        if(course == null)
            throw new ModelNotFoundException("ID NOT FOUND: " + courseDTO.getIdCourse());
        Course courseUpdated = service.update(mapper.map(courseDTO, Course.class));
        CourseDTO dto = mapper.map(courseUpdated, CourseDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){
        Course course = service.readById(id);
        if(course == null)
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
