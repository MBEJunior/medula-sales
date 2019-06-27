package com.medulasales.products;


import com.medulasales.products.configs.MSPPersistenceConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MSPApplication.class, MSPPersistenceConfig.class})
@DisplayName("Medula Sales Products Application test")
public class MSPApplicationTest {
    @Test
    @DisplayName("Context loads test")
    public void contextLoads() {

    }
}
