package com.project.recipe.recipe.entity.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity(name="user")
@Table(name="user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="username",nullable=false,unique = true)
    @NotEmpty(message = "UserName could not be empty!")
    private String username;

    @Column(name="password",nullable=false)
    @NotEmpty(message = "Password could not be empty!")
    private String password;

    @Column(name="user_statu",length = 1)
    private String userStatus;

    @ManyToMany(fetch =FetchType.EAGER)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles;

}
