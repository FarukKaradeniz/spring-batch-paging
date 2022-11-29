package com.farukkaradeniz.springbatchpagingreader;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
public class MyProcessor implements ItemProcessor<MyEntity, MyEntity> {
    @Override
    public MyEntity process(MyEntity myEntity) throws Exception {
        Thread.sleep((1 + new Random().nextInt(50)) * 20);
        if (Integer.parseInt(myEntity.getName().substring(7)) % 5 == 0) {
            log.info("Processing: {}", myEntity);
            Thread.sleep(10 * 1000L);
        }
        myEntity.setName(myEntity.getName() + "-processed");
        myEntity.setDate(LocalDateTime.now());
        log.info("Processed: {}", myEntity);
        return myEntity;
    }
}
