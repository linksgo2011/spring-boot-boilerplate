package cn.printf.springbootboilerplate.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@EqualsAndHashCode(of = "username")
@ToString(of = "username")
@Getter
@Setter
@Accessors(chain = true)
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String email;

    private String phone;

    private Boolean enabled;

    private String password;

    @CreationTimestamp
    private Timestamp createAt;

    @UpdateTimestamp
    private Timestamp updateAt;
}
