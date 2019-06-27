package com.medulasales.products.utils.jmgenericxrefqueryretriever;

import javax.persistence.criteria.Expression;

class JmOrderImpl implements javax.persistence.criteria.Order {
    private boolean ascending;
    private Expression<?> expression;

    @Override
    public javax.persistence.criteria.Order reverse() {
        this.ascending = !this.ascending;
        return this;
    }

    @Override
    public boolean isAscending() {
        return ascending;
    }

    @Override
    public Expression<?> getExpression() {
        return expression;
    }

    private JmOrderImpl() {}

    public static JmOrderImpl of(Expression expression, boolean ascending) {
        JmOrderImpl orderItem = new JmOrderImpl();
        orderItem.expression = expression;
        orderItem.ascending = ascending;
        return orderItem;
    }
}
