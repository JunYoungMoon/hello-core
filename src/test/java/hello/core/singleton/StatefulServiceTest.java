package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService bean1 = ac.getBean(StatefulService.class);
        StatefulService bean2 = ac.getBean(StatefulService.class);

        bean1.order("userA", 10000);
        bean2.order("userB", 20000);

        int price = bean1.getPrice();

        System.out.println("price = " + price);

        assertThat(bean1.getPrice()).isEqualTo(20000);
    }

    @Test
    void statefulServiceSingletonWithThreadPool() throws InterruptedException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService statefulService = ac.getBean(StatefulService.class);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Task for thread A
        Runnable taskA = () -> statefulService.order("userA", 10000);
        // Task for thread B
        Runnable taskB = () -> statefulService.order("userB", 20000);

        // 쓰레드풀에 taskA를 제출한다. 쓰레드풀이 이 작업을 가져가서 쓰레드에서 실행
        executorService.execute(taskA);
        executorService.execute(taskB);

        // 쓰레드 풀을 종료
        executorService.shutdown();

        // 쓰레드는 비동기적으로 동작하므로, 쓰레드의 작업이 완료될 시간을 정확히 알 수 없다.
        // 따라서 awaitTermination 메서드를 사용해서 쓰레드가 모두 완료될 때까지 기다린다.
        // 이때 2초까지 기다리고 2초 동안 대기한 후에도 쓰레드풀이 완료되지 않으면 false를 반환하고, 쓰레드풀이 완료되면 true를 반환한다.
        executorService.awaitTermination(2, TimeUnit.SECONDS);

        // 싱글톤이라 인스턴스값이 계속 바뀌는걸 볼수 있음
        int price = statefulService.getPrice();
        System.out.println("price = " + price);
    }


    static class TestConfig{

        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }

}