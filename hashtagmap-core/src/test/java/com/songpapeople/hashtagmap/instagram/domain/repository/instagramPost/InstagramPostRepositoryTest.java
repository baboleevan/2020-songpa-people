package com.songpapeople.hashtagmap.instagram.domain.repository.instagramPost;

import com.songpapeople.hashtagmap.instagram.domain.model.Instagram;
import com.songpapeople.hashtagmap.instagram.domain.model.InstagramPost;
import com.songpapeople.hashtagmap.instagram.domain.repository.InstagramRepository;
import com.songpapeople.hashtagmap.place.domain.model.Place;
import com.songpapeople.hashtagmap.place.domain.repository.PlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
class InstagramPostRepositoryTest {
    private Place place;
    private Instagram instagram;

    @Autowired
    private InstagramPostRepository instagramPostRepository;

    @Autowired
    private InstagramRepository instagramRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @BeforeEach
    void setUp() {
        place = placeRepository.save(Place.builder().id(1L).build());
        instagram = instagramRepository.save(Instagram.builder()
                .place(place)
                .build());
    }

    @DisplayName("인스타그램 아이디로 인스타그램 post들을 가져오는 기능 테스트")
    @Test
    void findAllByInstagramId() {
        List<InstagramPost> instagramPosts = IntStream.rangeClosed(1, 9).boxed()
                .map(this::createInstagramPost)
                .collect(Collectors.toList());
        instagramPostRepository.saveAll(instagramPosts);

//        assertThat(instagramPostRepository.findAllByInstagramId(1L)).hasSize(9);
    }

    private InstagramPost createInstagramPost(Integer number) {
        String url = String.valueOf(number);
        return InstagramPost.builder()
                .instagramId(instagram.getId())
                .imageUrl(url)
                .postUrl(url)
                .build();
    }
}
