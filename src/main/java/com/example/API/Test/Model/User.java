package com.example.API.Test.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "password_hint", length = 100)
    private String passwordHint;

    @Column(name = "prev_pwd1", length = 255)
    private String prevPwd1;

    @Column(name = "prev_pwd2", length = 255)
    private String prevPwd2;

//    @Column(name = "emp_id")
//    private UUID empId;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", foreignKey = @ForeignKey(name = "fk_users"), unique = true)
    private Employee employee;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "created_by_username", length = 50)
    private String createdByUsername;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "updated_by_username", length = 50)
    private String updatedByUsername;
}
