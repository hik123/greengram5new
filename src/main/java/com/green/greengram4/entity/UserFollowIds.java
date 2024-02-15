package com.green.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

//복합키 넣기
@Data
@Embeddable
@EqualsAndHashCode
public class UserFollowIds implements Serializable { // Serializable 안하면 에러
    private Long fromIuser;
    private Long toIuser;
}
