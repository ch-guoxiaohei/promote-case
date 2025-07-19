package com.guoxiaohe.sample;

import com.guoxiaohe.sample.spi.SpiLoggerSample;

import java.util.ServiceLoader;

public class MainClient {

    public static void main(String[] args) {
        ServiceLoader<SpiLoggerSample> serviceLoader = ServiceLoader.load(SpiLoggerSample.class);
        for (SpiLoggerSample spiLoggerSample : serviceLoader) {
            spiLoggerSample.log("hello");
            spiLoggerSample.error("hello");
        }
    }
}
