
function initialize() {

  var mapOptions = {
    zoom: 13,
    center: new google.maps.LatLng(43.257143,-2.924865),
    mapTypeId: google.maps.MapTypeId.ROADMAP,
    mapTypeControl: true,
    mapTypeControlOptions: {
      position: google.maps.ControlPosition.BOTTOM
    }
  };

  var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

  var trafficLayer = new google.maps.TrafficLayer();
  trafficLayer.setMap(map);

  var socket = new google.maps.MarkerImage('static/img/charge-map.png');
  var user = new google.maps.MarkerImage('static/img/user.png');

    // Adding markers to the map
  var deusto_marker = new google.maps.Marker({
      position: new google.maps.LatLng(43.271026,-2.938458),
      map: map,
      icon: socket
  });

  var san_mames_marker = new google.maps.Marker({
      position: new google.maps.LatLng(43.26287,-2.946515),
      map: map,
      icon: socket
  });

  var santutxu_marker = new google.maps.Marker({
      position: new google.maps.LatLng(43.254322,-2.911303),
      map: map,
      icon: socket
  });

  var las_arenas_marker = new google.maps.Marker({
      position: new google.maps.LatLng(43.325763,-3.01283),
      map: map,
      icon: socket
  });

  var barakaldo_marker = new google.maps.Marker({
      position: new google.maps.LatLng(43.297234,-2.985417),
      map: map,
      icon: socket
  });

  var getxo_marker = new google.maps.Marker({
      position: new google.maps.LatLng(43.340177,-3.006867),
      map: map,
      icon: socket
  });

  var portugalete_marker = new google.maps.Marker({
      position: new google.maps.LatLng(43.318279,-3.021316),
      map: map,
      icon: socket
  });

  // Creating the InfoWindows 
  var deusto_infowindow = new google.maps.InfoWindow();
  var san_mames_infowindow = new google.maps.InfoWindow();
  var santutxu_infowindow = new google.maps.InfoWindow();
  var las_arenas_infowindow = new google.maps.InfoWindow();
  var barakaldo_infowindow = new google.maps.InfoWindow();
  var getxo_infowindow = new google.maps.InfoWindow();
  var portugalete_infowindow = new google.maps.InfoWindow({
    maxWidth: 170
  });

  // Creating the InfoWindows content
  var deusto_content =
    '<div>' +
      '<h4>Estación de Deusto</h4>' +
      '<p><strong>Dirección:</strong></p>' +
      '<p><strong>Horario:</strong> 08:00-00:00</p>' +
      '<p><strong>Cómo llegar hasta ahi</strong></p>' +
      '<select class="span2">' +
                '<option>Caminando</option>' +
                '<option>Coche</option>' +
                '<option>Transporte público</option>' +
                '<option>Bicicleta</option>' +
      '</select>' +
      '<p><input type = "checkbox" id = "severalRoutes"' +
      '<label for = "severalRoutes">Mostrar varias rutas</label></p>' +
      '<button class="btn btn-inverse" data-latitude="43.271026" data-longitude="-2.938458">¿Cómo llegar hasta ahí?</button>' +
    '</div>';

  var san_mames_content =
    '<div>' +
      '<h4>Estación de San Mamés</h4>' +
      '<p><strong>Dirección:</strong></p>' +
      '<p><strong>Horario:</strong> 08:00-00:00</p>' +
      '<p><strong>Cómo llegar hasta ahi</strong></p>' +
    '<select class="span2">' +
              '<option>Caminando</option>' +
              '<option>Coche</option>' +
              '<option>Transporte público</option>' +
              '<option>Bicicleta</option>' +
    '</select>' +
    '<p><input type = "checkbox" id = "severalRoutes"' +
    '<label for = "severalRoutes">Mostrar varias rutas</label></p>' +
      '<button class="btn btn-inverse" data-latitude="43.26287" data-longitude="-2.946515">¿Cómo llegar hasta ahí?</button>' +
    '</div>';

  var santutxu_content =
  '<div>' +
    '<h4>Estación de Santutxu</h4>' +
    '<p><strong>Dirección:</strong></p>' +
    '<p><strong>Horario:</strong> 08:00-00:00</p>' +
    '<p><strong>Cómo llegar hasta ahi</strong></p>' +
    '<select class="span2">' +
              '<option>Caminando</option>' +
              '<option>Coche</option>' +
              '<option>Transporte público</option>' +
              '<option>Bicicleta</option>' +
    '</select>' +
    '<p><input type = "checkbox" id = "severalRoutes"' +
    '<label for = "severalRoutes">Mostrar varias rutas</label></p>' +
    '<button class="btn btn-inverse" data-latitude="43.254322" data-longitude="-2.911303">¿Cómo llegar hasta ahí?</button>' +
  '</div>';

  var las_arenas_content =
  '<div>' +
    '<h4>Estación de Las Arenas</h4>' +
    '<p><strong>Dirección:</strong></p>' +
    '<p><strong>Horario:</strong> 08:00-00:00</p>' +
    '<p><strong>Cómo llegar hasta ahi</strong></p>' +
    '<select class="span2">' +
              '<option>Caminando</option>' +
              '<option>Coche</option>' +
              '<option>Transporte público</option>' +
              '<option>Bicicleta</option>' +
    '</select>' +
    '<p><input type = "checkbox" id = "severalRoutes"' +
    '<label for = "severalRoutes">Mostrar varias rutas</label></p>' +
    '<button class="btn btn-inverse" data-latitude="43.325763" data-longitude="-3.01283">¿Cómo llegar hasta ahí?</button>' +
  '</div>';

  var barakaldo_content =
  '<div>' +
    '<h4>Estación de Barakaldo</h4>' +
    '<p><strong>Dirección:</strong></p>' +
    '<p><strong>Horario:</strong> 08:00-00:00</p>' +
    '<p><strong>Cómo llegar hasta ahi</strong></p>' +
    '<select class="span2">' +
              '<option>Caminando</option>' +
              '<option>Coche</option>' +
              '<option>Transporte público</option>' +
              '<option>Bicicleta</option>' +
    '</select>' +
    '<p><input type = "checkbox" id = "severalRoutes"' +
    '<label for = "severalRoutes">Mostrar varias rutas</label></p>' +
    '<button class="btn btn-inverse" data-latitude="43.297234" data-longitude="-2.985417">¿Cómo llegar hasta ahí?</button>' +
  '</div>';

  var getxo_content =
  '<div>' +
    '<h4>Estación de Getxo</h4>' +
    '<p><strong>Dirección:</strong></p>' +
    '<p><strong>Horario:</strong> 08:00-00:00</p>' +
    '<p><strong>Cómo llegar hasta ahi</strong></p>' +
    '<select class="span2">' +
              '<option>Caminando</option>' +
              '<option>Coche</option>' +
              '<option>Transporte público</option>' +
              '<option>Bicicleta</option>' +
    '</select>' +
    '<p><input type = "checkbox" id = "severalRoutes"' +
    '<label for = "severalRoutes">Mostrar varias rutas</label></p>' +
    '<button class="btn btn-inverse" data-latitude="43.340177" data-longitude="-3.006867">¿Cómo llegar hasta ahí?</button>' +
  '</div>';

  var portugalete_content =
  '<div>' +
    '<h4>Estación de Portugalete</h4>' +
    '<p><strong>Dirección:</strong></p>' +
    '<p><strong>Horario:</strong> 08:00-00:00</p>' +
    '<p><strong>Cómo llegar hasta ahi</strong></p>' +
    '<select class="span2">' +
              '<option>Caminando</option>' +
              '<option>Coche</option>' +
              '<option>Transporte público</option>' +
              '<option>Bicicleta</option>' +
    '</select>' +
    '<p><input type = "checkbox" id = "severalRoutes">' +
    '<label for = "severalRoutes">Mostrar varias rutas</label></p>' +
    '<p><input type = "checkbox" id = "avoidTolls">' +
    '<label for = "avoidTolls">Evitar peajes</label></p>' +
    '<button class="btn btn-inverse" data-latitude="43.318279" data-longitude="-3.021316">Buscar rutas</button>' +
  '</div>';

  // Setting the content of the InfoWindows
  deusto_infowindow.setContent(deusto_content);
  san_mames_infowindow.setContent(san_mames_content);
  santutxu_infowindow.setContent(santutxu_content);
  las_arenas_infowindow.setContent(las_arenas_content);
  barakaldo_infowindow.setContent(barakaldo_content);
  getxo_infowindow.setContent(getxo_content);
  portugalete_infowindow.setContent(portugalete_content);


  // Adding events to the markers
  google.maps.event.addListener(deusto_marker, 'click', function() {
    closeInfoWindows(deusto_marker);
    deusto_infowindow.open(map, deusto_marker);
  });

  google.maps.event.addListener(san_mames_marker, 'click', function() {
    closeInfoWindows(san_mames_marker);
    san_mames_infowindow.open(map, san_mames_marker);
  });

  google.maps.event.addListener(santutxu_marker, 'click', function() {
    closeInfoWindows(santutxu_marker);
    santutxu_infowindow.open(map, santutxu_marker);
  });

  google.maps.event.addListener(las_arenas_marker, 'click', function() {
    closeInfoWindows(las_arenas_marker);
    las_arenas_infowindow.open(map, las_arenas_marker);
  });

  google.maps.event.addListener(barakaldo_marker, 'click', function() {
    closeInfoWindows(barakaldo_marker);
    barakaldo_infowindow.open(map, barakaldo_marker);
  });

  google.maps.event.addListener(getxo_marker, 'click', function() {
    closeInfoWindows(getxo_marker);
    getxo_infowindow.open(map, getxo_marker);
  });

  google.maps.event.addListener(portugalete_marker, 'click', function() {
    closeInfoWindows(portugalete_marker);
    portugalete_infowindow.open(map, portugalete_marker);
  });

locateUser();
var Userposition;

function locateUser() {
  if (navigator.geolocation)
    {
      navigator.geolocation.getCurrentPosition(showPosition);
    } else {
        alert('No support for HTML5 geolocation.');
      }
  }

  function showPosition(position) {
    var latitude = position.coords.latitude;
    var longitude = position.coords.longitude;

    var latLng = new google.maps.LatLng(latitude, longitude);
    var user_marker = new google.maps.Marker({
      position: latLng,
      map: map,
      icon: user
    });
    UserPosition = latLng;
  }

  function closeInfoWindows(marker) {
    deusto_infowindow.close();
    san_mames_infowindow.close();
    santutxu_infowindow.close();
    las_arenas_infowindow.close();
    barakaldo_infowindow.close();
    getxo_infowindow.close();
    portugalete_infowindow.close();
    //map.panTo(marker.getPosition());
    //map.setZoom(map.getZoom()+1);
  }


  $('#map_canvas').delegate('.btn-inverse', "click", function() {
    closeInfoWindows();
    var travel_mode = $(this).closest('div').find('select').val();
    var alternative_routes = $(this).closest('div').find('input[type="checkbox"]:eq(0)').is(':checked');
    var avoidTolls = $(this).closest('div').find('input[type="checkbox"]:eq(1)').is(':checked');
    calculateRoute($(this).data('latitude'), $(this).data('longitude'), travel_mode, alternative_routes, avoidTolls);
    });

    var directionsService = new google.maps.DirectionsService();
    var directionsDisplay = new google.maps.DirectionsRenderer();
    directionsDisplay.setMap(map);
    directionsDisplay.setPanel(document.getElementById("directionsPanel"));

  function calculateRoute(latitude, longitude, travel_mode, alternative_routes, avoidTolls) {

    if (travel_mode == "Caminando")
      travel_mode = google.maps.TravelMode.WALKING;
    else if (travel_mode == "Transporte público")
      travel_mode = google.maps.TravelMode.TRANSIT;
    else if (travel_mode == "Bicicleta")
      travel_mode = google.maps.TravelMode.BICYCLING;
    else travel_mode = google.maps.TravelMode.DRIVING;

    var request = {
      origin: UserPosition,
      destination: new google.maps.LatLng(latitude,longitude),
      travelMode: travel_mode,
      provideRouteAlternatives: alternative_routes,
      avoidTolls: avoidTolls
    };

    directionsService.route(request, function(result, status) {
      if (status == google.maps.DirectionsStatus.OK) {
        directionsDisplay.setDirections(result);
      }
    });
  }

  $('#directionsPanel').bind('DOMNodeInserted', function(event) {
    if($('#directionsPanel .adp').length ==2) {
      $('#directionsPanel .adp').last().remove();
      $('#directionsPanel .adp-list').last().remove();
    }
});

}



function loadScript() {
  var script = document.createElement("script");
  script.type = "text/javascript";
  script.src = "http://maps.googleapis.com/maps/api/js?key=AIzaSyBmNZpoxu8WGGluEbJ1Q4cKChFN83z-ecU&sensor=false&callback=initialize";
  document.body.appendChild(script);
}

window.onload = loadScript;