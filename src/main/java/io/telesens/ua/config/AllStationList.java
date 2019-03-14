package io.telesens.ua.config;

import io.telesens.ua.Station;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class AllStationList {

    private static AllStationList instance;

    private HashSet<Station> setOfAllStations = new HashSet<>();
    List<Station> listOfAllStations = new ArrayList<>();

    public synchronized static AllStationList getInstance() {
        if (instance == null)
            instance = new AllStationList();
        return instance;
    }

    public synchronized List<Station> getListOfAllStations(){
        convertToArrayList();
        return listOfAllStations;
    }

    public synchronized void inputStation(Station station){
        setOfAllStations.add(station);
    }

    public synchronized int getNumberOfAllStations(){
        return setOfAllStations.size();
    }

    private void convertToArrayList(){
        listOfAllStations = new ArrayList<>();
        listOfAllStations.addAll(setOfAllStations);
    }

    public synchronized Station containStation(Station st){
        Iterator<Station> iterator = setOfAllStations.iterator();
        Station stationToReturn = null;

        while (iterator.hasNext()){
            Station helpStation = iterator.next();

            if(st.getName().equals(helpStation.getName())){
                stationToReturn = helpStation;
                break;
            }
        }

        return stationToReturn;
    }

}
