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

import React, {Component} from 'react';
import { Points } from './Points';

interface PointListProps {
    onChange(points: Points[]): void;  // called when a new point list is ready
}

interface PointListState {
    textMessage: string;
}

/**
 * A text field that allows the user to enter the list of points.
 * Also contains the buttons that the user will use to interact with the app.
 */


class PointList extends Component<PointListProps,PointListState> {

    constructor(props:PointListProps) {
        super(props);
        this.state = {
            textMessage: "Please type points here...",
        };
    }

    textFieldChange(event : any) {
        this.setState({textMessage : event.target.value})
    }

    clearTextField() {
        this.setState({textMessage : ""})
        this.props.onChange([]);
    }

    getData(str: string) {

        let pointList : Points[] = [];
        let lines : string[] = str.split("\n");
        for(let i = 0; i < lines.length; i++) {
            let eachPoint : string[] = lines[i].split(" ");
            if (isNaN(parseInt(eachPoint[0])) || isNaN(parseInt(eachPoint[1]))) {
                alert("Wrong coordinates type! Coordinates must be integers");
                return;
            }
            let pointx : number = parseInt(eachPoint[0]);
            let pointy : number = parseInt(eachPoint[1]);
            if(pointx < 0 || pointx > 4000 || pointy < 0 || pointy > 4000) {
                alert("Coordinates must be between 0 and 4000 (inclusive)");
                return;
            }
            pointList.push({x1 : pointx, y1 : pointy});
            console.log(eachPoint.toString());
        }
        console.log(pointList.length);
        this.props.onChange(pointList);
    }

    render() {
        return (
            <div id="point-list">
                Points <br/>
                <textarea
                    rows={5}
                    cols={30}
                    value={this.state.textMessage}
                    onChange = {(event) => this.textFieldChange(event)}
                /> <br/>
                <button onClick={() => this.getData(this.state.textMessage)}>Draw</button>
                <button onClick={() => this.clearTextField()}>Clear</button>
            </div>
        );
    }
}

export default PointList;
