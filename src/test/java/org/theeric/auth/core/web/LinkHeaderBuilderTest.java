package org.theeric.auth.core.web;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.google.common.collect.ImmutableList;

public class LinkHeaderBuilderTest {

    private static Stream<Arguments> getTestParameters() {

        final Page<?> first = new PageImpl<>(ImmutableList.of(), PageRequest.of(0, 2), 10);
        final Page<?> second = new PageImpl<>(ImmutableList.of(), PageRequest.of(2, 2), 10);
        final Page<?> last = new PageImpl<>(ImmutableList.of(), PageRequest.of(4, 2), 10);
        final Page<?> lastPlus1 = new PageImpl<>(ImmutableList.of(), PageRequest.of(5, 2), 10);
        final Page<?> hasSort = new PageImpl<>(ImmutableList.of(), PageRequest.of(0, 2, Sort.by("id")), 10);

        // @formatter:off
        return Stream.of( //
                Arguments.of("http://127.0.0.1/", first,    "<http://127.0.0.1/?page=1&size=2>; rel=\"next\", <http://127.0.0.1/?page=4&size=2>; rel=\"last\""), //
                Arguments.of("http://127.0.0.1/", second,   "<http://127.0.0.1/?page=3&size=2>; rel=\"next\", <http://127.0.0.1/?page=4&size=2>; rel=\"last\", <http://127.0.0.1/?page=0&size=2>; rel=\"first\", <http://127.0.0.1/?page=1&size=2>; rel=\"prev\""), //
                Arguments.of("http://127.0.0.1/", last,     "<http://127.0.0.1/?page=0&size=2>; rel=\"first\", <http://127.0.0.1/?page=3&size=2>; rel=\"prev\""), //
                Arguments.of("http://127.0.0.1/", lastPlus1,"<http://127.0.0.1/?page=0&size=2>; rel=\"first\", <http://127.0.0.1/?page=4&size=2>; rel=\"prev\""), //
                Arguments.of("http://127.0.0.1/", hasSort,  "<http://127.0.0.1/?page=1&size=2&sort=id:asc>; rel=\"next\", <http://127.0.0.1/?page=4&size=2&sort=id:asc>; rel=\"last\"") //
        );
        // @formatter:on 
    }

    @ParameterizedTest
    @MethodSource("getTestParameters")
    public void setAsText(String url, Page<?> page, String expected) {
        final LinkHeaderBuilder<?> builder = LinkHeaderBuilder.of(url, page);

        final String link = builder.build();

        assertThat(link).isEqualTo(expected);
    }

}
