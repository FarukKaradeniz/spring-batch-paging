package com.farukkaradeniz.springbatchpagingreader;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;

@Slf4j
public class MyProcessor implements ItemProcessor<MyEntity, MyEntity> {
    @Override
    public MyEntity process(MyEntity myEntity) throws Exception {
        log.info("In Processor: {}", myEntity);
        for (int i = 0; i < 1_000_000_000_000L; i++) {
            myEntity.setName(myEntity.getName());
        }
        myEntity.setName(myEntity.getName() + "-processed");
        myEntity.setDate(LocalDateTime.now());
        return myEntity;
    }
}
