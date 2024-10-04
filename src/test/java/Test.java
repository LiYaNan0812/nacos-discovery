import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(110);

        for (int j=0; j<10; j++) {
            executorService.execute(() -> {
                List<Map<String, Object>> list = new ArrayList<>();

                for (int i=0; i<100000; i++) {
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("a", i);
                    dataMap.put("b", i+1);
                    list.add(dataMap);
                }

                List<Object> strings = Collections.synchronizedList(new ArrayList<>());
                try {
                    extracted(executorService, list, strings, "a");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                List<Object> collect = strings.stream().distinct().collect(Collectors.toList());
                System.out.println("size:"+collect.size());
            });
        }



    }

    private static void extracted(ExecutorService executorService, List<Map<String, Object>> list, List<Object> strings, String str) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for (Map<String, Object> map : list){

            try {
               Future<Object> future =  executorService.submit(() -> {
                    if (strings.contains(map.get("a"))) {
                        System.out.println("Wrong:"+ map.get("a"));
                        countDownLatch.countDown();
                        return map.get("a");
                    } else {
                        strings.add(map.get("a"));
                    }
                    System.out.println( map.get("a"));
                    countDownLatch.countDown();
                    return map.get("a");

                });
            } finally {

            }

        }
        countDownLatch.await();
    }
}
