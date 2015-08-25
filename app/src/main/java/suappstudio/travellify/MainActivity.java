package suappstudio.travellify;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.suappstudio.travellify.backend.travelPointApi.model.GeoPt;
import com.suappstudio.travellify.backend.travelPointApi.model.TravelPoint;

import java.io.IOException;

import suappstudio.travellify.utils.CloudUtils;
import suappstudio.travellify.utils.TravellifyException;

public class MainActivity extends AppCompatActivity {

    EditText editLat;
    EditText editLon;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_point);
        CloudUtils.build(this, null);
        editLat = (EditText) findViewById(R.id.et_lat);
        editLon = (EditText) findViewById(R.id.et_lon);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPoint();

            }
        });

    }

    private void insertPoint() {
        TravelPoint point = new TravelPoint();
        GeoPt loc = new GeoPt();
        loc.setLatitude(Float.valueOf(editLat.getText().toString()));
        loc.setLongitude(Float.valueOf(editLon.getText().toString()));
        point.setLocation(new GeoPt());

        point.setInfo("Test");
        new InsertPointTask().execute(point);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


     class InsertPointTask extends AsyncTask<TravelPoint,Void,TravelPoint> {

         @Override
         protected void onPreExecute() {
             progress = ProgressDialog.show(MainActivity.this,"Loading","Wait");
         }

         @Override
        protected TravelPoint doInBackground(TravelPoint... params) {
            try {
                TravelPoint point = CloudUtils.insertPoint(params[0]);
                return point;
            } catch (TravellifyException e) {
                Log.d("CLOUD","Service not created");
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("CLOUD", "IO Exception");
                return null;
            }

        }

         @Override
         protected void onPostExecute(TravelPoint travelPoint) {
             progress.dismiss();
             if (travelPoint != null){
                  Snackbar.make(MainActivity.this.getWindow().getDecorView(),"Point inserted", Snackbar.LENGTH_SHORT).show();
             }
             else {
                 Snackbar.make(MainActivity.this.getWindow().getDecorView(),"Error!!!", Snackbar.LENGTH_SHORT).show();

             }
         }
     }



}
