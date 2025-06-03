package it.unicam.cs.agrotrace.shared.entity.verification;

import it.unicam.cs.agrotrace.shared.entity.content.ContentEntity;
import it.unicam.cs.agrotrace.shared.entity.user.CuratorEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "verifications")
public class VerificationEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private ContentEntity content;

    @ManyToOne
    @JoinColumn(name = "curator_id")
    private CuratorEntity curator;

    @Column(nullable = false, length = 1000)
    private String comments;

}
