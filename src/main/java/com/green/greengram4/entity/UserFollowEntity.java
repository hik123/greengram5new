package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_user_follow")
public class UserFollowEntity extends CreatedAtEntity {
    @EmbeddedId //복합키
    private UserFollowIds userFollowIds;

    @ManyToOne//  1:다           //외래키 부분
    @MapsId("fromIuser")
    @JoinColumn(name = "from_iuser", columnDefinition = "BIGINT UNSIGNED") //name = 컬럼명, 외래키적용 위해 타입 맞춤
    private UserEntity fromUserEntity; //

    @ManyToOne // UserEntity테이블과 관계설정?
    @MapsId("toIuser")
    @JoinColumn(name = "to_iuser", columnDefinition = "BIGINT UNSIGNED") //외래키
    private UserEntity toUserEntity;        // 변수명 아무거나상관없
}
