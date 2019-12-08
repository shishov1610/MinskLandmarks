package com.example.minsklandmarks.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PointF;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.minsklandmarks.MyApp;
import com.example.minsklandmarks.R;
import com.example.minsklandmarks.databaseHelper.DatabaseConnect;
import com.example.minsklandmarks.databaseService.DatabaseServiceImpl;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.geometry.SubpolylineHelper;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.PolylineMapObject;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.transport.TransportFactory;
import com.yandex.mapkit.transport.masstransit.MasstransitOptions;
import com.yandex.mapkit.transport.masstransit.MasstransitRouter;
import com.yandex.mapkit.transport.masstransit.Route;
import com.yandex.mapkit.transport.masstransit.Section;
import com.yandex.mapkit.transport.masstransit.SectionMetadata;
import com.yandex.mapkit.transport.masstransit.Session;
import com.yandex.mapkit.transport.masstransit.TimeOptions;
import com.yandex.mapkit.transport.masstransit.Transport;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ViewMapActivity extends AppCompatActivity implements View.OnClickListener, UserLocationObjectListener, Session.RouteListener {

    private final String MAPKIT_API_KEY = "a1f1557f-53d8-4fb3-b9bb-c472e49839b1";

    private MapView mapView;
    private UserLocationLayer userLocationLayer;
    private MapObjectCollection mapObjects;
    private MasstransitRouter mtRouter;
    private CameraPosition cameraPosition;
    Point myLocation;

    PlacemarkMapObject placemark;
    ImageButton backButton;
    String coordinate;

    int key = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        TransportFactory.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);

        Double coordinateX;
        Double coordinateY;

        Bundle arguments = getIntent().getExtras();
        if( arguments != null){
            key = arguments.getInt("key");
            coordinate = arguments.getString("coordinates");
        }

        mapView = findViewById(R.id.mapview);
        mapView.getMap().setRotateGesturesEnabled(true);

        cameraPosition = new CameraPosition(new Point(0, 0),14,0,0);

        mapView.getMap().move(cameraPosition);
        MapKit mapKit = MapKitFactory.getInstance();
        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());

        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);

        userLocationLayer.setObjectListener(this);


        if (key == 1) {

            String[] str = coordinate.split(",");
            coordinateX = Double.valueOf(str[0]);
            coordinateY = Double.valueOf(str[1]);

            mapObjects = mapView.getMap().getMapObjects().addCollection();

            MasstransitOptions options = new MasstransitOptions(
                    new ArrayList<String>(),
                    new ArrayList<String>(),
                    new TimeOptions());

            List<RequestPoint> points = new ArrayList<>();

//            Log.w("MY_TAG", myLocation.getLatitude()+"  "+ myLocation.getLongitude());
//            myLocation = userLocationLayer.cameraPosition().getTarget();
            points.add(new RequestPoint(new Point(53.911603,27.595791), RequestPointType.WAYPOINT, null));
            points.add(new RequestPoint(new Point(coordinateX,coordinateY), RequestPointType.WAYPOINT, null));
            mtRouter = TransportFactory.getInstance().createMasstransitRouter();
            mtRouter.requestRoutes(points, options, this);

        }

        else{
            DatabaseConnect dbc = DatabaseConnect.getInstance();

            DatabaseServiceImpl repository = new DatabaseServiceImpl(dbc.getDb());
            final ArrayList<String> coordinates = repository.getAllCoordinates();



            for( int i = 0; i<coordinates.size(); i++){
                String[] str = coordinates.get(i).split(",");
                coordinateX = Double.valueOf(str[0]);
                coordinateY = Double.valueOf(str[1]);
                placemark =  mapView.getMap().getMapObjects().addPlacemark(new Point(coordinateX,coordinateY));
                placemark.setIcon(ImageProvider.fromResource(this, R.drawable.placemark));

            }

        }
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.backButton:
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onObjectAdded(UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.5)),
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.83)));

//        Log.w("MY_TAG", (float)(mapView.getWidth() * 0.5)+"  "+ (float)(mapView.getHeight() * 0.83));

        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, R.drawable.user_arrow));

        CompositeIcon pinIcon = userLocationView.getPin().useCompositeIcon();

        pinIcon.setIcon(
                "pin",
                ImageProvider.fromResource(this, R.drawable.search_result),
                new IconStyle().setAnchor(new PointF(0.5f, 0.5f))
                        .setRotationType(RotationType.ROTATE)
                        .setZIndex(1f)
                        .setScale(0.5f)
        );

        userLocationView.getAccuracyCircle().setStrokeColor(Color.BLUE);
    }

    @Override
    public void onObjectRemoved(UserLocationView view) {
    }

    @Override
    public void onObjectUpdated(UserLocationView view, ObjectEvent event) {
    }

    @Override
    public void onMasstransitRoutes(List<Route> routes) {
        if (routes.size() > 0) {
            for (Section section : routes.get(0).getSections()) {
                drawSection( section.getMetadata().getData(), SubpolylineHelper.subpolyline(
                                routes.get(0).getGeometry(), section.getGeometry()));
            }
        }
    }


    @Override
    public void onMasstransitRoutesError(@NonNull Error error) {
        String errorMessage = getString(R.string.unknown_error_message);
        if (error instanceof RemoteError) {
            errorMessage = getString(R.string.remote_error_message);
        } else if (error instanceof NetworkError) {
            errorMessage = getString(R.string.network_error_message);
        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void drawSection(SectionMetadata.SectionData data,
                             Polyline geometry) {
        PolylineMapObject polylineMapObject = mapObjects.addPolyline(geometry);
        if (data.getTransports() != null) {
            for (Transport transport : data.getTransports()) {
                if (transport.getLine().getStyle() != null) {
                    polylineMapObject.setStrokeColor(
                            transport.getLine().getStyle().getColor() | 0xFF000000
                    );
                    return;
                }
            }
            HashSet<String> knownVehicleTypes = new HashSet<>();
            knownVehicleTypes.add("bus");
            knownVehicleTypes.add("tramway");
            for (Transport transport : data.getTransports()) {
                String sectionVehicleType = getVehicleType(transport, knownVehicleTypes);
                if (sectionVehicleType.equals("bus")) {
                    polylineMapObject.setStrokeColor(0xFF00FF00);  // Green
                    return;
                } else if (sectionVehicleType.equals("tramway")) {
                    polylineMapObject.setStrokeColor(0xFFFF0000);  // Red
                    return;
                }
            }
            polylineMapObject.setStrokeColor(0xFF0000FF);  // Blue
        } else {
            polylineMapObject.setStrokeColor(0xFF000000);  // Black
        }
    }

    private String getVehicleType(Transport transport, HashSet<String> knownVehicleTypes) {

        for (String type : transport.getLine().getVehicleTypes()) {
            if (knownVehicleTypes.contains(type)) {
                return type;
            }
        }
        return null;
    }
}
