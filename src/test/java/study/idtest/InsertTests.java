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
    private static final int THREAD_POOL_SIZE = 1000;

    @Test
    public void testIdStrategyPerformance() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        long autoIncrementTime = runTest(executorService, this::insertAutoIncrement);
        long randomTime = runTest(executorService, this::insertRandom);
        long tsidTime = runTest(executorService, this::insertTsid);
        long ulidTime = runTest(executorService, this::insertUlid);
        long uuidTime = runTest(executorService, this::insertUuid);

        executorService.shutdown();

        System.out.println("Auto increment time: " + autoIncrementTime + "ms");
        System.out.println("Random time: " + randomTime + "ms");
        System.out.println("Tsid time: " + tsidTime + "ms");
        System.out.println("Ulid time: " + ulidTime + "ms");
        System.out.println("Uuid time: " + uuidTime + "ms");
    }

    private long runTest(ExecutorService executorService, Consumer<Integer> insertMethod) throws Exception {
        long startTime = System.currentTimeMillis();

        List<Future<?>> futures = new ArrayList<>();
        for(int i = 0; i < USER_COUNT; i++) {
            final int seq = i;
            futures.add(executorService.submit(() -> insertMethod.accept(seq)));
        }

        for(Future<?> future : futures) {
            future.get();
        }

        return System.currentTimeMillis() - startTime;
    }

    private void insertAutoIncrement(int seq) {
        autoIncrementService.insert(seq);
    }

    private void insertRandom(int seq) {
        randomService.insert(seq);
    }

    private void insertTsid(int seq) {
        tsidService.insert(seq);
    }

    private void insertUlid(int seq) {
        ulidService.insert(seq);
    }

    private void insertUuid(int seq) {
        uuidService.insert(seq);
    }

}
