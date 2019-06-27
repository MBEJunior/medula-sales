package com.medulasales.products;

import com.medulasales.products.properties.ControllerProperties;
import com.medulasales.products.properties.ServiceProperties;
import com.medulasales.products.properties.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class, ServiceProperties.class, ControllerProperties.class})
public class MSPApplication {

    public static void main(String[] args) {
        SpringApplication.run(MSPApplication.class, args);
    }
}
