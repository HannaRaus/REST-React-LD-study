package com.ld.model;

import com.ld.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.EnumType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Name can't be empty")
    @Size(min = 3, max = 50, message = "Name must be in range of 3-50 symbols")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 5, max = 50, message = "Password must be in range of 3-50 symbols")
    @Column(name = "password", nullable = false)
    private String password;

    @Pattern(regexp="(^$|[0-9]{10})")
    private String phone;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "send_notification")
    private boolean sendNotification;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole = UserRole.ROLE_USER;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = com.ld.model.Lesson.class)
    @JoinTable(name = "user_favorite_lessons",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    private Set<Lesson> favoriteLessons;

}
