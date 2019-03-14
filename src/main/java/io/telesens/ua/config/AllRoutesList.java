package io.telesens.ua.config;

import io.telesens.ua.Route;

import java.util.ArrayList;

public class AllRoutesList {

    private static AllRoutesList instance;

    private ArrayList<Route> listOfAllRoutes = new ArrayList();

    public synchronized static AllRoutesList getInstance() {
        if (instance == null)
            instance = new AllRoutesList();
        return instance;
    }

    public synchronized ArrayList<Route> getListOfAllRoutes(){
        return listOfAllRoutes;
    }

    public synchronized void inputRoute(Route route){
        listOfAllRoutes.add(route);
    }

    public synchronized int getNumberOfAllRoutes(){
        return listOfAllRoutes.size();
    }
}
