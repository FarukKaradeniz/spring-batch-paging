package com.farukkaradeniz.springbatchpagingreader;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@AllArgsConstructor
public class MyItemReadListener implements ItemReadListener<MyEntity> {
    private ConcurrentHashMap<String, String> map;
    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(MyEntity myEntity) {
        if (map.containsKey(myEntity.getId())) {
            log.error("Has key: {}", myEntity.getId()); // TODO Item is being read more than once. Why?
        } else {
            map.put(myEntity.getId(), myEntity.getName());
            log.warn("Item Read: {}", myEntity);
        }
    }

    @Override
    public void onReadError(Exception e) {

    }
}
