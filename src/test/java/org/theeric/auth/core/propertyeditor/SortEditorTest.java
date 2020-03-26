package org.theeric.auth.core.propertyeditor;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import com.google.common.collect.ImmutableList;

public class SortEditorTest {

    private static Stream<Arguments> getSetAsTextTestParameters() {
        return Stream.of( //
                Arguments.of("", ImmutableList.<Order>of()), //
                Arguments.of("a", ImmutableList.of(Order.asc("a"))), //
                Arguments.of("a:asc", ImmutableList.of(Order.asc("a"))), //
                Arguments.of("a:desc", ImmutableList.of(Order.desc("a"))), //
                Arguments.of("a,b", ImmutableList.of(Order.asc("a"), Order.asc("b"))), //
                Arguments.of("a,b:asc", ImmutableList.of(Order.asc("a"), Order.asc("b"))), //
                Arguments.of("a,b:desc", ImmutableList.of(Order.asc("a"), Order.desc("b"))), //
                Arguments.of("a:asc,b", ImmutableList.of(Order.asc("a"), Order.asc("b"))), //
                Arguments.of("a:asc,b:asc", ImmutableList.of(Order.asc("a"), Order.asc("b"))), //
                Arguments.of("a:asc,b:desc", ImmutableList.of(Order.asc("a"), Order.desc("b"))), //
                Arguments.of("a:desc,b", ImmutableList.of(Order.desc("a"), Order.asc("b"))), //
                Arguments.of("a:desc,b:asc", ImmutableList.of(Order.desc("a"), Order.asc("b"))), //
                Arguments.of("a:desc,b:desc", ImmutableList.of(Order.desc("a"), Order.desc("b"))) //
        );
    }

    private static Stream<Arguments> getSetAsTextInvalidTestParameters() {
        return Stream.of( //
                Arguments.of("too:many:colons", new IllegalArgumentException("Invalid sort")), //
                Arguments.of("a:unknown", new IllegalArgumentException("Invalid value 'unknown' for orders given")) //
        );
    }

    private static Stream<Arguments> getGetAsTextTestParameters() {
        return Stream.of( //
                Arguments.of(ImmutableList.<Order>of(), ""), //
                Arguments.of(ImmutableList.of(Order.asc("a")), "a:asc"), //
                Arguments.of(ImmutableList.of(Order.desc("a")), "a:desc"), //
                Arguments.of(ImmutableList.of(Order.asc("a"), Order.asc("b")), "a:asc,b:asc"), //
                Arguments.of(ImmutableList.of(Order.asc("a"), Order.desc("b")), "a:asc,b:desc"), //
                Arguments.of(ImmutableList.of(Order.asc("a"), Order.asc("b")), "a:asc,b:asc"), //
                Arguments.of(ImmutableList.of(Order.asc("a"), Order.desc("b")), "a:asc,b:desc"), //
                Arguments.of(ImmutableList.of(Order.desc("a"), Order.asc("b")), "a:desc,b:asc"), //
                Arguments.of(ImmutableList.of(Order.desc("a"), Order.desc("b")), "a:desc,b:desc") //
        );
    }


    @ParameterizedTest
    @MethodSource("getSetAsTextTestParameters")
    public void setAsText(String text, List<Order> expected) {
        final SortEditor editor = new SortEditor();
        editor.setAsText(text);

        final Sort sort = (Sort) editor.getValue();
        assertThat(sort.toList()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getSetAsTextInvalidTestParameters")
    public void whenInvalidText_thenExceptionShouldBeThrown(String text, IllegalArgumentException expected) {
        final IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            final SortEditor editor = new SortEditor();
            editor.setAsText(text);
        });

        assertThat(e.getMessage()).startsWith(expected.getMessage());
    }

    @ParameterizedTest
    @MethodSource("getGetAsTextTestParameters")
    public void getAsText(List<Order> orders, String expected) {
        final Sort sort = Sort.by(orders);
        final SortEditor editor = new SortEditor();
        editor.setValue(sort);

        final String text = editor.getAsText();
        assertThat(text).isEqualTo(expected);
    }

}
