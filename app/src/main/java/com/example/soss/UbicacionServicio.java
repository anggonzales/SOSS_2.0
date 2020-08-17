package com.example.soss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.PolyUtil;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UbicacionServicio extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mapa;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    String latitudservicio;
    String longitudservicio;
    private double MiLatitude = 0.0;
    private double MiLongitude = 0.0;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    Polyline polyline = null;
    private boolean isContinue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_servicio);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.frgmapa);
        mapFragment.getMapAsync(this);
        request = Volley.newRequestQueue(getApplicationContext());


        if ((ContextCompat.checkSelfPermission(UbicacionServicio.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
        } else {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            } else {
                solicitarPermiso(Manifest.permission.ACCESS_FINE_LOCATION,
                        "Sin el permiso" + " de ubicación no podremos localizarte", 1);
            }
        }

        Bundle extras = getIntent().getExtras();
        latitudservicio =  extras.getString("Latitud");
        longitudservicio = extras.getString("Longitud");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();

        UbicacionServicio.this.getLocation();
    }

    public void getLocation() {
        if (isContinue) {
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        MiLatitude = location.getLatitude();
                        MiLongitude = location.getLongitude();
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                }
            });
        }
    }

    public void ObtenerRuta(View view) {
        Log.i("milocalizacion", "lat:" + MiLatitude + " lon:" + MiLongitude);
        Log.i("localizacionservicio", "lat:" + latitudservicio + " lon:" + longitudservicio);
        String latinicial = String.valueOf(MiLatitude);
        String loninicial = String.valueOf(MiLongitude);
        String latfinal = String.valueOf(latitudservicio);
        String lonfinal = String.valueOf(longitudservicio);
        webServiceObtenerRuta(latinicial, loninicial, latfinal, lonfinal);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-18.006622, -70.246063), 14));
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(false);
            mapa.getUiSettings().setCompassEnabled(true);
        }
    }

    public void DecodePolyline(String encodepolyline) {
        List<LatLng> list = PolyUtil.decode(encodepolyline);
        if (polyline != null) polyline.remove();
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(list).clickable(true);
        polyline = mapa.addPolyline(polylineOptions);
        polyline.setColor(Color.BLUE);
    }

    private void webServiceObtenerRuta(String latitudInicial, String longitudInicial, String latitudFinal, String longitudFinal) {
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + latitudInicial + "," + longitudInicial
                + "&destination=" + latitudFinal + "," + longitudFinal + "&key=AIzaSyBNiVf97pw9rbPMkGlPMDhlnx8UijRDIqQ";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jRoutes = null;
                try {
                    jRoutes = response.getJSONArray("routes");
                    String polyline = "";
                    polyline = (String) ((JSONObject) ((JSONObject) jRoutes.get(0)).get("overview_polyline")).get("points");
                    DecodePolyline(polyline);
                    JSONArray jLegs = null;
                    jLegs = ((JSONObject) jRoutes.get(0)).getJSONArray("legs");
                    String distancia = (String) ((JSONObject) ((JSONObject) jLegs.get(0)).get("distance")).get("text");
                    String tiempo = (String) ((JSONObject) ((JSONObject) jLegs.get(0)).get("duration")).get("text");
                    Log.i("distanciatiempo", "distancia:" + distancia + " tiempo:" + tiempo);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se puede conectar " + error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR: ", error.toString());
            }
        });
        request.add(jsonObjectRequest);
    }

    public void solicitarPermiso(final String permiso, String justificacion, final int codigo) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permiso)) {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Solicitud de permiso");
            dialogo1.setMessage(justificacion);
            dialogo1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    ActivityCompat.requestPermissions(UbicacionServicio.this, new String[]{permiso}, codigo);
                }
            });
            dialogo1.show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permiso}, codigo);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                solicitarPermiso(Manifest.permission.ACCESS_FINE_LOCATION,
                        "Sin el permiso" + " de ubicacion no podremos localizarte", 0);
            }
        }
    }
}
