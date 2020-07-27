package com.songpapeople.hashtagmap.scheduler;

import com.songpapeople.hashtagmap.crawler.InstagramCrawler;
import com.songpapeople.hashtagmap.exception.CrawlerException;
import com.songpapeople.hashtagmap.exception.InstagramSchedulerException;
import com.songpapeople.hashtagmap.exception.InstagramSchedulerExceptionStatus;
import com.songpapeople.hashtagmap.place.domain.model.Place;

public class CrawlerWithProxy {
    private static final int MAX_TRY_COUNT = 3;

    private final ProxySetter proxySetter;
    private final InstagramCrawler instagramCrawler;

    public CrawlerWithProxy(ProxySetter proxySetter, InstagramCrawler instagramCrawler) {
        this.proxySetter = proxySetter;
        this.instagramCrawler = instagramCrawler;
    }

    public CrawlingResult instagramCrawling(Place place, int tryCount) {
        if (tryCount > MAX_TRY_COUNT) {
            throw new InstagramSchedulerException(InstagramSchedulerExceptionStatus.NON_EXIST_HASHTAG);
        }
        try {
            proxySetter.set();
            return new CrawlingResult(instagramCrawler.crawling(place.getPlaceName()), place);
        } catch (CrawlerException e) {
            return instagramCrawling(place, tryCount + 1);
        }
    }
}
