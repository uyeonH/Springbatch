package study.springbatch.springbatch;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.BeanProperty;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MyJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job myJob() {
        return jobBuilderFactory.get("myJob")
                .start(myStep1())
                .next(myStep2())
                .build();
    }

    @Bean
    public Step myStep1() {
        return stepBuilderFactory.get("myStep")
                .tasklet(new Tasklet() {
                    // return 값이 RepeatStatus.FINISHED(또는 null)일 경우 tasklet이 한 번 실행되고 종료
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info("# MyStep1");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step myStep2() {
        return stepBuilderFactory.get("myStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info("# MyStep2");
                        return null;
                    }
                }).build();
    }


}

