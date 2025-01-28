package dasturlash.uz.repository;

import dasturlash.uz.entity.ProfileRoleEntity;
import dasturlash.uz.enums.ProfileRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Long> {
    @Transactional
    @Modifying
    void deleteByProfileId(Long profileId);

    List<ProfileRoleEntity> findByProfileId(Long profileId);
}
