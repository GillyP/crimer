var financeGradient = [
    'rgba(0, 255, 0, 0)',
    'rgba(0, 255, 0, 1)',
    'rgba(63, 191, 0, 1)',
    'rgba(127, 127, 0, 1)',
    'rgba(191, 63, 0, 1)',
    'rgba(255, 0, 0, 1)'
];

var inchoateGradient = [
    'rgba(255, 0, 255, 0)',
    'rgba(255, 0, 255, 1)',
    'rgba(255, 0, 191, 1)',
    'rgba(255, 0, 127, 1)',
    'rgba(255, 0, 63, 1)',
    'rgba(255, 0, 0, 1)',
    'rgba(255, 63, 0, 1)',
    'rgba(255, 127, 0, 1)',
    'rgba(255, 191, 0, 1)',
    'rgba(255, 255, 0, 1)'
];

var statutoryGradient = [
    'rgba(255, 255, 0, 0)',
    'rgba(255, 255, 0, 1)',
    'rgba(255, 191, 0, 1)',
    'rgba(255, 127, 0, 1)',
    'rgba(255, 63, 0, 1)',
    'rgba(255, 0, 0, 1)',
    'rgba(255, 0, 63, 1)',
    'rgba(255, 0, 127, 1)',
    'rgba(255, 0, 191, 1)',
    'rgba(255, 0, 255, 1)'
];

var personalGradient = [
    'rgba(255, 255, 0, 0)',
    'rgba(255, 255, 0, 1)',
    'rgba(255, 191, 0, 1)',
    'rgba(255, 127, 0, 1)',
    'rgba(255, 63, 0, 1)',
    'rgba(255, 0, 0, 1)',
    'rgba(255, 0, 0, 1)',
    'rgba(191, 0, 63, 1)',
    'rgba(127, 0, 127, 1)',
    'rgba(63, 0, 191, 1)',
    'rgba(0, 0, 255, 1)'
];

var propertyGradient = [
    'rgba(255, 0, 0, 0)',
    'rgba(255, 0, 0, 1)',
    'rgba(191, 63, 0, 1)',
    'rgba(127, 127, 0, 1)',
    'rgba(63, 191, 0, 1)',
    'rgba(0, 255, 0, 1)'
];

var map;

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 13,
        center: {lat: 41.8781, lng: -87.6298},
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

    statutoryHeatMap.set('radius', 50);
    personalHeatMap.set('radius', 50);
    propertyHeatMap.set('radius', 50);
    inchoateHeatMap.set('radius', 50);
    financeHeatMap.set('radius', 50);

    statutoryHeatMap.set('opacity', 1);
    personalHeatMap.set('opacity', 1);
    propertyHeatMap.set('opacity', 1);
    inchoateHeatMap.set('opacity', 1);
    financeHeatMap.set('opacity', 1);
}

function toggleHeatmap() {
    statutoryHeatMap.setMap(statutoryHeatMap.getMap() ? null : map);
    inchoateHeatMap.setMap(inchoateHeatMap.getMap() ? null : map);
    personalHeatMap.setMap(personalHeatMap.getMap() ? null : map);
    financeHeatMap.setMap(financeHeatMap.getMap() ? null : map);
    propertyHeatMap.setMap(propertyHeatMap.getMap() ? null : map);
}

document.getElementById("scrolltext").innerHTML = tickerText;
