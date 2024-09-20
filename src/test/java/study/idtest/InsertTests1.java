package study.idtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.idtest.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SpringBootTest
public class InsertTests1 {
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

    private static final int USER_COUNT = 1000000;

    @Test
    public void testIdStrategyPerformance() throws Exception {
        long autoIncrementTime = runTest(this::insertAutoIncrement);
        long randomTime = runTest(this::insertRandom);
        long tsidTime = runTest(this::insertTsid);
        long ulidTime = runTest(this::insertUlid);
        long uuidTime = runTest(this::insertUuid);

        System.out.println("Auto increment time: " + autoIncrementTime + "ms");
        System.out.println("Random time: " + randomTime + "ms");
        System.out.println("Tsid time: " + tsidTime + "ms");
        System.out.println("Ulid time: " + ulidTime + "ms");
        System.out.println("Uuid time: " + uuidTime + "ms");
    }

    private long runTest(Consumer<Integer> insertMethod) throws Exception {
        long startTime = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < USER_COUNT; i++) {
            final int seq = i;
            Thread thread = new Thread(() -> insertMethod.accept(seq));
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
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
