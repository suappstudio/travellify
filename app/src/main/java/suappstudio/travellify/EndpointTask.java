package suappstudio.travellify;

import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.suappstudio.travellify.backend.travelPointApi.TravelPointApi;
import com.suappstudio.travellify.backend.travelPointApi.model.TravelPoint;
import com.suappstudio.travellify.backend.travelPointApi.TravelPointApi.Builder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Android Dragon on 19/08/2015.
 */
public class EndpointTask extends AsyncTask<Void,Void,List<TravelPoint>> {

    private static TravelPointApi service = null;
    private Context context;

    public EndpointTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<TravelPoint> doInBackground(Void... params) {
        if(service == null){
            TravelPointApi.Builder builder= new TravelPointApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                    .setRootUrl(TravelPointApi.DEFAULT_ROOT_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            service = builder.build();

        }
        try {
            return service.list().execute().getItems();
        } catch (IOException e) {return Collections.EMPTY_LIST;

        }
    }
}
