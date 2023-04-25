package lk.com.ipccw2;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import lk.com.ipccw2.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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


        // Load the f1car.png image from the resources
        Bitmap carBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.f1car);

        // Resize the image to 50x50 pixels
        int width = carBitmap.getWidth();
        int height = carBitmap.getHeight();
        int newWidth = 50;
        int newHeight = 50;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(carBitmap, 0, 0, width, height, matrix, false);

        // Create a BitmapDescriptor object from the resized image
        BitmapDescriptor carIcon = BitmapDescriptorFactory.fromBitmap(resizedBitmap);

        // Add markers for different locations
//        LatLng location1 = new LatLng(6.9271, 79.8612);
//        mMap.addMarker(new MarkerOptions().position(location1).title("Location 1").icon(carIcon));
//
        LatLng location1 = new LatLng(7.00142, 79.9498);
        mMap.addMarker(new MarkerOptions().position(location1).icon(carIcon));


//        LatLng location1 = new LatLng(70.90, 79.95);
//        mMap.addMarker(new MarkerOptions().position(location1).title("F1-003").icon(carIcon));

        // Define the polygon options
//        PolygonOptions polygonOptions = new PolygonOptions()
//                .add(new LatLng(37.35, -122.0))
//                .add(new LatLng(37.45, -122.0))
//                .add(new LatLng(37.45, -121.9))
//                .add(new LatLng(37.35, -121.9))
//                .strokeColor(Color.RED)
//                .fillColor(Color.BLUE);
//
//
//        // Add the polygon to the map
//        mMap.addPolygon(polygonOptions);

        // Move the camera to the first location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1, 12));
    }

}