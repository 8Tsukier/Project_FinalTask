package io.telesens.ua;

import io.telesens.ua.config.AllRoutesList;

import java.util.List;
import java.util.Random;

public class Passenger {
    private int generatedTime;
    private Station stationFrom;
    private Station stationTo;
    private int patienceLevel;
    private int waitingTime;

    public Passenger(){}

    public Passenger (Station from){
        generatedTime = GlobalTime.getInstance().getGlobalTime(); //Remembering the creation time!
        stationFrom = from;
        Random rnd = new Random(System.currentTimeMillis());
        patienceLevel = rnd.nextInt(3);

        switch (patienceLevel){
            case 0:
                waitingTime = generatedTime + 30; //30 min to wait
                break;
            case 1:
                waitingTime = generatedTime + 25; //25 min for example
                break;
            case 2:
                waitingTime = generatedTime + 15; //15 min and leave
                break;
            default:
                break;
        }

        Route thisPassengerRoot = new Route();
        for(int i = 0; i < AllRoutesList.getInstance().getListOfAllRoutes().size(); i++){
            if(from.belongToRoute(AllRoutesList.getInstance().getListOfAllRoutes().get(i))){
                thisPassengerRoot = AllRoutesList.getInstance().getListOfAllRoutes().get(i);
                break;
            }
        }

        do {
            List<Station> listOfPossibleStations = thisPassengerRoot.getAllStations();
            int size = listOfPossibleStations.size();
            int random = rnd.nextInt(size);
            stationTo = listOfPossibleStations.get(random);
        }while (stationTo.equals(stationFrom));
    }

    public Station getStationFrom() {
        return stationFrom;
    }

    public Station getStationTo() {
        return stationTo;
    }

    public double getWaitingTime() {
        return waitingTime;
    }
}
