package dasturlash.uz.entity;

import dasturlash.uz.enums.ProfileRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "profile_role")
@Entity
@Getter
@Setter
public class ProfileRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "profile_id")
    private Long profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false   )
    private ProfileEntity profile;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ProfileRole role;

    @Column(name = "created_date")
    private LocalDateTime createdAt;
}
