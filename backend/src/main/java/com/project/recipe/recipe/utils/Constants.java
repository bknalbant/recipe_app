package com.project.recipe.recipe.utils;

public interface Constants {

    public static final String DATE_FORMAT_dd_MM_yyyy="dd‐MM‐yyyy HH:mm";

    public static final String SWAGGER_APP_DESC="Spring Boot Recipe Application";

    public static final String SWAGGER_APP_TITLE="Recipe Application";

    public static final String SWAGGER_VERSION="1.0.0";

    public static final String LOGIN_PAGE="/login";

    public static final String SIGNUP_PAGE="/recipe/v1/users/signup";

    public static final String RECIPE_PAGES="/api/v1/recipe/**";

    public static final String USER_ROLE="USER";

    public static final String ADMIN_ROLE="ADMIN";

    public static final String HEADER_AUTHORIZATION="Authorization";

    public static final String HEADER_BEARER="Bearer";

    public static final String HEADER_SecretKeyToGenJWTs="SecretKeyToGenJWTs";

    public static final String ROLES="roles";

    public static final String AUTHORITY="authority";

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    public  static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/",
            "/swagger-ui/**",
            "/h2-console/**",
            "/webjars/**"
    };

    public static final String H2_CONSOLE_IGNORE="/h2-console/**";
    public static final String EMPTY_STRING = "";
    public static final String AUTHENTICA_PAGE = "/recipe/v1/users/authenticate";
    public static final String USER_DEFAULT_PAGE="/recipe/v1/users/default";


    public enum Return {

        SUCCESS("0000", "SUCCEED"),
        FAIL("0001", "FAILED"),
        RECIPE_ALREADY_EXIST("0002", "Recipe already exist!"),
        RECIPE_NOT_FOUND_EXCEPTION("0003", "Recipe could not be found!"),
        USER_ALREADY_EXIST("0004", "User already exist"),
        USERNAME_NOT_FOUND_EXCEPTION("0005", "Username could not be founded!");

        private String code;
        private String message;

        public String getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }

        private Return(String code, String message) {
            this.code = code;
            this.message = message;
        }

    }

}
