package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedFavEntity;
import com.green.greengram4.entity.FeedFavIds;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedFavRepository extends JpaRepository<FeedFavEntity, FeedFavIds> {

}
