package io.telesens.ua;

import io.telesens.ua.config.AllRoutesList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Илья
 * Class represents the global symulation timer. Singletone pattern used.
 * 840 mins = one day
 */

public class GlobalTime {
    private static GlobalTime instance;

    private int globalTime = 0;
    private int weekNumber = 1;

    public synchronized static GlobalTime getInstance() {
        if (instance == null)
            instance = new GlobalTime();
        return instance;
    }

    public synchronized void setWeekNumber(int i){
        weekNumber = i;
    }

    public synchronized int getWeekNumber(){
        return weekNumber;
    }

    public synchronized int getGlobalTime(){
        return globalTime;
    }

    public synchronized String timeForPrint(){
        int hour;
        int min;

        hour = globalTime/60 + 8;
        min = globalTime%60;
        String minutes = min+"";

        if(min < 10)
            minutes = 0 + "" + min;

        return hour + ":" + minutes;
    }

    public synchronized void timeRuns(){
        globalTime++;
    }

    public synchronized void timerDropStart(){
        globalTime = 0;

        HashSet<Station> allStations = new HashSet<>();
        List<Station> listOfAllStations = new ArrayList<>();

        Iterator<Route> iterator = AllRoutesList.getInstance().getListOfAllRoutes().iterator();

        while (iterator.hasNext()){
            List<Station> helpList = new ArrayList<>(iterator.next().getAllStations());

            Iterator<Station> iterator1 = helpList.iterator();

            while (iterator1.hasNext())
                allStations.add(iterator1.next());
        }

        Iterator<Station> iterator2 = allStations.iterator();

        while (iterator2.hasNext())
            listOfAllStations.add(iterator2.next());

        Iterator<Station> iterator3 = listOfAllStations.iterator();

        while (iterator3.hasNext())
            iterator3.next().passengersOnStation = new ConcurrentLinkedQueue<>();
    }
}