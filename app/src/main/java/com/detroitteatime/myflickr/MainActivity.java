package com.detroitteatime.myflickr;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    ProgressBar progress;
    Button refresh;
    ArrayList<FlickrPhoto> photos;
    private double latitude;
    private double longitude;
    LocationManager locationManager;
    LocationListener locationListener;
    String lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        refresh = (Button) findViewById(R.id.refesh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoadTask(MainActivity.this);
            }
        });
        getLocation();
        startLoadTask(this);



    }

    public void startLoadTask(Context c){
        if (isOnline()) {
            LoadPhotos task = new LoadPhotos();
            task.execute();
        } else {
            Toast.makeText(c, "Not online", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    public void showList() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, new FlickerFragment());
        ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getLocation(){

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(MainActivity.this, "latitude: " + latitude + "longitude: " + longitude, Toast.LENGTH_LONG).show();
                startLoadTask(MainActivity.this);
                String[] str = new String[2];

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {}
        };

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);


    }

    private class LoadPhotos extends AsyncTask<String, Long, Long> {
        HttpURLConnection connection = null;
        ArrayList<FlickrPhoto> photos;

        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected Long doInBackground(String... strings) {
            lon = String.valueOf(longitude);
            lat = String.valueOf(latitude);

            String dataString = "http://api.openweathermap.org/data/2.5/forecast/daily?lat="+lat+"&lon="+lon+"&cnt=10&mode=json";

//            api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json.
            Log.i(Constants.TAG, dataString);
            try {
                URL dataUrl = new URL(dataString);
                connection = (HttpURLConnection) dataUrl.openConnection();
                connection.connect();
                int status = connection.getResponseCode();
                Log.d("TAG", "status " + status);
                //if it is successful
                if (status == 200) {
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String responseString;
                    StringBuilder sb = new StringBuilder();

                    while ((responseString = reader.readLine()) != null) {
                        sb = sb.append(responseString);
                    }
                    String photoData = sb.toString();

                    photos = FlickrPhoto.makeDayList(photoData);
                    Log.d("wtf", photoData);

                    //String description = photos.get(1).getCity();
                    //TextView title = (TextView) findViewById(R.id.textView9);
                    //title.setText(description);
                    return 0l;
                } else {
                    return 1l;
                }
            } catch (MalformedURLException e) {
                Log.i(Constants.TAG, "Malformed Url");
                e.printStackTrace();
                return 1l;
            } catch (IOException e) {
                e.printStackTrace();
                return 1l;
            } catch (JSONException e) {
                e.printStackTrace();
                return 1l;
            } finally {
                if (connection != null)
                    connection.disconnect();
            }

        }


        @Override
        protected void onPostExecute(Long result) {
            if (result != 1l) {
                DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
                dbHelper.clearTable();
                dbHelper.addRows(photos);
                dbHelper.close();
                showList();
                Log.i("nothing", "hello how are you doing");

                String description = photos.get(3).getCity();
                String country = photos.get(3).getCountry();

                TextView title = (TextView) findViewById(R.id.textView9);

                title.setText("City :  " + description +"   Country : "+ country);
                Log.i("supposedly a city", (String)photos.get(3).getCity());

            } else {
                Toast.makeText(getApplicationContext(), "AsyncTask didn't complete", Toast.LENGTH_LONG).show();
            }
            progress.setVisibility(View.GONE);
        }
    }


}
