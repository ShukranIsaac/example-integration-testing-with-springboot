package com.itspectra.studentservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;

    @BeforeEach
    void setup() {
        studentService.deleteAllStudents();
    }

    @DisplayName("Fetch a single student by id")
    @Test
    void test_get_student_returned_students() throws Exception {
        /// given
        given(studentService.getStudentById(anyLong()))
                .willReturn(Student.builder().id(1L).name("ShukranIsaac").age(25).build());

        /// when ///then
        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("ShukranIsaac"))
                .andExpect(jsonPath("age").value(25))
                .andReturn();
    }

    @DisplayName("Get student count in database")
    @Test
    void test_get_count_students_returned_count() throws Exception {
        /// given
        given(studentService.count()).willReturn(0);

        /// when ///then
        mockMvc.perform(get("/students/count"))
                .andExpect(status().isOk())
                .andReturn();
    }
}