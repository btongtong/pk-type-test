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
import java.util.function.Function;

@SpringBootTest
public class SelectTests {
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

    private static final int SELECT_COUNT = 100000;
    private static final int THREAD_POOL_SIZE = 12;

    private List<Long> autoIncrementIds;
    private List<Long> randomIds;
    private List<Long> tsidIds;
    private List<String> ulidIds;
    private List<String> uuidIds;

    @Test
    public void testAutoIncrementSelectPerformance() throws Exception {
        autoIncrementIds = autoIncrementService.selectRandomIds(SELECT_COUNT);
        System.out.println("Auto Increment IDs: " + autoIncrementIds.size());

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long autoIncrementSelectTime = runTest(executorService, id -> autoIncrementService.select(id), autoIncrementIds);
        executorService.shutdown();

        System.out.println("Auto increment select time: " + autoIncrementSelectTime + "ms");
    }

    @Test
    public void testRandomSelectPerformance() throws Exception {
        randomIds = randomService.selectRandomIds(SELECT_COUNT);
        System.out.println("Random IDs: " + randomIds.size());

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long randomSelectTime = runTest(executorService, id -> randomService.select(id), randomIds);
        executorService.shutdown();

        System.out.println("Random select time: " + randomSelectTime + "ms");
    }

    @Test
    public void testTsidSelectPerformance() throws Exception {
        tsidIds = tsidService.selectRandomIds(SELECT_COUNT);
        System.out.println("TSID IDs: " + tsidIds.size());

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long tsidSelectTime = runTest(executorService, id -> tsidService.select(id), tsidIds);
        executorService.shutdown();

        System.out.println("TSID select time: " + tsidSelectTime + "ms");
    }

    @Test
    public void testUlidSelectPerformance() throws Exception {
        ulidIds = ulidService.selectRandomIds(SELECT_COUNT);
        System.out.println("ULID IDs: " + ulidIds.size());

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long ulidSelectTime = runTest(executorService, id -> ulidService.select(id), ulidIds);
        executorService.shutdown();

        System.out.println("ULID select time: " + ulidSelectTime + "ms");
    }

    @Test
    public void testUuidSelectPerformance() throws Exception {
        uuidIds = uuidService.selectRandomIds(SELECT_COUNT);
        System.out.println("UUID IDs: " + uuidIds.size());

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long uuidSelectTime = runTest(executorService, id -> uuidService.select(id), uuidIds);
        executorService.shutdown();

        System.out.println("UUID select time: " + uuidSelectTime + "ms");
    }

    private <T> long runTest(ExecutorService executorService, Function<T, ?> selectMethod, List<T> ids) throws Exception {
        long startTime = System.currentTimeMillis();

        List<Future<?>> futures = new ArrayList<>();
        for (T id : ids) {
            futures.add(executorService.submit(() -> selectMethod.apply(id)));
        }

        for (Future<?> future : futures) {
            future.get();
        }

        return System.currentTimeMillis() - startTime;
    }
}
