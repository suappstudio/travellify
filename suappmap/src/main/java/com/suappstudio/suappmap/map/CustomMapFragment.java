package com.suappstudio.suappmap.map;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.suappstudio.suappmap.client.RamblerApi;
import com.suappstudio.suappmap.client.RamblerRetrofitClient;
import com.suappstudio.suappmap.model.Event;
import com.suappstudio.suappmap.model.RamblerBean;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import suappstudio.com.suappmap.R;

/**
 * Created by laganas on 08/12/2014.
 */
public class CustomMapFragment extends SupportMapFragment {

    public static final String TAG = "CustomMapFragment";
    private String place,day,time;
    private RamblerBean data;
    private GoogleMap mMap;
    private ClusterManager<Event> mClusterManager;
    private ViewGroup infoPoint;
    private ViewGroup root;
    private Animation animToBottom;
    private Animation animFromBottom;
    private TextView popUpMappaNome;
    private TextView popUpMappaIndirizzo;
    private TextView popUpMappaAtm;
    private TextView popUpMappaDistanza;

    public static CustomMapFragment newInstance(String place, String day, String time) {
        CustomMapFragment fragment = new CustomMapFragment();
        Bundle args = new Bundle();
        args.putString("place",place);
        args.putString("day",day);
        args.putString("time",time);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        place = getArguments().getString("place");
        day = getArguments().getString("day");
        time = getArguments().getString("time");
      //  setUpMapIfNeeded();
        animToBottom = AnimationUtils.loadAnimation(
                getActivity(), R.anim.slide_from_bottom);
        animFromBottom = AnimationUtils.loadAnimation(
                getActivity(), R.anim.slide_from_up_to_bottom);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);
        return root;
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = this.getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        mClusterManager = new ClusterManager<Event>(getActivity(), mMap);
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<Event>() {
            @Override
            public boolean onClusterItemClick(Event eventItem) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(eventItem.getPosition())
                        .tilt(60)
                        .zoom(16)
                        .build();
                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
                if (infoPoint != null){
                    ((ViewGroup) root).removeView(infoPoint);
                }
                infoPoint = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.info_window, null);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.BOTTOM;
                infoPoint.setLayoutParams(params);
                infoPoint.setVisibility(View.VISIBLE);
                popUpMappaNome = ((TextView) infoPoint.findViewById(R.id.tv_popup_mappa_nome));
                popUpMappaIndirizzo = ((TextView) infoPoint.findViewById(R.id.tv_popup_mappa_indirizzo));
                popUpMappaAtm = ((TextView) infoPoint.findViewById(R.id.tv_popup_mappa_atm));
                popUpMappaDistanza = ((TextView) infoPoint.findViewById(R.id.tv_distance));

                infoPoint.startAnimation(animFromBottom);


                popUpMappaNome.setText(eventItem.getName());
                popUpMappaIndirizzo.setText(eventItem.getPeople());
                popUpMappaAtm.setText("INFO");
                popUpMappaDistanza.setText("INFO");

                ((ViewGroup) root).addView(infoPoint);
                return true;
            }
        });
        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            if (infoPoint != null && infoPoint.getVisibility() == View.VISIBLE){
                infoPoint.startAnimation(animToBottom);
                infoPoint.setVisibility(View.GONE);
            }
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(mMap.getCameraPosition().target)
                    .tilt(0)
                    .zoom(15)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    });
        requestData(place,day,time);



    }

    public void requestData(String place,String day, String time) {
        RamblerApi api = RamblerRetrofitClient.init();

        api.getEvents(place,day,time,new Callback<RamblerBean>() {
            @Override
            public void success(RamblerBean ramblerBean, Response response) {
                data = ramblerBean;
                if(data != null) {
                    placeMarker(data);
                }



            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(),"Errore",Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpMapIfNeeded();
    }


    private void placeMarker(RamblerBean data){
        mClusterManager.clearItems();
        if(data.getEvents() != null)
        for (Event p : data.getEvents()) {
            mClusterManager.addItem(p);
        }

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(Double.parseDouble(data.getLatitude()), Double.parseDouble(data.getLongitude())), data.getZoom()-2, 0, 0)));
    }
}
