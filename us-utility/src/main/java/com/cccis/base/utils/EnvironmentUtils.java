package com.cccis.base.utils;

import java.util.UUID;

/**
 * 获取系统环境工具类
 */
public final class EnvironmentUtils {

    /**
     * 开发环境模式
     */
    public static final String DEV_MODE = ConstantUtils.getValue("isDevMode");

    /**
     * 执行JOB的机器
     */
    public static final String BATCH_SERVER = ConstantUtils.getValue("BatchServer");

    /**
     * 服务器ID
     */
    public static final String SERVER_ID = ConstantUtils.getValue("ccc.server.id");

    /**
     * UUID
     */
    private static final String DEFAULT_SERVER_ID = UUID.randomUUID().toString();

    private EnvironmentUtils() {

    }

    /**
     * 获取server 信息
     *
     * @return serverId
     */
    public static String getServerId() {
        String serverId = System.getProperty(SERVER_ID);
        if (StringUtils.isEmpty(serverId)) {
            serverId = DEFAULT_SERVER_ID;
        }
        serverId += ("-" + NetworkUtils.getIpAddrUnderVirtual());
        return serverId;
    }

    /**
     * 判断是否为JOB服务器
     *
     * @return
     */
    public static boolean isBatchJobServer() {

        final String batchServer = System.getProperty(BATCH_SERVER);
        return StringUtils.isNotEmpty(batchServer);
    }
}
