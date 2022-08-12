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
    x1: number; // x coordinate of point
    y1: number; // y coordinate of point
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
            <Marker position = {[yToLat(this.props.y1), xToLon(this.props.x1)]}>
                <Popup>
                    This point is at <br /> x: {this.props.x1} y: {this.props.y1}
                </Popup>
            </Marker>
        );
    }
}

export default MapPoint;
