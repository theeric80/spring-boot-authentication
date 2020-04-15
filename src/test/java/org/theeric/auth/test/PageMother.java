package org.theeric.auth.test;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class PageMother {

    public static <T> Page<T> newPage(List<T> content) {
        return new PageImpl<T>(content);
    }

}
