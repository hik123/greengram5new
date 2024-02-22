package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_feed")
public class FeedEntity extends BaseEntity{
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ifeed;

    @ManyToOne(fetch = FetchType.LAZY) //lazy지연로딩
    @JoinColumn(name = "iuser", nullable = false) // Definition 안줘도 자동으로
    private UserEntity userEntity;

    @Column(length = 1000)
    private String contents;

    @Column(length = 30)
    private String location;

    //양방향처리?
    @ToString.Exclude //toString 찍었을때 제외 // 제외 안하면 양방향이라 무한루프
    @OneToMany(mappedBy = "feedEntity",cascade = CascadeType.PERSIST) //mappedBy>> 있으면 테이블 생성안됨, cascade 영속성전이
    private List<FeedPicsEntity> feedPicsEntityList = new ArrayList();


    /*@ToString.Exclude
    @OneToMany(mappedBy = "feedEntity")
    private List<FeedFavEntity> feedFavEntityList = new ArrayList();*/

}
