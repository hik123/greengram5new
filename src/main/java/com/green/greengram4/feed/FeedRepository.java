package com.green.greengram4.feed;

import com.green.greengram4.common.ProviderTypeEnum;
import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<FeedEntity, Long>, FeedQdslRepository {  // < 연관된 entity    , 테이블의 pk값>
    @EntityGraph(attributePaths = {"userEntity"})                                               //ㄴ인터페이스 다중상속가능
    List<FeedEntity> findAllByUserEntityOrderByIfeedDesc(UserEntity userEntity, Pageable pageable);
                   //이름규칙// UserEntity 부분 >>feedentity의 멤버필드명과 같아야함
}
