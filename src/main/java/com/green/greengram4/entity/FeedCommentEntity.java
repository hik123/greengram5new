package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.CustomLog;
import lombok.Data;
import org.checkerframework.checker.units.qual.C;

@Data
@Entity
@Table(name = "t_feed_comment")
public class FeedCommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ifeed_comment", columnDefinition = "BIGINT UNSIGNED")
    private Long iFeedComment;

    @ManyToOne //테이블간의 관계설정
    @JoinColumn(name = "ifeed", nullable = false)
    private FeedEntity feedEntity;

    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private UserEntity userEntity;

    @Column(length = 500, nullable = false)
    private String comment;

}