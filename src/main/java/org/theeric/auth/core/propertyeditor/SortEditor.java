package org.theeric.auth.core.propertyeditor;

import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.StringJoiner;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import com.google.common.collect.Lists;

public class SortEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        final List<Order> orders = Lists.newArrayList();

        for (String order : StringUtils.split(text, ",")) {
            final String[] tokens = StringUtils.split(StringUtils.strip(order), ":");
            if (tokens.length == 0) {
                throw new IllegalArgumentException("Invalid sort");
            }

            final String property = tokens[0];
            if (tokens.length == 1) {
                orders.add(Order.asc(property));

            } else if (tokens.length == 2) {
                final String direction = tokens[1];
                orders.add(new Order(Direction.fromString(direction), property));
            }
        }

        if (orders.size() == 0) {
            setValue(Sort.unsorted());
        } else {
            setValue(Sort.by(orders));
        }
    }

    @Override
    public String getAsText() {
        final Sort sort = (Sort) getValue();
        if (sort == null || sort.isUnsorted()) {
            return "";
        }

        final StringJoiner joiner = new StringJoiner(",");
        for (Order order : sort) {
            final String direction = StringUtils.lowerCase(order.getDirection().name());
            joiner.add(String.format("%s:%s", order.getProperty(), direction));
        }
        return joiner.toString();
    }

}
