package com.dream.restapi.utils;

import com.dream.restapi.model.User;

public class RequestContext {
    private static final ThreadLocal<User> userContext = new ThreadLocal<User>();

    private RequestContext() {}

    public static void setCurrentUser(User data) {
        userContext.set(data);
    }

    public static User getCurrentUser() {
        return userContext.get();
    }

    public static void removeCurrentUser() {
        userContext.remove();
    }
}
