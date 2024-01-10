package com.scy.core.thread;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class SimpleDelayQueue<E> {

    private volatile Map<Integer, List<E>> ringData = new ConcurrentHashMap<>();

    public boolean offer(E e, long delayTime) {
        int ringSecond = (int) ((delayTime / 1000) % 60);
        List<E> ringSeconds = ringData.computeIfAbsent(ringSecond, k -> new ArrayList<>());
        return ringSeconds.add(e);
    }

    public List<E> poll() {
        List<E> ringSeconds = new ArrayList<>();
        int nowSecond = Calendar.getInstance().get(Calendar.SECOND);
        IntStream.rangeClosed(nowSecond - 3, nowSecond)
                .map(i -> (i + 60) % 60)
                .mapToObj(ringData::remove)
                .filter(Objects::nonNull)
                .forEach(ringSeconds::addAll);
        return ringSeconds;
    }
}
