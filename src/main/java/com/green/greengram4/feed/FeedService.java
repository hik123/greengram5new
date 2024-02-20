package com.green.greengram4.feed;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.MyFileUtils;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.FeedFavIds;
import com.green.greengram4.entity.FeedPicsEntity;
import com.green.greengram4.entity.UserEntity;
import com.green.greengram4.exception.FeedErrorCode;
import com.green.greengram4.exception.RestApiException;
import com.green.greengram4.feed.model.*;
import com.green.greengram4.security.AuthenticationFacade;
import com.green.greengram4.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final FeedPicsMapper picsMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commentMapper;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final FeedCommentRepository commentRepository;
    private final FeedFavRepository feedFavRepository;
    private final AuthenticationFacade authenticationFacade;
    private final MyFileUtils myFileUtils;

    @Transactional
    public FeedPicsInsDto postFeed(FeedInsDto dto) {
        if(dto.getPics() == null) {
            throw new RestApiException(FeedErrorCode.PICS_MORE_THEN_ONE);
        }

        UserEntity userEntity = userRepository.getReferenceById((long)authenticationFacade.getLoginUserPk());
        //UserEntity userEntity = new UserEntity();
        //userEntity.setIuser((long)authenticationFacade.getLoginUserPk());

        FeedEntity feedEntity = new FeedEntity();
        feedEntity.setUserEntity(userEntity);
        feedEntity.setContents(dto.getContents());
        feedEntity.setLocation(dto.getLocation());
        feedRepository.save(feedEntity);

        //영속성 전이?

        String target = "/feed/" + dto.getIfeed();

        FeedPicsInsDto pDto = new FeedPicsInsDto();
        pDto.setIfeed(feedEntity.getIfeed().intValue());
        for(MultipartFile file : dto.getPics()) {
            String saveFileNm = myFileUtils.transferTo(file, target);// 파일명넘어옴
            pDto.getPics().add(saveFileNm); //넘어온 파일명 저장
        }                               //사진 선택갯수 마다 for문 돔
        List<FeedPicsEntity> feedPicsEntityList = pDto.getPics()
                                                        .stream()
                                                        .map(item -> FeedPicsEntity.builder()
                                                                .pic(item)
                                                                .feedEntity(feedEntity)
                                                                .build()
                                                        ).collect(Collectors.toList()); // pics 저장하기위한
        feedEntity.getFeedPicsEntityList().addAll(feedPicsEntityList);

        return pDto;
    }

    /*@Transactional
    public FeedPicsInsDto postFeed(FeedInsDto dto) {
        if(dto.getPics() == null) {
            throw new RestApiException(FeedErrorCode.PICS_MORE_THEN_ONE);
        }

        dto.setIuser(authenticationFacade.getLoginUserPk());
        log.info("dto: {}", dto);
        int feedAffectedRows = mapper.insFeed(dto);
        String target = "/feed/" + dto.getIfeed();

        FeedPicsInsDto pDto = new FeedPicsInsDto();
        pDto.setIfeed(dto.getIfeed());
        for(MultipartFile file : dto.getPics()) {
            String saveFileNm = myFileUtils.transferTo(file, target);
            pDto.getPics().add(saveFileNm);
        }
        int feedPicsAffectedRows = picsMapper.insFeedPics(pDto);
        return pDto;
    }*/


    @Transactional
    public List<FeedSelVo> getFeedAll(FeedSelDto dto, Pageable pageable) {
        List<FeedEntity> feedEntityList = null;
        if(dto.getIsFavList() == 0 && dto.getTargetIuser() >0) {
            //feedRepository.findAllByUserEntityOrderByIfeedDesc(null, pageable)
            UserEntity userEntity = new UserEntity();
            userEntity.setIuser((long)dto.getTargetIuser());

            feedEntityList = feedRepository.findAllByUserEntityOrderByIfeedDesc(userEntity, pageable);

        }

        return feedEntityList == null
                ? new ArrayList()
                : feedEntityList.stream().map(item -> {

                    FeedFavIds feedFavIds = new FeedFavIds();
                    feedFavIds.setIuser((long)authenticationFacade.getLoginUserPk());
                    feedFavIds.setIfeed(item.getIfeed());
                    int isFav = feedFavRepository.findById(feedFavIds).isPresent() ? 1 : 0;

                    List<FeedPicsEntity> picList = item.getFeedPicsEntityList();
                       List<String> pic = picList.stream().map( picss -> picss.getPic()).collect(Collectors.toList());

                    List<FeedCommentSelVo> cmtList = commentRepository.findAllTop4ByFeedEntity(item)
                                                                    .stream()
                                                                    .map(cmt ->
                                    FeedCommentSelVo.builder()
                                            .ifeedComment(cmt.getIFeedComment().intValue())
                                            .comment(cmt.getComment())
                                            .createdAt(cmt.getCreatedAt().toString())
                                            .writerIuser(cmt.getUserEntity().getIuser().intValue())
                                            .writerNm(cmt.getUserEntity().getNm())
                                            .writerPic(cmt.getUserEntity().getPic())
                                            .build()
                    ).collect(Collectors.toList());
            //cmtList가 4개 이면 > isMoreComment =1, cmtList에 마지막 하나는 제거
            // else > isMoreComment = 0, cmtList는 변화가 없다.


                    UserEntity userEntity = item.getUserEntity();
                    return FeedSelVo.builder()
                            .ifeed(item.getIfeed().intValue())
                            .contents(item.getContents())
                            .location(item.getLocation())
                            .createdAt(item.getCreatedAt().toString())
                            .writerIuser(item.getUserEntity().getIuser().intValue())
                            .writerNm(userEntity.getNm())
                            .writerPic(userEntity.getPic())
                            .isMoreComment(cmtList.size() == 4 ? 1 : 0)
                            .pics(pic)
                            .comments(cmtList.size() == 4 ? cmtList.subList(0, 3) : cmtList)
                            .isFav(isFav)
                            .build();
                }
        ).collect(Collectors.toList());


        /*System.out.println("!!!!!");
        list = mapper.selFeedAll(dto);

        FeedCommentSelDto fcDto = new FeedCommentSelDto();
        fcDto.setStartIdx(0);
        fcDto.setRowCount(Const.FEED_COMMENT_FIRST_CNT);

        for(FeedSelVo vo : list) {
            List<String> pics = picsMapper.selFeedPicsAll(vo.getIfeed());
            vo.setPics(pics);

            fcDto.setIfeed(vo.getIfeed());
            List<FeedCommentSelVo> comments = commentMapper.selFeedCommentAll(fcDto);
            vo.setComments(comments);

            if(comments.size() == Const.FEED_COMMENT_FIRST_CNT) {
                vo.setIsMoreComment(1);
                comments.remove(comments.size() - 1);
            }
        }
        return list;*/
    }

    /*public List<FeedSelVo> getFeedAll(FeedSelDto dto) {
        System.out.println("!!!!!");
        List<FeedSelVo> list = mapper.selFeedAll(dto);

        FeedCommentSelDto fcDto = new FeedCommentSelDto();
        fcDto.setStartIdx(0);
        fcDto.setRowCount(Const.FEED_COMMENT_FIRST_CNT);

        for(FeedSelVo vo : list) {
            List<String> pics = picsMapper.selFeedPicsAll(vo.getIfeed());
            vo.setPics(pics);

            fcDto.setIfeed(vo.getIfeed());
            List<FeedCommentSelVo> comments = commentMapper.selFeedCommentAll(fcDto);
            vo.setComments(comments);

            if(comments.size() == Const.FEED_COMMENT_FIRST_CNT) {
                vo.setIsMoreComment(1);
                comments.remove(comments.size() - 1);
            }
        }
        return list;
    }*/

    public ResVo delFeed(FeedDelDto dto) {
        //1 이미지
        int picsAffectedRows = picsMapper.delFeedPicsAll(dto);
        if(picsAffectedRows == 0) {
            return new ResVo(Const.FAIL);
        }

        //2 좋아요
        int favAffectedRows = favMapper.delFeedFavAll(dto);

        //3 댓글
        int commentAffectedRows = commentMapper.delFeedCommentAll(dto);

        //4 피드
        int feedAffectedRows = mapper.delFeed(dto);
        return new ResVo(Const.SUCCESS);
    }

    //--------------- t_feed_fav
    public ResVo toggleFeedFav(FeedFavDto dto) {
        //ResVo - result값은 삭제했을 시 (좋아요 취소) 0, 등록했을 시 (좋아요 처리) 1
        int delAffectedRows = favMapper.delFeedFav(dto);
        if(delAffectedRows == 1) {
            return new ResVo(Const.FEED_FAV_DEL);
        }
        int insAffectedRows = favMapper.insFeedFav(dto);
        return new ResVo(Const.FEED_FAV_ADD);
    }
}
