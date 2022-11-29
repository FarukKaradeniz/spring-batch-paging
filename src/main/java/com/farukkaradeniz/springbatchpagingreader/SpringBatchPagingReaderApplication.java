package com.farukkaradeniz.springbatchpagingreader;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
@EnableBatchProcessing
public class SpringBatchPagingReaderApplication implements CommandLineRunner {

    private final JobConfig jobConfig;
    private final MyRepository myRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchPagingReaderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        myRepository.saveAll(myEntityList());

        jobConfig.startBatch(jobConfig.myJob());
    }

    @SneakyThrows
    private List<MyEntity> myEntityList() {
        var list = new ArrayList<MyEntity>();
        for (int i = 0; i < 50; i++) {
            list.add(new MyEntity(UUID.randomUUID().toString(), "Entity-" + i, LocalDateTime.now()));
        }
        return list;
    }
}
