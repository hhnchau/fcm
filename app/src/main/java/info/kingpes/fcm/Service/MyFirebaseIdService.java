package info.kingpes.fcm.Service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import info.kingpes.fcm.Common;

/**
 * Created by Chau Huynh on 7/24/2018.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        Common.currentToken = FirebaseInstanceId.getInstance().getToken();

    }
}
