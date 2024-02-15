package com.green.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass //가지고있는 컬럼들 상속?
@EntityListeners(AuditingEntityListener.class) // Auditing 사용하기위해 메인클래스에 @EnableJpaAuditing 추가해줘야함
public class CreatedAtEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
}