package io.telesens.ua;

import io.telesens.ua.config.DaysOfWeek;
import io.telesens.ua.statistic.TotalResults;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Otlev Illia
 */

public class Bus {
    int id;
    long timeForRest = 15; //min
    long timeForStation = 1; //in seconds.
    private int startTime; //Define in constructor
    long busTime;
    Route busRoute;
    Station busStartStation;
    Station busCurrentStation;
    Station busNextStation;
    final int capacity = 50;
    int currentCapacity = 0;
    HashMap<Station, Integer> passengersOnBoard = new HashMap<>();
    private int direction = -1;
    int routePerDayCounter = 0;

    public Bus(int id, Route route){
        this.id = id;
        busRoute = route;
        busStartStation = route.getStartStation();
        busCurrentStation = route.getStartStation();
       // busNextStation = this.getBusNextStation();
    }

    public double stopTime(int passengersOut, int passengersIn){
        return (timeForStation + passengersIn*0.2 + passengersOut*0.2);
    }

    public void setStartTime(int startTime){
        this.startTime = startTime;
    }

    public int getStartTime(){
        return startTime;
    }

    public void dropStartTime(){
        startTime = 0;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public long getTimeInRoad(Station node){
        long time = -1;
        int arcIndex = busRoute.returnIndexContatin(node);

        time = busRoute.getRouteList().get(arcIndex).getTime();
        return time;
    }

    public Station getBusCurrentStation() {
        return busCurrentStation;
    }

    public Station getBusNextStation() {
        Station currentStation = getBusCurrentStation();

        if(busRoute.isRoundabout()){
        for (Arc arc: busRoute.getRouteList()) {

            if (arc.getFromStation().equals(currentStation)) {
                busNextStation = arc.getToStation();
            }
            try {
                if (arc.getToStation().equals(currentStation)) {
                    busNextStation = busRoute.getRouteList().get(busRoute.getRouteList().indexOf(arc) + 1).getFromStation();

//                if (busNextStation == null) {
//                    busNextStation = busRoute.getRouteList().get(0).getToStation();
                }
            }catch (IndexOutOfBoundsException e){
                busNextStation = busRoute.getRouteList().get(0).getFromStation();
                TotalResults.getInstance().increaseNumberOfRounds();
                routePerDayCounter++;
                busTime += timeForRest;
            }

        }
        return busNextStation;
        }
        else {
            if(direction < 0){
                for(int i = 0; i < busRoute.getRouteList().size(); i++){
                    if(busRoute.getRouteList().get(i).getFromStation().equals(busCurrentStation)){
                        busNextStation = busRoute.getRouteList().get(i).getToStation();
                        break;
                    }
                    if(busRoute.getRouteList().get(i).getToStation().equals(busCurrentStation)){
                        if(i + 1 == busRoute.getRouteList().size()){
                            busNextStation = busRoute.getRouteList().get(i).getFromStation();
                            direction = direction * (-1);
                            busTime += timeForRest;
                            TotalResults.getInstance().increaseNumberOfRounds();
                            routePerDayCounter++;
                            break;
                        }else {
                        busNextStation = busRoute.getRouteList().get(i+1).getFromStation();
                        break;
                        }
                    }
                }
            }else {
                for(int i = busRoute.returnIndexContatin(busCurrentStation); i >= 0; i--){
                    if(busRoute.getRouteList().get(i).getToStation().equals(busCurrentStation)){
                        busNextStation = busRoute.getRouteList().get(i).getFromStation();
                        break;
                    }
                    if(busRoute.getRouteList().get(i).getFromStation().equals(busCurrentStation)){
                        if(i - 1 == -1){
                            busNextStation = busRoute.getRouteList().get(i).getToStation();
                            direction = direction * (-1);
                            busTime += timeForRest;
                            TotalResults.getInstance().increaseNumberOfRounds();
                            routePerDayCounter++;
                            break;
                        }else {
                            busNextStation = busRoute.getRouteList().get(i-1).getToStation();
                            break;
                        }
                    }
                }

            }
            return busNextStation;
        }
    }

    //Modify the method
    public boolean isToStation(Station st){
        boolean checker = false;
        for(int i = busRoute.returnIndexContatin(st); i < busRoute.getRouteList().size(); i ++){
            if(busRoute.getRouteList().contains(st)){
                checker = true;
            }
        }
        return checker;
        //return busRoute.containStation(st);
    }

    public boolean canTakePassenger(Passenger passenger){
        boolean checker = false;

        if(this.getCurrentCapacity() < capacity) {

            if (busRoute.isRoundabout()) {
                for (Arc arc : busRoute.getRouteList()) {
                    if (arc.getFromStation().equals(passenger.getStationTo()))
                        checker = true;

                    if (arc.getToStation().equals(passenger.getStationTo()))
                        checker = true;
                }
            } else {
                if (direction < 0) {
                    for (int i = busRoute.returnIndexContatin(busCurrentStation); i < busRoute.getRouteList().size(); i++) {
                        if (busRoute.getRouteList().get(i).getFromStation().equals(passenger.getStationTo()))
                            checker = true;

                        if (busRoute.getRouteList().get(i).getToStation().equals(passenger.getStationTo()))
                            checker = true;
                    }
                } else {
                    for (int i = busRoute.returnIndexContatin(busCurrentStation); i > 0; i--) {
                        if (busRoute.getRouteList().get(i).getFromStation().equals(passenger.getStationTo()))
                            checker = true;

                        if (busRoute.getRouteList().get(i).getToStation().equals(passenger.getStationTo()))
                            checker = true;
                    }
                }
            }
        }
        return checker;
        //return ((getCurrentCapacity()<capacity)&(busRoute.containStation(passenger.getStationTo())));
    }

    public int takeOffPassengers(){// set BusCurrent Station instead of station
        if(passengersOnBoard.get(busCurrentStation) == null){
            return 0;
        }
        else {
            int passengersOff = passengersOnBoard.get(busCurrentStation);

            passengersOnBoard.remove(busCurrentStation);
            currentCapacity -= passengersOff;
            TotalResults.getInstance().increaseNumberOfPassTravelled(passengersOff);

            return passengersOff;
        }
    }

    public void getPassengers(Passenger passenger){
        if(passengersOnBoard.size() == 0){
            passengersOnBoard.put(passenger.getStationTo(), 1);
            currentCapacity += 1;
        }
        else {
            if(passengersOnBoard.containsKey(passenger.getStationTo())){
                passengersOnBoard.put(passenger.getStationTo(), passengersOnBoard.get(passenger.getStationTo())+1);
                currentCapacity += 1;
            }
            else {
                passengersOnBoard.put(passenger.getStationTo(), 1);
                currentCapacity += 1;
            }
        }
    }

    public void initialStation(){

        int initPassCounter = 0;

        busTime = startTime;
        routePerDayCounter = 0;
        //startTime = 0;
        currentCapacity = 0;
        passengersOnBoard = new HashMap<>();

        this.busCurrentStation = this.busRoute.getStartStation();
        this.getBusCurrentStation().fillStation(DaysOfWeek.Monday);
//        System.out.println("Bus " + this + " starting from station: " + this.getBusCurrentStation());//some type of temporary log

//        System.out.println("Number of first passengers = " + this.getBusCurrentStation().passengersOnStation.size());

        Iterator<Passenger> iterator = this.getBusCurrentStation().passengersOnStation.iterator();

        while (iterator.hasNext()){
            Passenger pass = iterator.next();
            synchronized (pass){

                if (this.canTakePassenger(pass)){
                    this.getPassengers(pass);
                    this.getBusCurrentStation().passengersOnStation.remove(pass);
                    initPassCounter++;
//                    System.out.println("Bus " + this + " took " + initPassCounter + " passengers. On board - " + this.currentCapacity);
                }
                else break;

                if((int)(this.startTime+this.stopTime(0,initPassCounter)) == 0){
                    this.busTime += 1;
                } else {
                    this.busTime = this.startTime + (int)this.stopTime(0,initPassCounter);
                }
            }
        }

//        for (Passenger passenger : this.getBusCurrentStation().passengersOnStation) {
//
//            if (this.canTakePassenger(passenger)){
//                this.getPassengers(passenger);
//                this.getBusCurrentStation().passengersOnStation.remove(passenger);
//                initPassCounter++;
//                System.out.println("Bus " + this + " took " + initPassCounter + " passengers. On board - " + this.currentCapacity);
//            }
//            else break;
//
//            if((int)(this.startTime+this.stopTime(0,initPassCounter)) == 0){
//                this.busTime += 1;
//            } else {
//                this.busTime = this.startTime + (int)this.stopTime(0,initPassCounter);
//            }
//        }

        this.busTime += this.getTimeInRoad(this.getBusCurrentStation());
        this.busCurrentStation = this.getBusNextStation();
//        System.out.println("Bus " + this + " moving to station " + this.getBusCurrentStation());
    }

    @Override
    public String toString(){
        return id + " at " + busRoute;
    }
}
