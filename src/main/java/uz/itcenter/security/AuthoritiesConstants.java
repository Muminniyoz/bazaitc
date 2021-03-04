package uz.itcenter.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {
    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";
    public static final String CONTROLLER = "ROLE_CONTROLLER";
    public static final String ACCOUNTANT = "ROLE_ACCOUNTANT";
    public static final String DIRECTOR = "ROLE_DIRECTOR";
    public static final String MANAGER = "ROLE_MANAGER";
    public static final String TEACHER = "ROLE_TEACHER";
    public static final String SECRETARY = "ROLE_SECRETARY";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
