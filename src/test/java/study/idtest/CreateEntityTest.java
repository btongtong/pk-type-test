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

    private static final int USER_COUNT = 1000000;
    private static final int THREAD_POOL_SIZE = 12;

    @Test
    public void testIdStrategyPerformance() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        long autoIncrementTime = runTest(executorService, this::createAutoIncrement);
        long randomTime = runTest(executorService, this::createRandom);
        long tsidTime = runTest(executorService, this::createTsid);
        long ulidTime = runTest(executorService, this::createUlid);
        long uuidTime = runTest(executorService, this::createUuid);

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

    private void createAutoIncrement(int seq) {
        AutoIncrementEntity entity = new AutoIncrementEntity(seq);
    }

    private void createRandom(int seq) {
        RandomEntity entity = new RandomEntity(seq);
    }

    private void createTsid(int seq) {
        TsidEntity entity = new TsidEntity(seq);
    }

    private void createUlid(int seq) {
        UlidEntity entity = new UlidEntity(seq);
    }

    private void createUuid(int seq) {
        UuidEntity entity = new UuidEntity(seq);
    }

}
