package info.kingpes.fcm;

import info.kingpes.fcm.Remote.APIService;
import info.kingpes.fcm.Remote.RetrofitClient;

/**
 * Created by Chau Huynh on 7/24/2018.
 */

public class Common {
    public static String currentToken = "";

    private static String baseUrl = "https://fcm.googleapis.com/";

    public static APIService getFCMClient(){
        return RetrofitClient.getClient(baseUrl).create(APIService.class);
    }
}
