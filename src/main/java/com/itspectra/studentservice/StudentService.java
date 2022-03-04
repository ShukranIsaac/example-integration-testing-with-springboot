package com.itspectra.studentservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(StudentNotFoundException::new);
    }

    public boolean deleteAllStudents() {
        studentRepository.deleteAll();
        return studentRepository.findAll().isEmpty();
    }

    public int count() {
        return studentRepository.findAll().size();
    }
}
