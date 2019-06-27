package com.medulasales.products.entities;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * The EntityAuditorAware class
 *
 * @author Junior Mbe
 * @version 1.0
 * @since 27/06/2019
 */
public class EntityAuditorAware implements AuditorAware<String> {
    /**
     * Set return creator and modifier uuid of principal
     * @return Optional of principal uuid
     */
    @Override
    public Optional< String> getCurrentAuditor() {
        // todo return authenticated user uuid
        return Optional.of("16bce567-e687-4ec5-9eae-4c82c691e5c2");
    }
}
