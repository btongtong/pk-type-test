package study.idtest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import study.idtest.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

@SpringBootTest
class CreateEntityTest {

    private static final int USER_COUNT = 100000;
    private static final int THREAD_POOL_SIZE = 12;

    @Test
    public void testAutoIncrementPerformance() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long autoIncrementTime = runTest(executorService, AutoIncrementEntity::new);
        executorService.shutdown();
        System.out.println("Auto increment time: " + autoIncrementTime + "ms");
    }

    @Test
    public void testRandomPerformance() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long randomTime = runTest(executorService, RandomEntity::new);
        executorService.shutdown();
        System.out.println("Random time: " + randomTime + "ms");
    }

    @Test
    public void testTsidPerformance() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long tsidTime = runTest(executorService, TsidEntity::new);
        executorService.shutdown();
        System.out.println("Tsid time: " + tsidTime + "ms");
    }

    @Test
    public void testUlidPerformance() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long ulidTime = runTest(executorService, UlidEntity::new);
        executorService.shutdown();
        System.out.println("Ulid time: " + ulidTime + "ms");
    }

    @Test
    public void testUuidPerformance() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long uuidTime = runTest(executorService, UuidEntity::new);
        executorService.shutdown();
        System.out.println("Uuid time: " + uuidTime + "ms");
    }

    private long runTest(ExecutorService executorService, Consumer<Integer> insertMethod) throws Exception {
        long startTime = System.currentTimeMillis();

        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < USER_COUNT; i++) {
            final int seq = i;
            futures.add(executorService.submit(() -> insertMethod.accept(seq)));
        }

        for (Future<?> future : futures) {
            future.get();
        }

        return System.currentTimeMillis() - startTime;
    }
}
