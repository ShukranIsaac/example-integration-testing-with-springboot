package com.itspectra.studentservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class StudentServiceTest {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;

    @DisplayName("Return stored student by Id from the service layer")
    @Test
    void get_student_by_id_from_service_layer() {
        //given
        Student newStudent = studentRepository.save(Student.builder().id(null).name("ShukranIsaac").build());
        //when
        Student student = studentService.getStudentById(newStudent.getId());
        //then
        then(student.getName()).isEqualTo("ShukranIsaac");
        then(student.getId()).isNotNull();
    }

    @DisplayName("Throw exception for missing student by id")
    @Test
    void test_get_student_by_missing_id_is_exception_thrown() {
        ///given
        Long id = 1234L;
        //when
        Throwable throwable = catchThrowable(() -> studentService.getStudentById(id));
        ///then
        then(throwable).isInstanceOf(StudentNotFoundException.class);
    }

    @DisplayName("Return count of students in database")
    @Test
    void test_count_of_students_in_database() {
        Student isaac = Student.builder().name("Isaac").active(true).age(20).build();
        Student john = Student.builder().name("John").active(false).age(35).build();
        Student mary = Student.builder().name("Mary").active(true).age(30).build();

        Arrays.asList(isaac, john, mary).forEach(studentRepository::save);

        then(studentService.deleteAllStudents()).isTrue();
    }

    @DisplayName("Delete all records in the table return true")
    @Test
    void test_delete_students_returned_true() {
        //given
        Student isaac = Student.builder().name("Isaac").active(true).age(20).build();
        Student john = Student.builder().name("John").active(false).age(35).build();
        Student mary = Student.builder().name("Mary").active(true).age(30).build();
        Arrays.asList(isaac, john, mary).forEach(studentRepository::save);
        // then
        then(studentService.count()).isEqualTo(3);
        // when
        boolean result = studentService.deleteAllStudents();
        // then
        then(result).isTrue();
        then(studentService.count()).isEqualTo(0);
    }
}