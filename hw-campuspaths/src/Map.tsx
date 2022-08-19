/*
 * Copyright (C) 2022 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Summer Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import { LatLngExpression } from "leaflet";
import React, { Component } from "react";
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import { UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER } from "./Constants";
import { Edge } from "./Edge";
import { Point } from "./Point";
import MapPoint from "./MapPoint";


import L from 'leaflet';
import icon from 'leaflet/dist/images/marker-icon.png';

let pointIcon = L.icon({
    iconUrl: icon,
});

L.Marker.prototype.options.icon = pointIcon;

// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

// NOTE: This component is a suggestion for you to use, if you would like to. If
// you don't want to use this component, you're free to delete it or replace it
// with your hw-lines Map

interface MapProps {
    drawEdges: Edge[];
    drawPoints: Point[];
}

interface MapState {}

class Map extends Component<MapProps> {
    constructor(props: MapProps) {
        super(props);
    };

    render() {
        return (
            <div id="map">
                <MapContainer
                    center={position}
                    zoom={15}
                    scrollWheelZoom={false}
                >
                    <TileLayer
                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />
                    {
                        this.props.drawPoints.map((eachPoint) =>
                            <MapPoint
                                x={eachPoint.x}
                                y={eachPoint.y}
                                key={eachPoint.key}
                            />
                        )
                    }

                    {
                        this.props.drawEdges.map((eachEdge) =>
                            <MapLine
                                x1={eachEdge.x1}
                                x2={eachEdge.x2}
                                y1={eachEdge.y1}
                                y2={eachEdge.y2}
                                color={eachEdge.color}
                                key={eachEdge.key}
                            />
                        )
                    }


                </MapContainer>
            </div>
        );
    }
}

export default Map;
