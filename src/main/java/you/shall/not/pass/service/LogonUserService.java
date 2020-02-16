package you.shall.not.pass.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import you.shall.not.pass.domain.grant.AccessGrant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LogonUserService {

    public static Optional<AccessGrant> getCurrentAccessLevel() {
        return getGateKeeperGrant();
    }

    static Optional<AccessGrant> getGateKeeperGrant() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = ((UserDetails) principal);
            List<GrantedAuthority> targetList = new ArrayList<>(userDetails.getAuthorities());
            return targetList.stream().map(grantedAuthority ->
                    AccessGrant.valueOf(grantedAuthority.getAuthority())).findAny();
        }
        return Optional.empty();
    }

    public static Optional<String> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = ((UserDetails) principal);
            return Optional.of(userDetails.getUsername());
        }
        return Optional.empty();
    }

}
