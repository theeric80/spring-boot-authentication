package org.theeric.auth.core.web.pagination;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import com.google.common.collect.ImmutableList;

public class PageNumberPaginationTest {

    private static MockHttpServletRequest buildRequest() {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("127.0.0.1");
        request.setRequestURI("/");
        request.addParameter("page", "0");
        request.addParameter("size", "2");
        return request;
    }

    private static MockHttpServletRequest buildRequest(String sort) {
        final MockHttpServletRequest request = buildRequest();

        if (StringUtils.isNotBlank(sort)) {
            request.addParameter("sort", sort);
        }
        return request;
    }

    private static Stream<Arguments> getTestParameters() {
        final HttpServletRequest request = buildRequest();
        final MockHttpServletRequest requestWithSort = buildRequest("id");

        final Page<?> first = new PageImpl<>(ImmutableList.of(), PageRequest.of(0, 2), 10);
        final Page<?> second = new PageImpl<>(ImmutableList.of(), PageRequest.of(2, 2), 10);
        final Page<?> last = new PageImpl<>(ImmutableList.of(), PageRequest.of(4, 2), 10);
        final Page<?> lastPlus1 = new PageImpl<>(ImmutableList.of(), PageRequest.of(5, 2), 10);
        final Page<?> firstWithSort = new PageImpl<>(ImmutableList.of(), PageRequest.of(0, 2, Sort.by("id")), 10);

        final List<Integer> data = ImmutableList.of(1, 2);

        // @formatter:off
        return Stream.of( //
                Arguments.of(first,    request, data, "<http://127.0.0.1/?page=1&size=2>; rel=\"next\", <http://127.0.0.1/?page=4&size=2>; rel=\"last\""), //
                Arguments.of(second,   request, data, "<http://127.0.0.1/?page=3&size=2>; rel=\"next\", <http://127.0.0.1/?page=4&size=2>; rel=\"last\", <http://127.0.0.1/?page=0&size=2>; rel=\"first\", <http://127.0.0.1/?page=1&size=2>; rel=\"prev\""), //
                Arguments.of(last,     request, data, "<http://127.0.0.1/?page=0&size=2>; rel=\"first\", <http://127.0.0.1/?page=3&size=2>; rel=\"prev\""), //
                Arguments.of(lastPlus1,request, data, "<http://127.0.0.1/?page=0&size=2>; rel=\"first\", <http://127.0.0.1/?page=4&size=2>; rel=\"prev\""), //
                Arguments.of(firstWithSort, requestWithSort, data, "<http://127.0.0.1/?page=1&size=2&sort=id>; rel=\"next\", <http://127.0.0.1/?page=4&size=2&sort=id>; rel=\"last\"") //
        );
        // @formatter:on 
    }

    @ParameterizedTest
    @MethodSource("getTestParameters")
    public void build(Page<?> page, HttpServletRequest request, List<Integer> data, String expected) {
        final PageNumberPagination<?> paginator = PageNumberPagination.of(page, request);

        final ResponseEntity<List<Integer>> response = paginator.build(data);
        final String linkHeader = response.getHeaders().getFirst(HttpHeaders.LINK);

        assertThat(linkHeader).isNotNull();
        assertThat(linkHeader).isEqualTo(expected);
    }

}
