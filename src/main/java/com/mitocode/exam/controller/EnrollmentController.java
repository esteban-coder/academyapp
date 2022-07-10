package com.mitocode.exam.controller;

import com.mitocode.exam.service.IEnrollmentService;
import com.mitocode.exam.dto.EnrollmentDTO;
import com.mitocode.exam.exceptions.ModelNotFoundException;
import com.mitocode.exam.model.Enrollment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private IEnrollmentService service;

    @Autowired
    @Qualifier("enrollmentMapper")
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> readAll(){
        List<EnrollmentDTO> list = service.readAll().stream().map(enrollment -> mapper.map(enrollment, EnrollmentDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> readById(@PathVariable("id") Integer id){
        Enrollment enrollment = service.readById(id);
        if(enrollment == null)
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        EnrollmentDTO dto = mapper.map(enrollment, EnrollmentDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> create(@Valid @RequestBody EnrollmentDTO enrollmentDTO){
        Enrollment enrollment = mapper.map(enrollmentDTO, Enrollment.class);
        Enrollment enrollmentCreated = service.saveTransactional(enrollment, enrollment.getDetails());
        EnrollmentDTO dto = mapper.map(enrollmentCreated, EnrollmentDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<EnrollmentDTO> update(@Valid @RequestBody EnrollmentDTO enrollmentDTO){
        Enrollment enrollment = service.readById(enrollmentDTO.getIdEnrollment());
        if(enrollment == null)
            throw new ModelNotFoundException("ID NOT FOUND: " + enrollmentDTO.getIdEnrollment());
        Enrollment enrollmentUpdated = service.update(mapper.map(enrollmentDTO, Enrollment.class));
        EnrollmentDTO dto = mapper.map(enrollmentUpdated, EnrollmentDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){
        Enrollment enrollment = service.readById(id);
        if(enrollment == null)
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/grouping")
    public ResponseEntity<Map<String, List<String>>> readGrouping(){
        Map<String, List<String>> mapCourseStudents = service.findStudentsGroupByCourse();

        return new ResponseEntity<>(mapCourseStudents, HttpStatus.OK);
    }



    //Otras pruebas intersante

    /*
    @PostMapping
    public ResponseEntity<EnrollmentDTO> create(@Valid @RequestBody EnrollmentDTO enrollmentDTO){
        enrollmentDTO.getDetails().forEach(detail -> detail.setEnrollment(enrollmentDTO));
        Enrollment enrollment = service.create(mapper.map(enrollmentDTO, Enrollment.class));
        EnrollmentDTO dto = mapper.map(enrollment, EnrollmentDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> create(@Valid @RequestBody EnrollmentDTO enrollmentDTO){
        Enrollment enrollment = mapper.map(enrollmentDTO, Enrollment.class);
        enrollment.getDetails().forEach(detail -> detail.setEnrollment(enrollment));
        Enrollment enrollmentCreated = service.create(enrollment);
        EnrollmentDTO dto = mapper.map(enrollmentCreated, EnrollmentDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/detail")
    public ResponseEntity<EnrollmentDetailDTO> showDetail(@Valid @RequestBody EnrollmentDetailDTO enrollmentDetailDTO){
        return new ResponseEntity<>(enrollmentDetailDTO, HttpStatus.OK);
    }

    @GetMapping("/grouping")
    public ResponseEntity<Map<String, List<String>>> readGrouping(){
        Map<String, List<EnrollmentDetail>> mapCourseEnrollmentDetails = service.findStudentsGroupByCourse();

        Map<String, List<EnrollmentDetailDTO>> mapCourseEnrollmentDetailsDTO = mapCourseEnrollmentDetails
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> mapper.map((List<EnrollmentDetail>)e.getValue(), new TypeToken<List<EnrollmentDetailDTO>>() {}.getType())));

        return new ResponseEntity<>(mapCourseEnrollmentDetailsDTO, HttpStatus.OK);
}
    */

}
