$(function(){
    $("#load_geolocation").click(function(ev){
        $(ev.currentTarget).text("Getting the position now...");
        var geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(function(r){
            if(this.getStatus() == BMAP_STATUS_SUCCESS){
                var pt = new BMap.Point(r.point.lng,r.point.lat);
                var geoc = new BMap.Geocoder();
                geoc.getLocation(pt, function(rs){
                    var addComp = rs.addressComponents;
                    $(ev.currentTarget).text(addComp.city + ", " + addComp.district + ", " + addComp.street);
                });
            }
            else {
                $(ev.currentTarget).text('Fail to locate your position');
            }
        },{enableHighAccuracy: true})
    });
});



