package com.itspectra.studentservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void test_delete_students() {
        studentRepository.deleteAll();
        then(studentRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    void test_get_student_by_name() {
        /// given
        Student shukranIsaac = Student.builder().id(null).name("ShukranIsaac").age(25).build();
        Student storedStudent = testEntityManager.persistAndFlush(shukranIsaac);
        // when
        Student student = studentRepository.getStudentByName("ShukranIsaac");
        // then
        then(student.getId()).isNotNull();
        then(student.getName()).isEqualTo(shukranIsaac.getName());
    }

    @Test
    void text_get_avg_age_for_active_students() {
        //given
        Student isaac = Student.builder().name("Isaac").active(true).age(20).build();
        Student john = Student.builder().name("John").active(false).age(35).build();
        Student mary = Student.builder().name("Mary").active(true).age(30).build();
        Arrays.asList(isaac, john, mary).forEach(testEntityManager::persistAndFlush);
        //when
        Double avgAge = studentRepository.getAvgAgeForActiveStudents();
        //then
        then(avgAge).isEqualTo(25);
    }

    @Test
    void test_get_all_students() {
        //given
        Student isaac = Student.builder().name("Isaac").active(true).age(20).build();
        Student john = Student.builder().name("John").active(false).age(35).build();
        Student mary = Student.builder().name("Mary").active(true).age(30).build();
        Arrays.asList(isaac, john, mary).forEach(testEntityManager::persistAndFlush);
        //when
        int count = studentRepository.findAll().size();
        //then
        then(count).isEqualTo(3);
    }
}