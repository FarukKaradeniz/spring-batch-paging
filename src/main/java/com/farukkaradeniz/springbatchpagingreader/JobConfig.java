package com.farukkaradeniz.springbatchpagingreader;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobConfig implements InitializingBean {

    private final JobLauncher jobLauncher;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SessionFactory sessionFactory;
    private final MyRepository myRepository;

    @SneakyThrows
    public void startBatch(Job job) {
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);
        jobLauncher.run(job, jobParameters);
    }

    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(5);
        executor.setThreadNamePrefix("Thread-");
        return executor;
    }

    public TaskExecutor otherTaskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
//        executor.setQueueCapacity(5);
        executor.setThreadNamePrefix("Thread-");
        executor.afterPropertiesSet();
        executor.initialize();
        return executor;
    }

    @Bean
    public Job myJob() {
        return jobBuilderFactory.get("myJob").start(myStep()).build();
    }

    public Step myStep() {
        return stepBuilderFactory.get("myStep")
                .<MyEntity, Future<MyEntity>>chunk(3)
                .reader(myItemReader())
                .listener(new MyItemReadListener(map()))
                .processor(myItemProcessor())
                .writer(myItemWriter())
                .taskExecutor(otherTaskExecutor())
                .build();
    }

    @Bean
    private ConcurrentHashMap<String, String> map() {
        return new ConcurrentHashMap<>(50);
    }

    public ItemReader<MyEntity> myItemReader() {
        return new MyReader(sessionFactory);
    }

    @SneakyThrows
    public AsyncItemProcessor<MyEntity, MyEntity> myItemProcessor() {
        var async = new AsyncItemProcessor<MyEntity, MyEntity>();
        async.setDelegate(new MyProcessor());
        async.afterPropertiesSet();
        return async;
    }

    @SneakyThrows
    public AsyncItemWriter<MyEntity> myItemWriter() {
        var async = new AsyncItemWriter<MyEntity>();
        async.setDelegate(new MyWriter(myRepository));
        async.afterPropertiesSet();
        return async;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
