package dasturlash.uz.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "post")
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "photo_id")
    private String photoId;

    @OneToOne()
    @JoinColumn(name = "photo_id", updatable = false, insertable = false)
    private Attach photo;
}
