package com.example.xiaoyuliang.map;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int room = 0;
    private ArrayList<HashMap<String, String>> locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sjsu = new LatLng(37.33642715101153, -121.8819272518158);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(19));
        mMap.addMarker(new MarkerOptions().position(sjsu).title("SJSU Engineering Building"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sjsu));

        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.engr_bldg_f3);

         LatLng southWest = new LatLng(37.33639302952647, -121.88194870948792);
        LatLng northEast = new LatLng(37.33783251621043, -121.88155978918076);

//        LatLng sw = new LatLng(37.33642715101153, -121.8819272518158);
//        LatLng ne = new LatLng(37.33773228617937, -121.88150882720947);
//        LatLng nw = new LatLng(37.33727165282733, -121.88254952430725);
//        LatLng se = new LatLng(37.336904850174776, -121.88091337680817);
//
//        LatLngBounds latLngBounds = new LatLngBounds(sw, ne).including(nw).including(se);
//        //LatLngBounds latLngBounds = new LatLngBounds(southWest, northEast);
        GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions();
//        groundOverlayOptions.positionFromBounds(latLngBounds);

        LatLng centerLocation = new LatLng(37.337066925986306, -121.88179314136505);

        groundOverlayOptions.image(bitmapDescriptor);
        groundOverlayOptions.position(centerLocation, 140f, 140f);
        groundOverlayOptions.bearing(-31);

        groundOverlayOptions.image(bitmapDescriptor);
        groundOverlayOptions.transparency(0.2f);
        mMap.addGroundOverlay(groundOverlayOptions);

        //LatLng room278 = new LatLng(37.33675983467852, -121.88165943832397);
        //mMap.addMarker(new MarkerOptions().position(room278).title("Room 390"));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
    public void location() {

    }
    public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to call police?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                call();
                                Toast.makeText(MapsActivity.this,"Call Police",Toast.LENGTH_LONG).show();
                            }

                            private void call() {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:0377778888"));
                                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED) {
                                    return;
                                }
                                startActivity(callIntent);
                            }

                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MapsActivity.this,"Cancel",Toast.LENGTH_LONG).show();
            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
