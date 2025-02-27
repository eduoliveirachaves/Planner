package com.edu.planner.utils;

import jakarta.servlet.http.Cookie;

/**
 * CustomCookie class.
 * This class is used to create and delete cookies.
 * It provides methods to create a cookie with a token and to delete a cookie (logout).
 * Used by the AuthService class.
 */

public class CustomCookie {

    public static Cookie create(String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24); // 1 dia
        return cookie;
    }

    public static Cookie delete() {
        Cookie cookie = new Cookie("token", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }
}
