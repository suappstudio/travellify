package suappstudio.travellify.utils;

import android.content.Context;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.suappstudio.travellify.backend.travelPointApi.TravelPointApi;
import com.suappstudio.travellify.backend.travelPointApi.model.TravelPoint;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Android Dragon on 20/08/2015.
 */
public class CloudUtils {

    private final static String TAG = "CloudUtils";

    private static TravelPointApi serviceApiHandler = null;

    public static void build(Context context, String email) {
        serviceApiHandler = buildServiceHandler(context, email);
    }

    /**
     * Build and returns an instance of {@link TravelPointApi}
     *
     * @param context
     * @param email
     * @return
     */
    public static TravelPointApi buildServiceHandler(
            Context context, String email) {
        GoogleAccountCredential credential = null;
        if(email != null){
            credential = GoogleAccountCredential.usingAudience(
                    context, AppConstants.AUDIENCE);
            credential.setSelectedAccountName(email);
        }


        TravelPointApi.Builder builder
                = new TravelPointApi.Builder(
                AppConstants.HTTP_TRANSPORT,
                AppConstants.JSON_FACTORY, credential);

        return builder.build();
    }

    public static TravelPoint insertPoint(TravelPoint point) throws TravellifyException, IOException {
        if(serviceApiHandler == null){
          throw new TravellifyException();
        }
        return serviceApiHandler.insert(point).execute();
    }

    public static List<TravelPoint> retrieveList() throws TravellifyException {
        if(serviceApiHandler == null){
            throw new TravellifyException();
        }
        try {
            return serviceApiHandler.list().execute().getItems();
        } catch (IOException e) {
            return Collections.EMPTY_LIST;
        }
    }
}


