package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity //entity주면 pk 무조건 줘야함
@Table(name = "dm")
public class DmEntity extends CreatedAtEntity {
    @Id         //모든 레코드가 구분이 되어야 해서 테이블에 pk 무조건있어야함
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")  //타입 무조건 넣어야함 + UNSIGNED
    private Long idm;

    @Column(length = 2000, name = "last_msg")
    private String lastMsg;

    @LastModifiedDate       //자바에서 날짜적용
    @Column(name = "last_msg_at")
    private LocalDateTime lastMsgAt;
}
