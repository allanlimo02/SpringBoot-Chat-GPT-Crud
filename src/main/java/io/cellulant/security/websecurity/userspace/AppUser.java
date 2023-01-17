package io.cellulant.security.websecurity.userspace;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity @Table(name = "app-user")
public class AppUser {
    @Id
    Long userId;
    String userName;
    String userEmail;
    String password;
}
