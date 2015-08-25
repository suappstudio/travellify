package suappstudio.travellify;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.suappstudio.travellify.backend.travelPointApi.model.TravelPoint;

import java.util.Collections;
import java.util.List;

import suappstudio.travellify.utils.CloudUtils;
import suappstudio.travellify.utils.TravellifyException;

/**
 * Created by Android Dragon on 23/08/2015.
 */
public class TravelPointLoader extends AsyncTaskLoader<List<TravelPoint>> {

    public TravelPointLoader(Context context) {
        super(context);
    }

    @Override
    public List<TravelPoint> loadInBackground() {
        try {
            return CloudUtils.retrieveList();
        } catch (TravellifyException e) {
            return Collections.EMPTY_LIST;
        }

    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }


}
