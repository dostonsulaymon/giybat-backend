package dasturlash.uz.service;

import dasturlash.uz.dto.AuthDTO;
import dasturlash.uz.dto.JwtDTO;
import dasturlash.uz.dto.RegistrationDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.entity.ProfileRoleEntity;
import dasturlash.uz.enums.GeneralStatus;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.repository.ProfileRoleRepository;
import dasturlash.uz.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ProfileRoleService profileRoleService;

    @Autowired
    private EmailSendingService emailSendingService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public ResponseEntity<?> registration(RegistrationDTO dto) {

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
            if (optional.isPresent()) {
                ProfileEntity profile = optional.get();

                if(profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)){
                      profileRoleService.deleteRoles(profile.getId());
                      profileRepository.delete(profile);
                      // faqat sms orqali verification qoldi
                }else {
                    throw new AppBadException("User already exists");
                }
            }
        ProfileEntity entity = new ProfileEntity();
            entity.setName(dto.getName());
            entity.setUsername(dto.getUsername());
            entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            entity.setStatus(GeneralStatus.IN_REGISTRATION);
            entity.setVisible(true);
            entity.setCreatedDate(LocalDateTime.now());
            profileRepository.save(entity);

            profileRoleService.create(entity.getId(), ProfileRole.ROLE_USER);

            emailSendingService.sendRegistrationEmail(dto.getUsername(), entity.getId());

            return ResponseEntity.ok(entity);
    }

    public ResponseEntity<String> regVerification(Long profileId) {
        ProfileEntity profile = profileService.getById(profileId);

        if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
            profileRepository.changeStatus(profileId, GeneralStatus.ACTIVE);
            return ResponseEntity.ok("Verification Successful");
        }

        return ResponseEntity.badRequest().body("Verification Failed    ");
    }

    public String login(AuthDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadException("Invalid username or password");
        }

        ProfileEntity profile = optional.get();
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), profile.getPassword())) {
            throw new AppBadException("Invalid password");
        }

        if (profile.getStatus() != GeneralStatus.ACTIVE) {
            throw new RuntimeException("User is not active");
        }

        List<ProfileRoleEntity> roleList = profileRoleRepository.findByProfileId(profile.getId());
        if (roleList.isEmpty()) {
            throw new AppBadException("User has no roles");
        }

        String role = roleList.get(0).getRole().name(); // ROLE_USER, ROLE_ADMIN
        String accessToken = jwtService.encode(dto.getUsername(), role);
        String refreshToken = jwtService.generateRefreshToken(dto.getUsername());

        return accessToken + " " + refreshToken;
    }

    public JwtDTO validateToken(String token) {
        try {
            return jwtService.decode(token);
        } catch (Exception e) {
            throw new AppBadException("Invalid token");
        }
    }

}
