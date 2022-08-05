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

package pathfinder;

import graph.DirectedLabeledGraph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CampusMap represent the map University of Washington's campus including buildings
 * (both short name and long name) and path from building to building, and it implements ModelAPI.
 */
public class CampusMap implements ModelAPI {

    // the graph that store info of the points and edge between each points
    private DirectedLabeledGraph<Point,Double> dlg;

    // a map that store the short name of building to building objects on campus
    private Map<String, CampusBuilding> buildingWithShortName;
    // a map that store the short name and the location of buildings
    private  Map<String,DirectedLabeledGraph.Node<Point>>  buildingShortNameNode;

    // AF(this): a UW campus map that
    // this.dlg represent the points and distance between points in UW
    // buildingWithShortName represent the short name of all buildings in UW
    // buildingShortNameNode represent the short name and their location(point) in the map

    //RI: Points in the map are unique
    // All the buildings in the map(both short name and long name) are unique
    // dlg != null
    // buildingWithShortName != null
    // buildingShortNameNode != null

    /**
     * construct a campus map
     * @spec.effects  dlg stores the points and distance between points
     *                buildingWithShortName stores the short name of all buildings
     *                buildingShortNameNode stores the short name and their location(point) in the map
     */
    public CampusMap() {
        dlg = new DirectedLabeledGraph<>();
        buildingWithShortName = new HashMap<>();
        buildingShortNameNode = new HashMap<>();

        List<CampusPath> campusPathList =  CampusPathsParser.parseCampusPaths("campus_paths.csv");
        for(CampusPath path: campusPathList) {
            DirectedLabeledGraph.Node<Point> startNode = new DirectedLabeledGraph.Node<>(new Point(path.getX1(), path.getY1()));
            DirectedLabeledGraph.Node<Point> endNode = new DirectedLabeledGraph.Node<>(new Point(path.getX2(), path.getY2()));
            dlg.addNode(startNode);
            dlg.addNode(endNode);
            dlg.addEdge(new DirectedLabeledGraph.Edge<>(startNode,endNode, path.getDistance()));
        }
        List<CampusBuilding> campusBuildingList = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        for(CampusBuilding bld:campusBuildingList) {
            buildingWithShortName.put(bld.getShortName(),bld);
            buildingShortNameNode.put(bld.getShortName(),new DirectedLabeledGraph.Node<>(
                    new Point(bld.getX(), bld.getY())));
        }
    }

    @Override
    public boolean shortNameExists(String shortName) {
        return buildingWithShortName.containsKey(shortName);
    }

    @Override
    public String longNameForShort(String shortName) {
        if(!buildingWithShortName.containsKey(shortName)) {
            throw new IllegalArgumentException();
        }
        return buildingWithShortName.get(shortName).getLongName();
    }

    @Override
    public Map<String, String> buildingNames() {
        Map<String, String> bldName =new HashMap<String, String>();
        for(CampusBuilding bld: buildingWithShortName.values()) {
            bldName.put(bld.getShortName(), bld.getLongName());
        }
        return bldName;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if(!shortNameExists(startShortName) || startShortName == null
           || !shortNameExists(endShortName) || endShortName == null ) {
            throw new IllegalArgumentException();
        }
        DirectedLabeledGraph.Node<Point> startPoint = buildingShortNameNode.get(startShortName);
        DirectedLabeledGraph.Node<Point> endPoint = buildingShortNameNode.get(endShortName);
        return Dijkstra.pathFinder(dlg,startPoint,endPoint);
    }

    // check the representation invariant.
    private void checkRep() {
        assert(dlg != null): "dlg == null";
        assert(buildingWithShortName != null): "buildingWithShortName == null";
        assert(buildingShortNameNode != null): "buildingShortNameNode == null";
    }
}
