package config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public final class AppConfig {

    private static final String LEGACY_ADMIN_HASH =
            "f8e68e8d44bfb5314974a97f787d017ff6ac9d0046083f28665fcf96f0cef80c";
    private static final String LEGACY_USER_HASH =
            "a511c7477309c83dadaa570c9afa50903b73aa08a53bd5ff63b95f684d631ae4";
    private static final String LEGACY_DELETE_HASH =
            "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5";

    private AppConfig() {
    }

    public static String getDatabaseUrl() {
        return setting("crms.db.url", "CRMS_DB_URL", "jdbc:mysql://localhost:3306/mydb");
    }

    public static String getDatabaseUser() {
        return setting("crms.db.user", "CRMS_DB_USER", "root");
    }

    public static String getDatabasePassword() {
        return setting("crms.db.password", "CRMS_DB_PASSWORD", "1234");
    }

    public static String getDatabaseUrlWithOptions(String options) {
        String url = getDatabaseUrl();
        if (options == null || options.trim().isEmpty()) {
            return url;
        }
        return url + (url.contains("?") ? "&" : "?") + options;
    }

    public static boolean isAdminLogin(String username, char[] password) {
        return verifyLogin("admin", username, password,
                setting("crms.admin.loginHash", "CRMS_ADMIN_LOGIN_HASH", LEGACY_ADMIN_HASH));
    }

    public static boolean isUserLogin(String username, char[] password) {
        return verifyLogin("user", username, password,
                setting("crms.user.loginHash", "CRMS_USER_LOGIN_HASH", LEGACY_USER_HASH));
    }

    public static boolean verifyDeletePassword(String password) {
        char[] chars = password == null ? new char[0] : password.toCharArray();
        try {
            return constantTimeEquals(
                    sha256(new String(chars)),
                    setting("crms.delete.passwordHash", "CRMS_DELETE_PASSWORD_HASH", LEGACY_DELETE_HASH));
        } finally {
            Arrays.fill(chars, '\0');
        }
    }

    private static boolean verifyLogin(String requiredUser, String username, char[] password, String expectedHash) {
        try {
            String value = requiredUser + ":" + new String(password);
            return requiredUser.equals(username) && constantTimeEquals(sha256(value), expectedHash);
        } finally {
            Arrays.fill(password, '\0');
        }
    }

    private static String setting(String propertyName, String environmentName, String fallback) {
        String value = System.getProperty(propertyName);
        if (value == null || value.trim().isEmpty()) {
            value = System.getenv(environmentName);
        }
        return value == null || value.trim().isEmpty() ? fallback : value.trim();
    }

    private static String sha256(String value) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 is unavailable.", ex);
        }
    }

    private static boolean constantTimeEquals(String actual, String expected) {
        return MessageDigest.isEqual(
                actual.getBytes(StandardCharsets.US_ASCII),
                expected.getBytes(StandardCharsets.US_ASCII));
    }
}
