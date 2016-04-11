<%--
//
// (c) 2012 DS Data Systems UK Ltd, All rights reserved.
//
// DS Data Systems and KonaKart and their respective logos, are 
// trademarks of DS Data Systems UK Ltd. All rights reserved.
//
// The information in this document is free software; you can redistribute 
// it and/or modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// 
// This software is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
--%>
<%@include file="Taglibs.jsp" %>
<% com.konakart.al.KKAppEng kkEng = (com.konakart.al.KKAppEng) session.getAttribute("konakartKey");  %>

<s:set scope="request" var="aboutUsContent" value="aboutUsContent"/> 
<%String aboutUsContent = (String)(request.getAttribute("aboutUsContent")); %>
<script src="http://maps.googleapis.com/maps/api/js"></script>
<script src="http://maps.google.com/maps/api/js?sensor=true"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script>
    var myCenter = new google.maps.LatLng(1.3041936, 103.8313419);
    //var myCenter2 = new google.maps.LatLng(1.3010481, 103.8363148);
    function calculateRoute(from, to, mode) {
        // Center initialized to Naples, Italy
        $('#panel').load(document.URL +  ' #panel');
        var myOptions = {
            zoom: 12,
            center: new google.maps.LatLng(1.3041936, 103.8313419),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        // Draw the map
        var mapObject = new google.maps.Map(document.getElementById("map"), myOptions);

        var directionsService = new google.maps.DirectionsService();
        var directionsDisplay = new google.maps.DirectionsRenderer();

        directionsDisplay.setMap(mapObject);
        directionsDisplay.setPanel(document.getElementById('panel'));

        var directionsRequest = {
            origin: from,
            destination: to,
            travelMode: google.maps.DirectionsTravelMode[mode],
            unitSystem: google.maps.UnitSystem.METRIC
        };

//        var directionsRequest2 = {
//            origin: from,
//            destination: to2,
//            travelMode: google.maps.DirectionsTravelMode.DRIVING,
//            unitSystem: google.maps.UnitSystem.METRIC
//        };

        directionsService.route(
                directionsRequest,
                function(response, status)
                {
                    if (status == google.maps.DirectionsStatus.OK)
                    {
//                        new google.maps.DirectionsRenderer({
//                            map: mapObject,
//                            directions: response
//                        });                        
                        directionsDisplay.setDirections(response);
                    }
                    else
                        $("#error").append("Unable to retrieve your route<br />");
                }
        );

//        var directionsService2 = new google.maps.DirectionsService();
//        var directionsDisplay2 = new google.maps.DirectionsRenderer();
//
//        directionsService2.route(
//                directionsRequest2,
//                function(response, status)
//                {
//                    if (status == google.maps.DirectionsStatus.OK)
//                    {
//                        new google.maps.DirectionsRenderer({
//                            map: mapObject,
//                            directions: response
//                        });
//                        
//                        directionsDisplay2.setDirections(response);
//                    }
//                    else
//                        $("#error").append("Unable to retrieve your route<br />");
//                }
//        );
    }

    $(document).ready(function() {
        // If the browser supports the Geolocation API
        if (typeof navigator.geolocation == "undefined") {
            $("#error").text("Your browser doesn't support the Geolocation API");
            return;
        }

        $("#from-link, #to-link").click(function(event) {
            event.preventDefault();
            var addressId = this.id.substring(0, this.id.indexOf("-"));

            navigator.geolocation.getCurrentPosition(function(position) {
                var geocoder = new google.maps.Geocoder();
                geocoder.geocode({
                    "location": new google.maps.LatLng(position.coords.latitude, position.coords.longitude)
                },
                function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK)
                        $("#" + addressId).val(results[0].formatted_address);
                    else
                        $("#error").append("Unable to retrieve your address<br />");
                });
            },
                    function(positionError) {
                        $("#error").append("Error: " + positionError.message + "<br />");
                    },
                    {
                        enableHighAccuracy: true,
                        timeout: 10 * 1000 // 10 seconds
                    });
        });

        $("#calculate-route").submit(function(event) {
            event.preventDefault();
            var selectedMode = document.getElementById('mode').value;
            calculateRoute($("#from").val(), myCenter, selectedMode);
        });
    });

//    function initialize()
//    {
//        var mapProp = {
//            center: myCenter,
//            zoom: 12,
//            mapTypeId: google.maps.MapTypeId.ROADMAP
//        };
//
//        var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
//
//        var marker = new google.maps.Marker({
//            position: myCenter,
//        });
//
//        marker.setMap(map);
//    }
//
//    google.maps.event.addDomListener(window, 'load', initialize);

</script>
<style type="text/css">
    #map {
        width: 500px;
        height: 400px;
        margin-top: 10px;
    }
</style>
<h1 id="page-title"><kk:msg  key="header.about.us"/></h1>			
<div class="content-area rounded-corners">
    <div id="about-us">
        <br />
        <form id="calculate-route" name="calculate-route" action="#" method="get">
            <label for="from">From:</label>
            <input type="text" id="from" name="from" required="required" placeholder="An address" size="30" />
            <a id="from-link" href="#">Get my position</a>
            <br />
            Mode of Transport:
            <select id="mode">
                <option value="DRIVING">Driving</option>
                <option value="WALKING">Walking</option>
                <option value="TRANSIT">Transit</option>
            </select>
            <!--<label for="to">To:</label>
            <input type="text" id="to" name="to" required="required" placeholder="Another address" size="30" />
            <a id="to-link" href="#">Get my position</a>
            <br />-->
            <br />
            <input type="submit" />
            <input type="reset" />
        </form>
        <div style="width: 600px;">
            <div id="map" style="width: 280px; height: 400px; float: left;"></div> 
            <div id="panel" style="width: 300px; float: right;"></div> 
        </div>
        <p id="error"></p>

        <div class="form-buttons-wide">
            <a href="Welcome.action" id="continue-button" class="button small-rounded-corners"><span><kk:msg  key="common.close"/></span></a>
        </div>
    </div>
</div>

