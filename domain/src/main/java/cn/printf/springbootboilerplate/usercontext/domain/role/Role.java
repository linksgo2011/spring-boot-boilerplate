package cn.printf.springbootboilerplate.usercontext.domain.role;

import cn.printf.springbootboilerplate.usercontext.domain.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "role")
@Getter
@Setter
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String remark;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    private Timestamp createAt;

    private Timestamp updateAt;
}
