package com.project.recipe.recipe.entity.user;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="roles")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name",nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserInfo> userInfos;


}
