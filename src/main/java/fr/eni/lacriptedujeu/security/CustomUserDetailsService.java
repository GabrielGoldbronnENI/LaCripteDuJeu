package fr.eni.lacriptedujeu.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if ("admin@gmail.com".equals(email)) {
            return User.builder()
                    .username(email)
                    .password(new BCryptPasswordEncoder().encode("admin")) // Remplacer par un vrai mot de passe
                    .roles("USER", "ADMIN")
                    .build();
        }

        throw new UsernameNotFoundException("User not found");
    }
}
