package me.jordancarlson.dabeetsdeets;

import java.util.List;

import me.jordancarlson.dabeetsdeets.model.Login;
import me.jordancarlson.dabeetsdeets.model.Reading;
import me.jordancarlson.dabeetsdeets.model.Subscription;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jordan on 8/3/17.
 */

public interface DexcomClient {
    @POST("General/LoginSubscriberAccount")
    Call<String> getSessionId(@Body Login login);

    @POST("Subscriber/ListSubscriberAccountSubscriptions")
    Call<List<Subscription>>  getSubscriptions(@Query("sessionId") String sessionId);

    @POST("Subscriber/ReadSubscriptionLatestGlucoseValues")
    Call<List<Reading>> getReadings(@Query("sessionId") String sessionId,
                                    @Query("subscriptionId") String subscriptionId,
                                    @Query("minutes") int minutes,
                                    @Query("maxCount") int maxCount);
}
