package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class StatelessServiceTest {
    @Test
    void statelessServiceSingleton(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatelessService bean1 = ac.getBean(StatelessService.class);
        StatelessService bean2 = ac.getBean(StatelessService.class);

        int price1 = bean1.order("userA", 10000);
        int price2 = bean2.order("userB", 20000);

        System.out.println("userA price = " + price1);

        assertThat(price1).isEqualTo(10000);
    }

    @Test
    void statelessServiceTestWithThreadPool() throws InterruptedException, ExecutionException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatelessService statelessService = ac.getBean(StatelessService.class);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Future objects to hold results of tasks
        Future<Integer> futurePrice1;
        Future<Integer> futurePrice2;

        // Task for thread A
        Callable<Integer> taskA = () -> statelessService.order("userA", 10000);
        // Task for thread B
        Callable<Integer> taskB = () -> statelessService.order("userB", 20000);

        // Execute tasks
        futurePrice1 = executorService.submit(taskA);
        futurePrice2 = executorService.submit(taskB);

        // Shut down executor after all tasks are done
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);

        // Assert the results
        assertThat(futurePrice1.get()).isEqualTo(10000);
        assertThat(futurePrice2.get()).isEqualTo(20000);
    }

    static class TestConfig{
        @Bean
        public StatelessService statelessService(){
            return new StatelessService();
        }
    }

}