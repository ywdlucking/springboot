package com.ywd.boot.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * <h1>定时任务生成</h1>
 * */
@Slf4j
@Component
public class BootTask {

    @Async("mvcTaskExecutor")
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void boot() throws Exception {

        log.info("start to mvcTaskExecutor");
        Thread.sleep(10000);
    }
}
