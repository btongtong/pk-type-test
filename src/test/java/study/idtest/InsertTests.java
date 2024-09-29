package study.idtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.idtest.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

@SpringBootTest
class InsertTests {

    @Autowired
    private AutoIncrementService autoIncrementService;
    @Autowired
    private RandomService randomService;
    @Autowired
    private TsidService tsidService;
    @Autowired
    private UlidService ulidService;
    @Autowired
    private UuidService uuidService;

    private static final int USER_COUNT = 100000;
    private static final int THREAD_POOL_SIZE = 12;

    @Test
    public void testAutoIncrementPerformance() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long autoIncrementTime = runTest(executorService, seq -> autoIncrementService.insert(seq));
        executorService.shutdown();
        System.out.println("Auto increment time: " + autoIncrementTime + "ms");
    }

    @Test
    public void testRandomPerformance() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long randomTime = runTest(executorService, seq -> randomService.insert(seq));
        executorService.shutdown();
        System.out.println("Random time: " + randomTime + "ms");
    }

    @Test
    public void testTsidPerformance() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long tsidTime = runTest(executorService, seq -> tsidService.insert(seq));
        executorService.shutdown();
        System.out.println("Tsid time: " + tsidTime + "ms");
    }

    @Test
    public void testUlidPerformance() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long ulidTime = runTest(executorService, seq -> ulidService.insert(seq));
        executorService.shutdown();
        System.out.println("Ulid time: " + ulidTime + "ms");
    }

    @Test
    public void testUuidPerformance() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long uuidTime = runTest(executorService, seq -> uuidService.insert(seq));
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
