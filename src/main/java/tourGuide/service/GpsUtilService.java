package tourGuide.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import org.springframework.stereotype.Service;
import tourGuide.user.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class GpsUtilService {
    private GpsUtil gpsUtil;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public GpsUtilService() {
        this.gpsUtil = new GpsUtil();
    }

    public void trackUser(User user, TourGuideService tourGuideService) {
        CompletableFuture.supplyAsync(() -> gpsUtil.getUserLocation(user.getUserId()), executor)
                .thenAccept(visitedLocation -> tourGuideService.addUserRewards(user, visitedLocation));
    }

    public List<Attraction> getAttractions() {
        return gpsUtil.getAttractions();
    }

}
