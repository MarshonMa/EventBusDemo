package com.zxy.jsbridge;

import com.zxy.jsbridge.core.NativeMethodInjectHelper;

public class RainbowBridge {
    private static RainbowBridge sInstance;

    public static RainbowBridge getInstance() {
        RainbowBridge instance = sInstance;
        if (instance == null) {
            synchronized (RainbowBridge.class) {
                instance = sInstance;
                if (instance == null) {
                    instance = new RainbowBridge();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    public NativeMethodInjectHelper clazz(Class<?> clazz) {
        return NativeMethodInjectHelper.getInstance()
                .clazz(clazz);
    }
}
