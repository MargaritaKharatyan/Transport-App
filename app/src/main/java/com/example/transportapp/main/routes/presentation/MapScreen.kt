package com.example.transportapp.main.routes.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.transportapp.R
import com.example.transportapp.main.routes.presentation.components.createCircleMarker
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingRouterType
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import org.koin.androidx.compose.koinViewModel

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    routeId: Int,
    viewModel: MapViewModel = koinViewModel(),
    navigateToRoutes: () -> Unit,
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val mapObjects: MapObjectCollection =
        remember { mapView.mapWindow.map.mapObjects.addCollection() }
    val stops by viewModel.routePoints.collectAsState()

    val userLocationLayer = remember {
        MapKitFactory.getInstance().createUserLocationLayer(mapView.mapWindow)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.values.all { it }
        if (granted) {
            userLocationLayer.isVisible = true
            userLocationLayer.isHeadingEnabled = true
        }
    }

    LaunchedEffect(Unit) {
        val fineLocation =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocation =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (fineLocation == PackageManager.PERMISSION_GRANTED && coarseLocation == PackageManager.PERMISSION_GRANTED) {
            userLocationLayer.isVisible = true
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    DisposableEffect(mapView) {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        onDispose {
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
        }
    }

    LaunchedEffect(routeId) {
        viewModel.fetchRouteById(routeId)
    }

    LaunchedEffect(stops) {
        if (stops.isEmpty()) return@LaunchedEffect
        mapObjects.clear()

        val points = mutableListOf<Point>()

        stops.forEach { stop ->
            stop.firstStopCoords?.let {
                points.add(Point(it.latitude, it.longitude))
            }

            stop.intermediateStops.forEach { geoPoint ->
                points.add(Point(geoPoint.latitude, geoPoint.longitude))
            }

            stop.lastStopCoords?.let {
                points.add(Point(it.latitude, it.longitude))
            }
        }

        points.forEachIndexed { index, point ->
            val placemark = mapObjects.addPlacemark(point)
            val isEdge = (index == 0 || index == points.size - 1)

            val markerColor =
                if (isEdge) android.graphics.Color.BLUE else android.graphics.Color.WHITE
            val markerRadius = if (isEdge) 20f else 14f

            val imageProvider = createCircleMarker(markerColor, markerRadius)
            placemark.setIcon(imageProvider)

            placemark.zIndex = if (isEdge) 10f else 5f
        }

        if (points.size >= 2) {
            val drivingRouter =
                DirectionsFactory.getInstance().createDrivingRouter(DrivingRouterType.COMBINED)

            val requestPoints: List<RequestPoint> = points.mapIndexed { index, point ->
                RequestPoint(
                    point,
                    RequestPointType.WAYPOINT,
                    null,
                    null
                )
            }

            drivingRouter.requestRoutes(
                requestPoints,
                DrivingOptions(),
                VehicleOptions(),
                object : DrivingSession.DrivingRouteListener {
                    override fun onDrivingRoutes(routes: MutableList<DrivingRoute>) {
                        routes.firstOrNull()?.let { route ->
                            mapObjects.addPolyline(route.geometry)

                            val geometry =
                                com.yandex.mapkit.geometry.Geometry.fromPolyline(route.geometry)
                            val cameraPosition =
                                mapView.mapWindow.map.cameraPosition(geometry, 0f, 0f, null)

                            mapView.mapWindow.map.move(
                                cameraPosition,
                                Animation(Animation.Type.SMOOTH, 1.5f),
                                null
                            )
                        }
                    }

                    override fun onDrivingRoutesError(error: com.yandex.runtime.Error) {
                    }
                }
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {

        AndroidView(
            factory = { mapView },
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp)
        ) {
            val image =
                painterResource(id = R.drawable.ic_back)

            IconButton(
                onClick = { navigateToRoutes() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = (-12).dp)
                    .background(
                        color = Color.LightGray.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = image,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }

        FloatingActionButton(
            onClick = {
                val userLocation = userLocationLayer.cameraPosition()
                if (userLocation != null) {
                    mapView.mapWindow.map.move(
                        CameraPosition(userLocation.target, 16f, 0f, 0f),
                        Animation(Animation.Type.SMOOTH, 1f),
                        null
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 48.dp, end = 16.dp),
            backgroundColor = Color.White,
            contentColor = Color.Black,
            shape = CircleShape
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.rotate(35f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_navigation),
                    contentDescription = "My Location",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )

                Icon(
                    painter = painterResource(R.drawable.ic_navigation),
                    contentDescription = "My Location",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}