package com.dempseywood.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String DW = "ROLE_DW";

    public static final String QUATTRA = "ROLE_QUATTRA";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String DW_READ_ONLY = "ROLE_DW_READ_ONLY";

    private AuthoritiesConstants() {
    }
}
