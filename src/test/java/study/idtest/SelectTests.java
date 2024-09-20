package study.idtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.idtest.entity.*;
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

    @BeforeEach
    void setup() {
        autoIncrementIds = new ArrayList<>();
        randomIds = new ArrayList<>();
        tsidIds = new ArrayList<>();
        ulidIds = new ArrayList<>();
        uuidIds = new ArrayList<>();

        for (int i = 0; i < SELECT_COUNT; i++) {
            autoIncrementIds.add(autoIncrementService.insert(i));
            randomIds.add(randomService.insert(i));
            tsidIds.add(tsidService.insert(i));
            ulidIds.add(ulidService.insert(i));
            uuidIds.add(uuidService.insert(i));
        }
    }

    @Test
    public void testIdStrategySelectPerformance() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        long autoIncrementSelectTime = runTest(executorService, this::selectAutoIncrement, autoIncrementIds);
        long randomSelectTime = runTest(executorService, this::selectRandom, randomIds);
        long tsidSelectTime = runTest(executorService, this::selectTsid, tsidIds);
        long ulidSelectTime = runTest(executorService, this::selectUlid, ulidIds);
        long uuidSelectTime = runTest(executorService, this::selectUuid, uuidIds);

        executorService.shutdown();

        System.out.println("Select Performance:");
        System.out.println("Auto increment time: " + autoIncrementSelectTime + "ms");
        System.out.println("Random time: " + randomSelectTime + "ms");
        System.out.println("Tsid time: " + tsidSelectTime + "ms");
        System.out.println("Ulid time: " + ulidSelectTime + "ms");
        System.out.println("Uuid time: " + uuidSelectTime + "ms");
    }

    private <T> long runTest(ExecutorService executorService, Function<T, ?> selectMethod, List<T> ids) throws Exception {
        long startTime = System.currentTimeMillis();

        List<Future<?>> futures = new ArrayList<>();
        for(T id : ids) {
            futures.add(executorService.submit(() -> selectMethod.apply(id)));
        }

        for(Future<?> future : futures) {
            future.get();
        }

        return System.currentTimeMillis() - startTime;
    }

    private AutoIncrementEntity selectAutoIncrement(Long id) {
        return autoIncrementService.select(id);
    }

    private RandomEntity selectRandom(Long id) {
        return randomService.select(id);
    }

    private TsidEntity selectTsid(Long id) {
        return tsidService.select(id);
    }

    private UlidEntity selectUlid(String id) {
        return ulidService.select(id);
    }

    private UuidEntity selectUuid(String id) {
        return uuidService.select(id);
    }
}
