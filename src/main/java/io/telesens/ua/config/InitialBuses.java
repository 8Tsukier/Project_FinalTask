package io.telesens.ua.config;

import io.telesens.ua.Bus;
import io.telesens.ua.Route;
import io.telesens.ua.inputdata.ReadRoutes;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InitialBuses {
    public static Bus[] initialAllBus(){

        try {
            ReadRoutes.readXMLs();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        List<Bus> myDynamicList = new ArrayList<>();

        Iterator<Route> iterator = AllRoutesList.getInstance().getListOfAllRoutes().iterator();

        while (iterator.hasNext()){
            Route someRoute = iterator.next();
            int nbuses = someRoute.getNumberOfBuses();
            int startTimeBus = 0;

            for(int i = 0; i < nbuses; i++){
                Bus someBus = new Bus(i+1, someRoute);
                someBus.setStartTime(startTimeBus);
                startTimeBus += 10;
                myDynamicList.add(someBus);
            }
        }

        Bus[] myBuses = new Bus[myDynamicList.size()];

        for(int i = 0; i < myBuses.length; i++){
            myBuses[i] = myDynamicList.get(i);
        }

        return myBuses;
    }
}
