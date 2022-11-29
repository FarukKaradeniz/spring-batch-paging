package com.farukkaradeniz.springbatchpagingreader;

import org.hibernate.SessionFactory;
import org.springframework.batch.item.database.HibernatePagingItemReader;

public class MyReader extends HibernatePagingItemReader<MyEntity> {
    public MyReader(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
        setPageSize(5);
        setName("MyReader");
        setQueryString(query());
        setSaveState(false);
    }

    private String query() {
        return "SELECT new com.farukkaradeniz.springbatchpagingreader.MyEntity(id, name, date) from MyEntity order by date desc";
    }
}
