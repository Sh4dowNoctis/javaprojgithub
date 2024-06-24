package epita.tp.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtility {
    public static String getUserRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().orElseThrow(() -> new RuntimeException("Role not found")).getAuthority();
    }
}
