package com.ywd.boot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ywd.boot.annotation.OperateLog;
import com.ywd.boot.service.StudentServiceImpl;
import com.ywd.boot.vo.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "学生接口")
@OperateLog
public class StudentController {

    private final StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }


    @PostMapping("/create")
    @ApiOperation(value = "创建")
    public Long createStudent(@RequestBody Student student) throws JsonProcessingException {
        return studentService.createStudent(student);
    }


    @GetMapping("/info")
    @ApiOperation(value = "查询")
    public List<Student> studentInfo() throws IOException {
        return studentService.studentInfo();
    }

}
