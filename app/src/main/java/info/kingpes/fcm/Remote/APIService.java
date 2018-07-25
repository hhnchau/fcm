package info.kingpes.fcm.Remote;

import info.kingpes.fcm.model.MyResponse;
import info.kingpes.fcm.model.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Chau Huynh on 7/24/2018.
 */

public interface APIService {

    @Headers({
            "Content-Type:application/json",
            "Authorization: key=AAAA0lG-NFc:APA91bG-maHrUb0tOmJo90o7t47LoiuXTyWlbKOUObwqxaVED6QcKe2jAt2v1jmvbeLY9WmdAq8EnWiNGckyht6ZpJwktWEbR9nhabu5GuMdVPuKRlbEKArdESxJEeoZrY0FsripgldgsPSfQmP8jJGaM5iesqu-EA"
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
