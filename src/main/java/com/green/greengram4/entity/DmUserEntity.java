package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "t_dm_user")
public class DmUserEntity {
    @EmbeddedId
    private DmUserIds dmUserIds;

    @ManyToOne
    @MapsId("idm") //FK를 PK로 지정할 때 사용
    @JoinColumn(name = "idm", columnDefinition = "BIGINT UNSIGNED")
    private DmEntity dmEntity;

    @ManyToOne
    @MapsId("iuser")
    @JoinColumn(name = "iuser", columnDefinition = "BIGINT UNSIGNED")
    private UserEntity userEntity;

}
