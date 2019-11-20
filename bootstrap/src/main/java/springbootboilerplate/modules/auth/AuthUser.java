package springbootboilerplate.modules.auth;

import cn.printf.springbootboilerplate.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser implements UserDetails {
    private Long id;

    private String username;

    private String email;

    private String phone;

    private Boolean enabled;

    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO
        return Arrays.asList(new SimpleGrantedAuthority("admin"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public static AuthUser of(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, AuthUser.class);
    }
}
