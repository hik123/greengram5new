package com.green.greengram4.user;

import com.green.greengram4.common.ProviderTypeEnum;
import com.green.greengram4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {  // <   , pk값>
    Optional<UserEntity> findByProviderTypeAndUid(ProviderTypeEnum providerType, String uid); //repository메소드 //쿼리문 만들어줌

}
