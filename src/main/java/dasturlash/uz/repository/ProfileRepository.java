package dasturlash.uz.repository;

import dasturlash.uz.entity.ProfileEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Long> {

    Optional<ProfileEntity> findByUsernameAndVisibleTrue(String username);
}
