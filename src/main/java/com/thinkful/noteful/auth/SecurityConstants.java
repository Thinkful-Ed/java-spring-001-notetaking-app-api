package com.thinkful.noteful.auth;

public class SecurityConstants {
  public static final String JWT_SECRET = "jwt_secret_key_such_as_amazing_squid_nosed_accountant";
  public static final long EXPIRATION_TIME = 604_800_000; //7 days
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_NAME = "Authorization";
  public static final String SIGN_UP_URL = "/api/users/sign-up";
}