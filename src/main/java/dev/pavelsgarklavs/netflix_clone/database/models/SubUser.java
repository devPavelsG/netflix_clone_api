package dev.pavelsgarklavs.netflix_clone.database.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SUB_USERS")
public class SubUser extends BaseEntity {
    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ToString.Exclude
    private User user;
}
