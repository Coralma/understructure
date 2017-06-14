package com.cccis.base.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * Created by CCC on 2016/3/21.
 */
public class HystrixSetter {

    /** 判断是否启用缓存 */
    public static String defaultCacheEnable = "false";
    /** 默认端口超时设置 */
    public static String defaultTimeout = "1800000";
    /** 默认接口健康情况收集时间，计算这段时间范围内的错误率百分比，默认为500毫秒，扩展到10秒 */
    public static String defaultMetricsHealthSnapshotIntervalInMilliseconds = "10000";
    /** 触发接口熔断的错误次数。低于这个错误次数就不会触发熔断。*/
    public static String defaultCircuitBreakerRequestVolumeThreshold = "50";
    /** 在触发熔断后接口将等待100000毫秒后再次尝试调用。*/
    public static String defaultCircuitBreakerSleepWindowInMilliseconds = "100000";
    /** 熔断触发后是否拒绝所有请求。*/
    public static String defaultCircuitBreakerForceOpen = "false";
    /** 默认最大支持的并发数，如果超出这个并发数的请求将会被拒绝。*/
    public static String defaultExecutionIsolationSemaphoreMaxConcurrentRequests = "500";
    /** 默认最大支持的回滚并发数，如果超出这个并发数的回滚请求将会被拒绝。*/
    public static String defaultFallbackIsolationSemaphoreMaxConcurrentRequests = "50";
    /** 默认最大线程池。计算方式: (每秒请求书 * 高峰时99.5%的平均响应时间) + 向上预留5个线程即可 */
    public static String defaultCorePoolSize = "10";
    /** 默认最大队列等待数。 建议计算一个正确容量的线程池，而不建议采用队列等待方式。*/
    public static String defaultMaxQueueSize = "-1";

    /**
     * 令分组用于对依赖操作分组,便于统计,汇总等. CommandGroup是每个命令最少配置的必选参数，
     * 在不指定ThreadPoolKey的情况下，字面值用于对不同依赖的线程池/信号区分.相同的groupkey和commandKey会公用同一个缓存。
     * @param groupKey
     * @param commandKey
     * @return
     */
    public static HystrixCommand.Setter initSetter(String groupKey, String commandKey) {
        return HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        .withRequestCacheEnabled(Boolean.valueOf(defaultCacheEnable))
                        .withMetricsHealthSnapshotIntervalInMilliseconds(Integer.valueOf(defaultMetricsHealthSnapshotIntervalInMilliseconds))
                        .withCircuitBreakerRequestVolumeThreshold(Integer.valueOf(defaultCircuitBreakerRequestVolumeThreshold))
                        .withCircuitBreakerSleepWindowInMilliseconds(Integer.valueOf(defaultCircuitBreakerSleepWindowInMilliseconds))
                        .withExecutionTimeoutInMilliseconds(Integer.valueOf(defaultTimeout)).withCircuitBreakerForceOpen(Boolean.valueOf(defaultCircuitBreakerForceOpen))
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(Integer.valueOf(defaultExecutionIsolationSemaphoreMaxConcurrentRequests))
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(Integer.valueOf(defaultFallbackIsolationSemaphoreMaxConcurrentRequests)))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(Integer.valueOf(defaultCorePoolSize)).withMaxQueueSize(Integer.valueOf(defaultMaxQueueSize)));
    }

    public void setDefaultCacheEnable(String defaultCacheEnable) {
        HystrixSetter.defaultCacheEnable = defaultCacheEnable;
    }

    public void setDefaultTimeout(String defaultTimeout) {
        HystrixSetter.defaultTimeout = defaultTimeout;
    }

    public void setDefaultMetricsHealthSnapshotIntervalInMilliseconds(String defaultMetricsHealthSnapshotIntervalInMilliseconds) {
        HystrixSetter.defaultMetricsHealthSnapshotIntervalInMilliseconds = defaultMetricsHealthSnapshotIntervalInMilliseconds;
    }

    public void setDefaultCircuitBreakerRequestVolumeThreshold(String defaultCircuitBreakerRequestVolumeThreshold) {
        HystrixSetter.defaultCircuitBreakerRequestVolumeThreshold = defaultCircuitBreakerRequestVolumeThreshold;
    }

    public void setDefaultCircuitBreakerSleepWindowInMilliseconds(String defaultCircuitBreakerSleepWindowInMilliseconds) {
        HystrixSetter.defaultCircuitBreakerSleepWindowInMilliseconds = defaultCircuitBreakerSleepWindowInMilliseconds;
    }

    public void setDefaultCircuitBreakerForceOpen(String defaultCircuitBreakerForceOpen) {
        HystrixSetter.defaultCircuitBreakerForceOpen = defaultCircuitBreakerForceOpen;
    }

    public void setDefaultExecutionIsolationSemaphoreMaxConcurrentRequests(String defaultExecutionIsolationSemaphoreMaxConcurrentRequests) {
        HystrixSetter.defaultExecutionIsolationSemaphoreMaxConcurrentRequests = defaultExecutionIsolationSemaphoreMaxConcurrentRequests;
    }

    public void setDefaultFallbackIsolationSemaphoreMaxConcurrentRequests(String defaultFallbackIsolationSemaphoreMaxConcurrentRequests) {
        HystrixSetter.defaultFallbackIsolationSemaphoreMaxConcurrentRequests = defaultFallbackIsolationSemaphoreMaxConcurrentRequests;
    }

    public void setDefaultCorePoolSize(String defaultCorePoolSize) {
        HystrixSetter.defaultCorePoolSize = defaultCorePoolSize;
    }

    public void setDefaultMaxQueueSize(String defaultMaxQueueSize) {
        HystrixSetter.defaultMaxQueueSize = defaultMaxQueueSize;
    }
}
