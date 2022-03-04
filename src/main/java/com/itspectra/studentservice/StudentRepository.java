package com.itspectra.studentservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student getStudentByName(String name);
    @Query("select avg (age) from Student where active = true ")
    Double getAvgAgeForActiveStudents();
}
