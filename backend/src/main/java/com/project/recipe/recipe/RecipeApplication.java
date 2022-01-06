package com.project.recipe.recipe;

import com.project.recipe.recipe.entity.user.Role;
import com.project.recipe.recipe.entity.user.UserInfo;
import com.project.recipe.recipe.repository.RoleRepository;
import com.project.recipe.recipe.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@Slf4j
public class RecipeApplication {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(RecipeApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
                log.info("[CommandLineRunner->] initial user data is creating");
                createInitialUserData();
               log.info("[CommandLineRunner->] initial user data has been created");
        };
    }

    private void createInitialUserData(){

        try{


            Role adminRole = Role.builder().id(1).name("ADMIN").build();

            Role userRole = Role.builder().id(2).name("USER").build();

            roleRepository.save(adminRole);
            roleRepository.save(userRole);


            Set<Role> roleListAdmin = Stream.of(adminRole, userRole).collect(Collectors.toSet());

            UserInfo admin=UserInfo.builder().id(1l).
                    username("burak").userStatus("E")
                    .password(encoder.encode("12345")).
                    roles(roleListAdmin).build();


            Set<Role> roleListUser = Stream.of(userRole).collect(Collectors.toSet());

            UserInfo user=UserInfo.builder().id(2l).
                    username("demo").userStatus("E").
                    password(encoder.encode("12345")).roles(roleListUser).build();

            userRepository.save(admin);

            userRepository.save(user);

        }catch(Exception e){
            log.info("Data has not been initialized yet");
        }
    }


}
