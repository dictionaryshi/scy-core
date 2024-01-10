package com.scy.core.thread;

import com.scy.core.format.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class SimpleDelayQueue<E> {

    private final Map<Integer, List<E>> ringData = new ConcurrentHashMap<>();

    public boolean offer(E e, long delayTime) {
        int ringSecond = (int) ((delayTime / 1000) % 60);
        List<E> ringSeconds = ringData.computeIfAbsent(ringSecond, k -> new ArrayList<>());
        return ringSeconds.add(e);
    }

    public List<E> poll() {
        List<E> ringSeconds = new ArrayList<>();
        int nowSecond = Calendar.getInstance().get(Calendar.SECOND);
        IntStream.rangeClosed(nowSecond - 2, nowSecond)
                .map(i -> (i + 60) % 60)
                .mapToObj(ringData::remove)
                .filter(Objects::nonNull)
                .forEach(ringSeconds::addAll);
        return ringSeconds;
    }

    public static void main(String[] args) {
        SimpleDelayQueue<Integer> simpleDelayQueue = new SimpleDelayQueue<>();
        long delayTime = System.currentTimeMillis();
        System.out.println("当前时间:" + DateUtil.getCurrentDateStr());

        int i = 1;
        while (true) {
            delayTime = delayTime + 1000;
            if (delayTime <= System.currentTimeMillis() + 10_000) {
                simpleDelayQueue.offer(i, delayTime);
            } else {
                break;
            }
            i++;
        }

        while (true) {
            ThreadUtil.quietSleep(1000 - System.currentTimeMillis() % 1000);

            List<Integer> ringSeconds = simpleDelayQueue.poll();
            if (!ringSeconds.isEmpty()) {
                System.out.println(DateUtil.getCurrentDateStr() + "-" + ringSeconds);
            }
        }
    }
}
