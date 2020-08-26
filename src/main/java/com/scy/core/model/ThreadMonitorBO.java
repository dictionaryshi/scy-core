package com.scy.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ThreadMonitorBO
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/26.
 */
@Getter
@Setter
@ToString
public class ThreadMonitorBO {

    /**
     * 用户配置核心线程数
     */
    private int corePoolSize;

    /**
     * 用户配置最大线程数
     */
    private int maximumPoolSize;

    /**
     * 用户配置队列数
     */
    private long workQueueSize;

    /**
     * 当前线程池中线程总数
     */
    private int poolSize;

    /**
     * 当前线程池中正在执行的线程数
     */
    private int activeCount;

    /**
     * 线程池中曾达到的最大线程数
     */
    private int largestPoolSize;

    /**
     * 线程池中总任务数(已完成任务 + 正在执行任务 + 队列缓存任务)
     */
    private long taskCount;

    /**
     * 线程池已完成任务数
     */
    private long completedTaskCount;
}
