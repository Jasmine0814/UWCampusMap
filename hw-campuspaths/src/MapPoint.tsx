import React, { Component } from "react";
import {Marker, Popup} from "react-leaflet";
import {
    UW_LATITUDE,
    UW_LATITUDE_OFFSET,
    UW_LATITUDE_SCALE,
    UW_LONGITUDE,
    UW_LONGITUDE_OFFSET,
    UW_LONGITUDE_SCALE
} from "./Constants";

interface MapPointProps {
    x: number; // x coordinate of point
    y: number; // y coordinate of point
    key:string;
}

/**
 * Converts x coordinate to longitude
 */
function xToLon(x: number): number {
    return UW_LONGITUDE + (x - UW_LONGITUDE_OFFSET) * UW_LONGITUDE_SCALE;
}

/**
 * Converts y coordinate to latitude
 */
function yToLat(y: number): number {
    return UW_LATITUDE + (y - UW_LATITUDE_OFFSET) * UW_LATITUDE_SCALE;
}

class MapPoint extends Component<MapPointProps, {}> {
    constructor(props: any) {
        super(props);
        this.state = {
            edgeText: "",
        };
    }

    render() {
        return (
            <Marker position = {[yToLat(this.props.y), xToLon(this.props.x)]}>
                <Popup>
                    Building is at<br /> x: {this.props.x} y: {this.props.y}<br />
                </Popup>
            </Marker>
        );
    }
}

export default MapPoint;