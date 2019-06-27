package com.medulasales.products.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("medula-sales.products.services")
@Getter
@Setter
public class ServiceProperties {
    private int defaultPageNumber = 1;
    private int defaultPageSize = 30;
    private int maxPageSize = 100;
}
