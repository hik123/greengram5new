package com.green.greengram4.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

@Data
@Entity
@Table(name = "t_feed_pics")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedPicsEntity extends CreatedAtEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long ifeedPics;

    @ManyToOne
    @JoinColumn(name = "ifeed")
    private FeedEntity feedEntity;


    @Column(length = 2100, nullable = false)
    private String pic;

}
