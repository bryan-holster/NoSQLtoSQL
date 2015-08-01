$('#mapModal').on('shown.bs.modal', function () {
if(ld == 0){

$('#mapModal').css('cursor', 'url(http://caladan.umw.edu:22224/NoSQLtoSQL/img/nwcursor.gif),auto');

function setUpClickListener() {

      map.addEventListener('tap', function (evt) {
      var coord = map.screenToGeo(evt.currentPointer.viewportX,
            evt.currentPointer.viewportY);
      document.getElementById('lat'+ next()).value = (((coord.lat > 0) ? '' : '-') + Math.abs(coord.lat.toFixed(4)));
      document.getElementById('long'+ counter).value = (((coord.lng > 0) ? '' : '-') + Math.abs(coord.lng.toFixed(4)));
      if(counter == 2){
          addRectangleToMap();
      }
      });
  }

  function addRectangleToMap() {
    if(last != 0){
      map.removeObject(last);
    }
      var boundingBox = new H.geo.Rect(document.getElementById('lat1').value, document.getElementById('long1').value, document.getElementById('lat2').value, document.getElementById('long2').value);

      var boundingShape =  new H.map.Rect(boundingBox, {
          style: {
            strokeColor: '#E8FA75',
            lineWidth: 4
          },
      });
      last = boundingShape;
      map.addObject(boundingShape);

  }

  var map = new H.Map(document.getElementById('map'),
        defaultLayers.normal.map,{
        center: {lat: 39.5112, lng: -98.1692},
        zoom: 4
    });

  var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

  setUpClickListener();
ld = 1;
}
});         
