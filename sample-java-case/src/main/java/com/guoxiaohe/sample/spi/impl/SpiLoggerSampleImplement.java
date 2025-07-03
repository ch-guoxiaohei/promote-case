package com.guoxiaohe.sample.spi.impl;

import com.guoxiaohe.sample.spi.SpiLoggerSample;

public class SpiLoggerSampleImplement implements SpiLoggerSample {

    public SpiLoggerSampleImplement() {

    }

    @Override
    public void log(String msg) {
        System.out.println("SpiLoggerSampleImplement:" + msg);
    }

    @Override
    public void error(String msg) {
        System.out.println("SpiLoggerSampleImplement error:" + msg);
    }
}
