package org.theeric.auth.core.context;

import org.springframework.util.Assert;

public class UserContextHolder {

    private static final ThreadLocal<UserContext> contextHolder = new ThreadLocal<>();

    public static void clearContext() {
        contextHolder.remove();
    }

    public static UserContext getContext() {
        UserContext ctx = contextHolder.get();

        if (ctx == null) {
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }

        return ctx;
    }

    public static void setContext(UserContext context) {
        Assert.notNull(context, "Only non-null UserContext instances are permitted");
        contextHolder.set(context);
    }

    public static UserContext createEmptyContext() {
        return new UserContext();
    }

}
