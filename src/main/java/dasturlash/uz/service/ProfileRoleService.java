package dasturlash.uz.service;

import dasturlash.uz.entity.ProfileRoleEntity;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfileRoleService {
    @Autowired
    ProfileRoleRepository profileRoleRepository;

    public void create(Long profileId, ProfileRole profileRole) {

        ProfileRoleEntity entity = new ProfileRoleEntity();
        entity.setProfileId(profileId);
        entity.setRole(profileRole);
        entity.setCreateDate(LocalDateTime.now());
        profileRoleRepository.save(entity);
    }

    public void deleteRoles(Long profileId) {
        profileRoleRepository.deleteByProfileId(profileId);
    }
}
