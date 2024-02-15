package com.green.greengram4.entity;

import com.green.greengram4.common.ProviderTypeEnum;
import com.green.greengram4.common.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity  //무조건 pk적용해야함
@Table(name = "t_user",  uniqueConstraints = {
        @UniqueConstraint(
                columnNames ={"provider_type", "uid"} // 유니크 설정, 컬럼명 적어야됨
        )
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends  BaseEntity {
    @Id //pk줄때 사용
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long iuser;

    @Column(length = 10, name = "provider_type", nullable = false)  // / name ="" db랑 이름다를때 사용/ nullable = false <<not null
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'LOCAL'")
    private ProviderTypeEnum providerType;

    @Column(length = 100, nullable = false)
    private String uid;

    @Column(length = 300, nullable = false)
    private String upw;

    @Column(length = 25, nullable = false)
    private String nm;

    @Column(length = 2100)
    private String pic;

    @Column(length = 2100, name = "firebase_token")
    private String firebaseToken;

    @Column(length = 10, nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'USER'")
    private RoleEnum role;
}
