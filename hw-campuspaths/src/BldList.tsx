import { Component } from "react";
import { Building } from "./Building";
import { Edge } from "./Edge";
import { Point } from "./Point";

interface BuildingListProps {
    onChange(edges: Edge[], points: Point[]): void;
    //onChangeClear():void
}

interface BuildingListState {
    buildings: Building[];
    startBld:string
    endBld:string
}

class BldList extends Component<BuildingListProps, BuildingListState> {

    constructor(props:BuildingListProps) {
        super(props);
        this.state = {
            buildings:[],
            startBld: "empty",
            endBld: "empty",
        };
    }


    startBuildingOnChange = (event: { target: { value: any; }; }) => {
        this.setState({
            startBld: event.target.value,
        });
    }

    endBuildingOnChange = (event: { target: { value: any; }; }) => {
        this.setState({
            endBld: event.target.value,
        });
    }

    componentDidMount() {
        this.sendBuildingRequest()
    }

    sendBuildingRequest = async () => {
        try{
            let url:string = `http://localhost:4567/getBuildings`;
            let response = await fetch(url);
            if(!response.ok) {
                alert("Expected: 200-299, But was: " + response.status)
                return;
            }
            let promise = await response.text();
            this.getBuildings(promise);
        }catch(e) {
            alert("Error contacting the server!(Building)");
            console.log(e)
        }
    }

    sendPathRequest = async () => {
        const start = this.state.startBld;
        const end = this.state.endBld;
        if(start === "empty" && end === "empty") {
            alert("Please chose a start building and a end building.")
            return
        } else if (start === "empty") {
            alert("Please chose a start building.")
            return
        } else if (end === "empty") {
            alert("Please chose a end building.")
            return
        } else if (start === end) {
            alert("start building and end building should be different")
            return
        }
        try{
            let url = `http://localhost:4567/findpath?start=${start}&end=${end}`;
            let response = await fetch(url);
            if(!response.ok) {
                alert("Expected: 200-299, But was: " + response.status)
                return;
            }

            let promise = await response.text();
            this.getPath(promise);

        }catch(e) {
            alert("Error contacting the server!(Path)");
            console.log(e)
        }

    }

    // sendPathingRequest = async () => {
    //     const start = this.state.startBld;
    //     const end = this.state.endBld;
    //     try{
    //         let url:string = `http://localhost:4567/findpath?start=${start}&end=${end}`;
    //         let response = await fetch(url);
    //         if(!response.ok) {
    //             alert("Expected: 200, But: " + response.status)
    //             return;
    //         }
    //         let promise = await response.text();
    //         this.getPath(promise);
    //     }catch(e) {
    //         alert("Error contacting the server!");
    //         console.log(e)
    //     }
    // }

    getPath(str:string) {

        let pathList: string[] = str.substring(2, str.length - 2).split("},{");
        let pointList: Point[] = [];
        let edgeList: Edge[] = [];

        for(let i = 0 ; i < pathList.length; i++) {
            let alternatingColor : string;
            let eachVar: string[] = pathList[i].split(",");

            if(i % 2 === 0) {
                alternatingColor = 'yellow'
            } else {
                alternatingColor = 'purple'
            }
            for(let j = 0; j < eachVar.length; j++) {
                eachVar[j] = eachVar[j].substring(5);
            }
            let startx = parseInt(eachVar[0]);
            let starty = parseInt(eachVar[1]);
            let endx = parseInt(eachVar[2])
            let endy = parseInt(eachVar[3])

            edgeList.push({x1: startx, y1: starty,
                x2: endx , y2: endy, color: alternatingColor, key: i.toString()});
        }

            let startP: Point = {
                key: edgeList[0].x1 + "," + edgeList[0].y1,
                x: edgeList[0].x1,
                y: edgeList[0].y1,
            }

            let endP: Point = {
                key: edgeList[edgeList.length - 1].x1 + "," + edgeList[edgeList.length - 1].y1,
                x: edgeList[edgeList.length - 1].x1,
                y: edgeList[edgeList.length - 1].y1,
            }
            pointList.push(startP, endP);
            this.props.onChange(edgeList, pointList);
    }


    // getPath(str:string) {
    //     let bracketIndex : number = str.indexOf('[');
    //     let paths:string = str.substring(bracketIndex + 1, str.length - 2);
    //     let eachpaths : string[] = paths.split("},{")
    //     let PointList : Point[] = [];
    //     let edgeList : Edge[] = [];
    //     let color : string;
    //
    //
    //     for(let i = 0; i < eachpaths.length; i++) {
    //         if(i % 2 === 0) {
    //             color = 'yellow'
    //         } else {
    //             color = 'purple'
    //         }
    //         let eachVar : string[] = eachpaths[i].split("")
    //     }
    // }

    getBuildings(str:string) {

        let buildingList:Building[] = [];
        let buildingData:string[] = str.substring(1, str.length - 1).split(",");
        for (let i = 0; i < buildingData.length; i++) {

            let oneBuilding: string[] = buildingData[i].split(":");
            let shortname = oneBuilding[0].substring(1, oneBuilding[0].length - 1);
            let longname = oneBuilding[1].substring(1, oneBuilding[1].length - 1);
            buildingList.push({shortName: shortname, longName: longname, key:longname+":"+shortname});
            console.log(shortname + " " + longname);
        }

        const sortBuilding = (a:Building, b:Building) => {
            const bBuilding =  b.longName.toUpperCase();
            const aBuilding = a.longName.toUpperCase();
            return bBuilding.localeCompare(aBuilding);
        }

        buildingList.sort(sortBuilding);

        this.setState({
            buildings: buildingList,
        })
    }

    clearOnClick= () => {
        this.setState({startBld:"empty",endBld:"empty"})
        this.props.onChange([], []);
    }

    render() {
        return (
            <div>
                <h3> Start Building </h3>
                <div id="dropdown">
                    <label htmlFor="PickStartBuilding" >Start Building:</label>
                    <select id="PickStartBuilding" onChange={this.startBuildingOnChange} value={this.state.startBld}>
                        <option disabled selected> - Select a start building here - </option>
                        {this.state.buildings.map((building, index) =>{
                            return (
                                <option key={index} value={building.shortName}>{building.longName}</option>
                            )
                        })
                        }
                    </select>
                </div>

                <h3> End Building </h3>
                <div id="dropdown">
                    <label htmlFor="PickEndBuilding" >End Building:</label>
                    <select id="PickEndBuilding" onChange={this.endBuildingOnChange} value={this.state.endBld}>
                        <option disabled selected> - Select a end building here - </option>

                        {this.state.buildings.map((building, index) =>{
                            return (
                                <option key={index} value={building.shortName}>{building.longName}</option>
                            )
                        })
                        }
                    </select>
                </div>

                <button onClick={this.sendPathRequest}>Draw Path</button>
                <button onClick={this.clearOnClick}>Clear Map</button>
            </div>
        )
    }
}

export default BldList;
