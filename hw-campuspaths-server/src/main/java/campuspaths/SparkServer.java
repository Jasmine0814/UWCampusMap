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

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import graph.DirectedLabeledGraph;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().
        CampusMap UWmap = new CampusMap();
        Gson gson = new Gson();

        Spark.get("/getBuildings", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Map<String, String> buildings = UWmap.buildingNames();
                //Gson gson = new Gson();
                return gson.toJson(buildings);
            }
        });

        Spark.get("/findpath", new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {

                String startBld = request.queryParams("start");
                String endBld = request.queryParams("end");

//                if(startBld == null || endBld == null)  {
//                    Spark.halt(400, "must have start and end");
//                }
//                Path<Point> shortestPath = UWmap.findShortestPath(startBld,endBld);

                Path<Point> shortestPath = null;

                try{
                    shortestPath = UWmap.findShortestPath(startBld, endBld);
                }
                catch (IllegalArgumentException iae){
                    Spark.halt(400, "start and end must be valid buildings");
                }

                List<Segment> result = new ArrayList<>();
                for (Path<Point>.Segment p : shortestPath) {
                    Segment curr = new Segment(p.getStart().getX(), p.getStart().getY(),
                            p.getEnd().getX(),p.getEnd().getY());
                    result.add(curr);
                }

                return gson.toJson(result);
            }
        });
    }

}
