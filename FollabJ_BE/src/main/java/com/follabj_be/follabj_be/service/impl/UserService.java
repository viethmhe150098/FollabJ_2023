package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.config.securityConfig.PasswordEncoder;
import com.follabj_be.follabj_be.dto.AppUserDTO;
import com.follabj_be.follabj_be.dto.PasswordDTO;
import com.follabj_be.follabj_be.dto.UpdateUserDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.AppUser;
import com.follabj_be.follabj_be.entity.ConfirmToken;
import com.follabj_be.follabj_be.entity.Invitation;
import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;
import com.follabj_be.follabj_be.repository.InvitationRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import com.follabj_be.follabj_be.service.EmailSender;
import com.follabj_be.follabj_be.service.TokenInterface;
import com.follabj_be.follabj_be.service.UserInterface;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserService implements UserDetailsService, UserInterface {

    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String specials = "~=+%^*/()[]{}/!@#$?|";
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static final String ALL = alpha + alphaUpperCase + digits + specials;

    private final UserRepository userRepository;
    private final TokenInterface tokenInterface;
    private final PasswordEncoder passwordEncoder;
    private final InvitationRepository invitationRepository;

    private final EmailSender emailSender;
    private final BuildEmail buildEmail;

    private static Random generator = new Random();

    public UserService(UserRepository userRepository, TokenInterface tokenInterface, PasswordEncoder passwordEncoder, InvitationRepository invitationRepository, EmailSender emailSender, BuildEmail buildEmail) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenInterface = tokenInterface;
        this.invitationRepository = invitationRepository;
        this.emailSender = emailSender;
        this.buildEmail = buildEmail;
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

            AppUser appUserPrevious = userRepository.findAppUserByEmail(appUser.getEmail()).get();
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
        userRepository.updateRole(user_id, 1);
    }

    @Override
    public List<UserDTO> findUsersByEmail(String email_cha) {
        List<UserDTO> foundUser = userRepository.findByEmailLike(email_cha);
        return foundUser;
    }

    @Override
    public List<Invitation> getAllInvitation(Long user_id) {
        userRepository.findById(user_id).orElseThrow(() -> new ObjectNotFoundException("Not found user", user_id.toString()));
        return invitationRepository.findByUserId(user_id);
    }

    @Override
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public AppUser getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<AppUser> getInvitedUserByProjectId(Long project_id) {
        return userRepository.findAllUserInvitedToProject(project_id);
    }

    @Override
    public AppUserDTO updateStatus(int status, Long u_id) {
        if(status == 2 || status == 1) {
            AppUser appUser = userRepository.findById(u_id).orElseThrow(() -> new ObjectNotFoundException("Not found user", u_id.toString()));
            appUser.setStatus(status);
            userRepository.save(appUser);
            AppUserDTO aud = new AppUserDTO(appUser.getId(), appUser.getEmail(), appUser.getUsername(), appUser.getStatus());
            return aud;
        }else{
            return null;
        }
    }

    @Override
    public String changePassword(PasswordDTO passwordDTO, Long u_id) {
        AppUser appUser = userRepository.findById(u_id).orElseThrow(() -> new ObjectNotFoundException("Not found user", u_id.toString()));
        String old_password = passwordDTO.getOld_password();
        String new_password = passwordDTO.getNew_password();
        if(u_id == passwordDTO.getReq_u_id()){
            if(passwordEncoder.bCryptPasswordEncoder().matches(old_password, appUser.getPassword())){
                appUser.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(new_password));
                userRepository.save(appUser);
                return "CHANGE PASSWORD SUCCESS";
            }else{
                return CustomErrorMessage.WRONG_PASSWORD.getMessage();
            }
        }else{
            return CustomErrorMessage.NO_PERMISSION.getMessage();
        }
    }

    @Override
    public Map<String, String> updateUser(UpdateUserDTO userDTO, Long u_id) {
        Map<String, String> res = new HashMap<>();
        AppUser appUser = userRepository.findById(u_id).orElseThrow(() -> new ObjectNotFoundException("Not found user", u_id.toString()));
        if(u_id == userDTO.getU_id()){
            appUser.setFullname(userDTO.getFullname());
            appUser.setPhone_number(userDTO.getPhone_number());
            appUser.setUsername(userDTO.getUsername());
            userRepository.save(appUser);
            res.put("status", "200");
            res.put("message", "Update success");
        }else {
            res.put("status", "401");
            res.put("message", CustomErrorMessage.NO_PERMISSION.getMessage());
        }
        return res;
    }

    @Override
    public String count(String by) {
        by = by.toUpperCase();
        String result = "0";
        LocalDate lc = LocalDate.now();
        switch (by) {
            case "YEAR":
                result = userRepository.countByYear(lc.getYear());
                break;
            case "MONTH":
                result = userRepository.countByMonth(lc.getMonth().getValue());
                break;
            case "DAY":
                result = userRepository.countByDay(lc.getDayOfMonth());
                break;
            default:
                result = "Wrong format";
        }
        return result;
    }

    public String forgetPassword(String email) {
        AppUser ap = userRepository.findAppUserByEmail(email).orElseThrow(() -> new ObjectNotFoundException("doesn't exist", email));
//        AppUser ap1 = getUserByEmail(email);
        String password = random();
        ap.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(password));
        userRepository.save(ap);
        emailSender.sendEmail(email, buildEmail.forgotPassword(password, email));
        return "We have sent you a new password via "+email;
    }

    @Override
    public int checkIfUserExistInProject(Long project_id, Long user_id) {
        return userRepository.existsByProjectsId(project_id, user_id);
    }

    public AppUserDTO getUserProfile(Long u_id){
        AppUser appUser = userRepository.findById(u_id).orElseThrow(() -> new ObjectNotFoundException("Not found user", u_id.toString()));
        return new AppUserDTO(appUser.getId(), appUser.getEmail(), appUser.getUsername(), appUser.getStatus(),appUser.getFullname(), appUser.getPhone_number());
    }

    private String random() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }

    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }
}
