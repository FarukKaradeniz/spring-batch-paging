package com.farukkaradeniz.springbatchpagingreader;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@AllArgsConstructor
@Slf4j
public class MyWriter implements ItemWriter<MyEntity> {
    private MyRepository myRepository;

    @Override
    public void write(List<? extends MyEntity> list) throws Exception {
        myRepository.saveAll(list);
    }
}
