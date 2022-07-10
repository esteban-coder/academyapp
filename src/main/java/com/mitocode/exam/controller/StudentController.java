package com.mitocode.exam.controller;

import com.mitocode.exam.service.IStudentService;
import com.mitocode.exam.dto.StudentDTO;
import com.mitocode.exam.exceptions.ModelNotFoundException;
import com.mitocode.exam.model.Student;
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
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private IStudentService service;

    @Autowired
    @Qualifier("studentMapper")
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<StudentDTO>> readAll(){
        List<StudentDTO> list = service.readAll().stream().map(student -> mapper.map(student, StudentDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> readById(@PathVariable("id") Integer id){
        Student student = service.readById(id);
        if(student == null)
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        StudentDTO dto = mapper.map(student, StudentDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> create(@Valid @RequestBody StudentDTO studentDTO){
        Student student = service.create(mapper.map(studentDTO, Student.class));
        StudentDTO dto = mapper.map(student, StudentDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<StudentDTO> update(@Valid @RequestBody StudentDTO studentDTO){
        Student student = service.readById(studentDTO.getIdStudent());
        if(student == null)
            throw new ModelNotFoundException("ID NOT FOUND: " + studentDTO.getIdStudent());
        Student studentUpdated = service.update(mapper.map(studentDTO, Student.class));
        StudentDTO dto = mapper.map(studentUpdated, StudentDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){
        Student student = service.readById(id);
        if(student == null)
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/order/age/desc")
    public ResponseEntity<List<Student>> findAllOrderByAgeDesc(){
        List<Student> list = service.findAllOrderByAgeDesc();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
