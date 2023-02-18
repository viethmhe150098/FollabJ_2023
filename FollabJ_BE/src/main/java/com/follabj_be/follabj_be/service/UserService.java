package com.follabj_be.follabj_be.service;

import com.follabj_be.follabj_be.config.securityConfig.PasswordEncoder;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.ConfirmToken;
import com.follabj_be.follabj_be.entity.Invitation;
import com.follabj_be.follabj_be.repository.InvitationRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import com.follabj_be.follabj_be.service.dependency.TokenInterface;
import com.follabj_be.follabj_be.service.dependency.UserInterface;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserService implements UserDetailsService, UserInterface {

    private final UserRepository userRepository;
    private final TokenInterface tokenInterface;
    private final PasswordEncoder passwordEncoder;
    private final InvitationRepository invitationRepository;

    public UserService(UserRepository userRepository, TokenInterface tokenInterface, PasswordEncoder passwordEncoder, InvitationRepository invitationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenInterface = tokenInterface;
        this.invitationRepository = invitationRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findAppUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", email)));
        log.info(user.toString());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            log.info(role.getName());
            authorities.add(new SimpleGrantedAuthority(role.getName()));

        });

        return new User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public String signUpUser(AppUser appUser) {
        boolean userExists = userRepository.findAppUserByEmail(appUser.getEmail()).isPresent();

        if (userExists) {

            AppUser appUserPrevious =  userRepository.findAppUserByEmail(appUser.getEmail()).get();
            int isEnabled = appUserPrevious.getStatus();

            if (isEnabled == 0) {
                String token = UUID.randomUUID().toString();

                //A method to save user and token in this class
                saveConfirmationToken(appUserPrevious, token);

                return token;

            }
            throw new IllegalStateException(String.format("User with email %s already exists!", appUser.getEmail()));
        }

        String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        //Saving the user after encoding the password
        userRepository.save(appUser);

        //Creating a token from UUID
        String token = UUID.randomUUID().toString();

        //Getting the confirmation token and then saving it
        saveConfirmationToken(appUser, token);


        //Returning token
        return token;
    }

    @Override
    public void saveConfirmationToken(AppUser appUser, String token) {
        ConfirmToken confirmationToken = new ConfirmToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), appUser);
        tokenInterface.saveConfirmationToken(confirmationToken);
    }

    @Override
    public void enableAppUser(String email, int status) {
        userRepository.enableAppUser(status, email);
    }

    public void activeUser(Long user_id){
        userRepository.updateRole(user_id);
    }

    @Override
    public List<UserDTO> findUsersByEmail(String email_cha) {
        List<UserDTO> foundUser = userRepository.findByEmailLike(email_cha);
        return foundUser;
    }

    @Override
    public List<Invitation> getAllInvitation(Long user_id) {
        userRepository.findById(user_id).orElseThrow(()-> new ObjectNotFoundException("Not found user", user_id.toString()));
        return invitationRepository.findByUserId(user_id);
    }
}
