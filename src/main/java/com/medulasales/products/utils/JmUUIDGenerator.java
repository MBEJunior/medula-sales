package com.medulasales.products.utils;

import java.io.Serializable;
import java.util.UUID;

public class JmUUIDGenerator {
    public static Serializable generate() {
        return UUID.randomUUID().toString();
    }
    public static Serializable generateDashless() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
