package com.ymkj.im.config.durid;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by zz1987 on 2018/3/15 0015.
 */
@ConfigurationProperties(prefix = "druid")
public class DruidProperties {
    private String driverClass;
    private int maxActive;
    private int minIdle;
    private int initialSize;
    private boolean testOnBorrow;


    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }
}
