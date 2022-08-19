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
import { Edges } from './Edges';

interface EdgeListProps {
    onChange(edges: Edges[]): void;  // called when a new edge list is ready
}

interface EdgeListState {
    textMessage: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */


class EdgeList extends Component<EdgeListProps,EdgeListState> {

    constructor(props:EdgeListProps) {
        super(props);
        this.state = {
            textMessage: "Please type here... ",
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

        let edgeList : Edges[] = [];
        let lines : string[] = str.split("\n");
        for(let i = 0; i < lines.length; i++) {
            let eachEdge : string[] = lines[i].split(" ");
            if (isNaN(parseInt(eachEdge[0])) || isNaN(parseInt(eachEdge[1])) ||
                isNaN(parseInt(eachEdge[2])) || isNaN(parseInt(eachEdge[3]))) {
                alert("Wrong coordinates type!");
                return;
            }
            let edgex1 : number = parseInt(eachEdge[0]);
            let edgey1 : number = parseInt(eachEdge[1]);
            let edgex2 : number = parseInt(eachEdge[2]);
            let edgey2 : number = parseInt(eachEdge[3]);
            if(edgex1 < 0 || edgex1 > 4000 || edgey1 < 0 || edgey1 > 4000 ||
                edgex2 < 0 || edgex2 > 4000 || edgey2 < 0 || edgey2 > 4000) {
                alert("Coordinates must be between 0 and 4000 (inclusive)");
                return;
            }
            let getcolor : string = eachEdge[4];
            edgeList.push({x1 : edgex1, y1 : edgey1,
                x2 : edgex2, y2 : edgey2,color : getcolor, key: i.toString()});
            console.log(eachEdge.toString());
        }
        console.log(edgeList.length);
        this.props.onChange(edgeList);
    }

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
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

export default EdgeList;
