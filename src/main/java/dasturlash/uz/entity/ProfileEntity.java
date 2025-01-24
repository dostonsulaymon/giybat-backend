package dasturlash.uz.entity;

import dasturlash.uz.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "profile")
@Entity
@Getter
@Setter
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String username;

    @Column(name = "username")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status; //ACTIVE, BLOCK

    @Column(name = "visible")
    private boolean visible = Boolean.TRUE;

    @Column(name = "created_date")
    private LocalDateTime createdDate;


    // ROLE, USER, ADMIN

//    @OneToMany(mappedBy = "profile")
//    private List<ProfileRoleEntity> rolesList;




}
