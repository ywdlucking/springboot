package com.ywd.boot.controller;

import com.ywd.boot.exception.BaseErrorEnum;
import com.ywd.boot.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/runtime")
    public void runtimeException() {
        throw new RuntimeException("some error appear");
    }

    @GetMapping("/biz-exception")
    public void bizException() {
        throw new BizException(BaseErrorEnum.INNER_LOGIC_ERROR);
    }
}
