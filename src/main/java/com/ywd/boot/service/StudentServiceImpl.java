package com.ywd.boot.service;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywd.boot.vo.Student;
import com.ywd.boot.vo.RedBlueCount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentServiceImpl {

    private final static String IMOOCER_LIST_KEY = "SpringBoot:Imoocer:List";

    private final ObjectMapper mapper;
    private final StringRedisTemplate redisTemplate;

    public StudentServiceImpl(@Qualifier("objectMapper") ObjectMapper mapper, StringRedisTemplate redisTemplate) {
        this.mapper = mapper;
        this.redisTemplate = redisTemplate;
    }

    public Long createStudent(Student student) throws JsonProcessingException {

        Long size = redisTemplate.opsForList().size(IMOOCER_LIST_KEY);
        if (null == size) {
            size = 0L;
        }

        student.setId(size + 1);
        student.setProfile("fangao.jpg");
        Long result = redisTemplate.opsForList().leftPush(IMOOCER_LIST_KEY, mapper.writeValueAsString(student));
        log.info("left push student to list: [{}]", result);

        return student.getId();
    }

    public List<Student> studentInfo() throws IOException {

        List<String> studentsCache = redisTemplate.opsForList().range(IMOOCER_LIST_KEY, 0, -1);
        if (CollectionUtil.isEmpty(studentsCache)) {
            return Collections.emptyList();
        }

        List<Student> students = new ArrayList<>(studentsCache.size());
        for (int i = 0; i != studentsCache.size(); ++i) {
            students.add(mapper.readValue(studentsCache.get(i), Student.class));
        }

        return students;
    }


}
