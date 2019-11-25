package springbootboilerplate.modules.auth;

import cn.printf.springbootboilerplate.domain.Role;
import cn.printf.springbootboilerplate.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser implements UserDetails {
    private Long id;

    private String username;

    private Boolean enabled;

    private String password;

    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(
                (role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet()
        );
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

    public static AuthUser from(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, AuthUser.class);
    }
}
