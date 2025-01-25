package dasturlash.uz.service;

import dasturlash.uz.dto.RegistrationDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.GeneralStatus;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String registration(RegistrationDTO dto) {

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
            if (optional.isPresent()) {
                ProfileEntity profile = optional.get();
                if(profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)){
                      profileRepository.delete(profile);
                      //2 sen sms/ email for verificate
                }
                throw new AppBadException("User already exists");
            }
        ProfileEntity entity = new ProfileEntity();
            entity.setName(dto.getName());
            entity.setUsername(dto.getUsername());
            entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            entity.setStatus(GeneralStatus.IN_REGISTRATION);
            entity.setVisible(true);
            entity.setCreatedDate(LocalDateTime.now());
            profileRepository.save(entity);

        return null;
    }
}
