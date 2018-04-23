var financeGradient = [
    'rgba(63, 0, 0, 0)',
    'rgba(127, 0, 0, 1)',
    'rgba(191, 0, 0, 1)',
    'rgba(255, 0, 0, 1)'
];

var inchoateGradient = [
    'rgba(0, 255, 255, 0)',
    'rgba(0, 255, 255, 1)',
    'rgba(0, 191, 255, 1)',
    'rgba(0, 127, 255, 1)',
    'rgba(0, 63, 255, 1)',
    'rgba(0, 0, 255, 1)',
    'rgba(0, 0, 223, 1)',
    'rgba(0, 0, 191, 1)',
    'rgba(0, 0, 159, 1)',
    'rgba(0, 0, 127, 1)',
    'rgba(63, 0, 91, 1)',
    'rgba(127, 0, 63, 1)',
    'rgba(191, 0, 31, 1)',
    'rgba(255, 0, 0, 1)'
];

var statutoryGradient = [
    'rgba(0, 255, 255, 0)',
    'rgba(0, 255, 255, 1)',
    'rgba(0, 191, 255, 1)',
    'rgba(0, 127, 255, 1)',
    'rgba(0, 63, 255, 1)',
    'rgba(0, 0, 255, 1)',
    'rgba(0, 0, 223, 1)',
    'rgba(0, 0, 191, 1)',
    'rgba(0, 0, 159, 1)',
    'rgba(0, 0, 127, 1)',
    'rgba(63, 0, 91, 1)',
    'rgba(127, 0, 63, 1)',
    'rgba(191, 0, 31, 1)',
    'rgba(255, 0, 0, 1)'
];

var personalGradient = [
    'rgba(0, 255, 255, 0)',
    'rgba(0, 255, 255, 1)',
    'rgba(0, 191, 255, 1)',
    'rgba(0, 127, 255, 1)',
    'rgba(0, 63, 255, 1)',
    'rgba(0, 0, 255, 1)',
    'rgba(0, 0, 223, 1)',
    'rgba(0, 0, 191, 1)',
    'rgba(0, 0, 159, 1)',
    'rgba(0, 0, 127, 1)',
    'rgba(63, 0, 91, 1)',
    'rgba(127, 0, 63, 1)',
    'rgba(191, 0, 31, 1)',
    'rgba(255, 0, 0, 1)'
];

var propertyGradient = [
    'rgba(0, 255, 255, 0)',
    'rgba(0, 255, 255, 1)',
    'rgba(0, 191, 255, 1)',
    'rgba(0, 127, 255, 1)',
    'rgba(0, 63, 255, 1)',
    'rgba(0, 0, 255, 1)',
    'rgba(0, 0, 223, 1)',
    'rgba(0, 0, 191, 1)',
    'rgba(0, 0, 159, 1)',
    'rgba(0, 0, 127, 1)',
    'rgba(63, 0, 91, 1)',
    'rgba(127, 0, 63, 1)',
    'rgba(191, 0, 31, 1)',
    'rgba(255, 0, 0, 1)'
];

var map;

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 13,
        center: {lat: 37.775, lng: -122.434}, //CHANGE ME
        mapTypeId: 'satellite'
    });

    financeHeatMap = new google.maps.visualization.HeatmapLayer({
        data: getRedPoints(),
        map: map,
        gradient: financeGradient
    });

    propertyHeatMap = new google.maps.visualization.HeatmapLayer({
        data: getGreenPoints(),
        map: map,
        gradient: propertyGradient
    });

    personalHeatMap = new google.maps.visualization.HeatmapLayer({
        data: getBluePoints(),
        map: map,
        gradient: personalGradient
    });

    inchoateHeatMap = new google.maps.visualization.HeatmapLayer({
        data: getYellowPoints(),
        map: map,
        gradient: inchoateGradient
    });

    statutoryHeatMap = new google.maps.visualization.HeatmapLayer({
        data: getPurplePoints(),
        map: map,
        gradient: statutoryGradient
    });
}

function toggleHeatmap() {
    statutoryHeatMap.setMap(statutoryHeatMap.getMap() ? null : map);
    inchoateHeatMap.setMap(inchoateHeatMap.getMap() ? null : map);
    personalHeatMap.setMap(personalHeatMap.getMap() ? null : map);
    financeHeatMap.setMap(financeHeatMap.getMap() ? null : map);
    propertyHeatMap.setMap(propertyHeatMap.getMap() ? null : map);
}

document.getElementById("scrolltext").innerHTML = tickerText;

