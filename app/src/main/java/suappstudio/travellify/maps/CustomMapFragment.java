package suappstudio.travellify.maps;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.maps.android.clustering.ClusterManager;
import com.suappstudio.travellify.backend.travelPointApi.model.TravelPoint;

/**
 * Created by Android Dragon on 02/09/2015.
 */
public class CustomMapFragment extends MapFragment{

    private GoogleMap mMap;

    private ViewGroup infoPoint;
    private ViewGroup root;
    private Animation animToBottom;
    private Animation animFromBottom;
    private TextView popUpMappaNome;
    private TextView popUpMappaIndirizzo;
    private TextView popUpMappaAtm;
    private TextView popUpMappaDistanza;





}
