package org.example.filterexam.filterExam4.filter;

// ThreadLocal을 이용해서 필요한 정보를 저장하는데
// 이 ThreadLocal 을 가지는 객체


import org.example.filterexam.filterExam4.entity.User;

public class UserContext {
    private static final ThreadLocal<User> USER_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);

    public static void setUser(User user) {
        USER_THREAD_LOCAL.set(user);
    }

    public static User getUser() {
        return USER_THREAD_LOCAL.get();
    }

    public static void clear() {
        USER_THREAD_LOCAL.remove();
    }
}
