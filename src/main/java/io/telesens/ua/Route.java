package io.telesens.ua;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route {
    private List<Arc> routeList = new ArrayList();
    private Station startStation;
    private Station endStation;
    private int routeNumber;
    boolean roundAbout = false;
    int numberOfBuses = 0;

    //Defining constructors:
    public Route(){}

    public Route(List<Arc> givenRouteList, int ID, int round){
        routeList = givenRouteList;
        startStation = givenRouteList.get(0).getFromStation();
        endStation = givenRouteList.get((givenRouteList.size()-1)).getToStation();
        routeNumber = ID;

        if(round == 1)
            roundAbout = true;
    }

    public List<Arc> getRouteList() {
        return routeList;
    }

    public Station getStartStation() {
        return startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public int getNumberOfBuses() {
        return numberOfBuses;
    }

    public void setNumberOfBuses(int numberOfBuses) {
        this.numberOfBuses = numberOfBuses;
    }

    //Method returning the index of arc in routelist containing given station.
    public int returnIndexContatin(Station node){
        int index = -1;

        for (int i = 0; i < routeList.size(); i++){
            if(routeList.get(i).containsStation(node))
                index = i;
        }

        return index;
    }

    public boolean isRoundabout(){
        return roundAbout;
    }

    //Method checking is station belong to this route.
    public boolean containStation(Station st){
        boolean checker = false;

        for (Arc arc: routeList)
            if(arc.containsStation(st)){
                checker = true;
                break;
            }
        return checker;
    }

    //Method returning list of all stations at the current route.
    public final List<Station> getAllStations() {
        List<Station> allRouteStations = new ArrayList();

        for (Arc route : routeList) {
            Station node = route.getFromStation();

            if (Collections.frequency(allRouteStations, node) == 0) {
                allRouteStations.add(node);
            }
            node = route.getToStation();

            if (Collections.frequency(allRouteStations, node) == 0) {
                allRouteStations.add(node);
            }
        }
        return allRouteStations;
    }

    @Override
    public String toString(){
        return "route number " + routeNumber;
    }

}
