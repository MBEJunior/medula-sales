package com.medulasales.products.entities;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

/**
 * The ProductAggregator class
 *
 * @author Junior Mbe
 * @version 1.0
 * @since 26/06/2019
 */
public class ProductAggregator implements ArgumentsAggregator {
    private static final String NULL_VALUE = "@null";

    private static final int NAME_INDEX = 0;
    private static final int DESCRIPTION_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int UNIT_PRICE_INDEX = 3;

    private static final int _ISSUE_INDEX = 4;
    private static final int _EXCEPTION_CLASS_INDEX = 5;
    private static final int _DESCRIPTION_INDEX = 6;

    @Override
    public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
        return new ParameterizedInput<Product>(
          Product.create(
                NULL_VALUE.equals(accessor.getString(NAME_INDEX)) ? null : accessor.getString(NAME_INDEX),
                NULL_VALUE.equals(accessor.getString(DESCRIPTION_INDEX)) ? null : accessor.getString(DESCRIPTION_INDEX),
                NULL_VALUE.equals(accessor.getString(QUANTITY_INDEX)) ? null : accessor.getFloat(QUANTITY_INDEX),
                NULL_VALUE.equals(accessor.getString(UNIT_PRICE_INDEX)) ? null : accessor.getFloat(UNIT_PRICE_INDEX),
                null,
            null
            ),
          ParameterizedInput.Issue.valueOf(NULL_VALUE.equals(accessor.getString(_ISSUE_INDEX)) ? null : accessor.getString(_ISSUE_INDEX)),
          NULL_VALUE.equals(accessor.getString(_EXCEPTION_CLASS_INDEX)) ? null : accessor.getString(_EXCEPTION_CLASS_INDEX),
          NULL_VALUE.equals(accessor.getString(_DESCRIPTION_INDEX)) ? null : accessor.getString(_DESCRIPTION_INDEX)
        );
    }
}
