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

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";
import Map from "./Map";
import { Edge } from './Edge';
import { Point } from './Point';
import BldList from './BldList';

interface AppState {
    lines: Edge[];
    points: Point[];
}
class App extends Component<{}, AppState> {

    constructor(props: any) {
        super(props);
        this.state = {
            lines: [],
            points: []
        };
    }

    handleEdgeChange(value: Edge[]) {
        this.setState({lines: value});
    }

    handlePointChange(value: Point[]) {
        this.setState({points: value});
    }

    clear() {
        this.setState({
            lines:[],
            points:[],
        });
    }

    render() {
        return (
            <div id="uw-mapper">
                <h1 id="app-title">UW Mapper! Find the shortest Path!</h1>
                <div>
                    <Map
                        drawEdges = {this.state.lines}
                        drawPoints = {this.state.points}
                    />
                </div>
                <BldList
                    onChange={(edges,points) => {
                        this.handleEdgeChange(edges);
                        this.handlePointChange(points);
                    }}
                />
            </div>
        );
    }

}

export default App;