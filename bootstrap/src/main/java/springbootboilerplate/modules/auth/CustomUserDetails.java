package springbootboilerplate.modules.auth;

import cn.printf.springbootboilerplate.domain.Role;
import cn.printf.springbootboilerplate.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
public class CustomUserDetails implements UserDetails {
    private Long id;

    private String username;

    private Boolean enabled;

    private String password;

    private Set<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
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

    public static CustomUserDetails from(User user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<User, CustomUserDetails>() {
            @Override
            protected void configure() {
                map().setRoles(source.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
            }
        });
        return modelMapper.map(user, CustomUserDetails.class);
    }

    public static CustomUserDetails from(String username, Set<String> roles) {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUsername(username);
        customUserDetails.setRoles(roles);
        return customUserDetails;
    }
}
