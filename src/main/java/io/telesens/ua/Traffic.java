package io.telesens.ua;

import io.telesens.ua.config.DaysOfWeek;
import java.util.Iterator;

import io.telesens.ua.statistic.TotalResults;
import org.apache.log4j.Logger;

/**
 * @author Илья
 * Class that simulates thread of moving busse, gettin in/out passengers.
 */
public class Traffic implements Runnable {
    Bus[] bustoMove;
    DaysOfWeek dayOfWeek;
    int numberOfWeeks = 1;

    private static final Logger log = Logger.getLogger(Demo.class);

    public Traffic(Bus[] bus) {
        bustoMove = bus;
    }

    public void getHowLong(int numberOfWeeks){
        this.numberOfWeeks = numberOfWeeks;
    }

    //@Override
    public void run(){
        for(int i = 0; i < numberOfWeeks; i ++){
            runAWeek();
            GlobalTime.getInstance().setWeekNumber(i+1);
        }
    }

    public void runAWeek() {

        //Первоначальная загрузка перед стартом маршрута!
        dayOfWeek = DaysOfWeek.Monday;
        int dayCounter = 0;

        while (dayCounter < 7) {
//            System.out.println("Start moving at 8:00 AM on " + dayOfWeek);
            int weekNumber = GlobalTime.getInstance().getWeekNumber();
            log.info("Week №" + weekNumber + ". Start moving: " + dayOfWeek + " - 8:00 AM");

            //The day is starting. 8am. Continue, while it is 10pm.
            while (GlobalTime.getInstance().getGlobalTime() <= 840) { //Until the day is over!

                for (Bus bus : bustoMove) {

                    int passOff = 0;
                    int passIn = 0;

                    if(bus.getStartTime() == GlobalTime.getInstance().getGlobalTime())
                        bus.initialStation();

                    if (((bus.busTime + 40) >= 840) && (bus.getBusCurrentStation().equals(bus.busStartStation)))
                        break;

                    if (bus.busTime == GlobalTime.getInstance().getGlobalTime()) {

                        bus.getBusCurrentStation().checkStation();
                        bus.getBusCurrentStation().fillStation(dayOfWeek); //Modify with day of week


                        log.info(GlobalTime.getInstance().timeForPrint() + " - Bus " + bus + " arrived at " + bus.getBusCurrentStation());
//                        System.out.println("Bus " + bus + " arrived at " + bus.getBusCurrentStation());
                        passOff = bus.takeOffPassengers();

                        if (bus.getCurrentCapacity() < bus.capacity) {
                            Iterator<Passenger> iterator = bus.getBusCurrentStation().passengersOnStation.iterator();
                            while (iterator.hasNext()) {
                                Passenger passenger = iterator.next();

                                if (bus.canTakePassenger(passenger)) {
                                    bus.getPassengers(passenger);
                                    bus.getBusCurrentStation().getPassengersOnStation().remove(passenger);
                                    //iterator.remove(); //Rewrited with Iterator to awoid the java.util.ConcurrentModificationException. Seems helped. Not sure.
                                    //bus.getBusCurrentStation().passengersOnStation.remove(passenger);
                                    passIn += 1;
                                } else break;
                            }
                        }
//                    for (Passenger passenger : bus.getBusCurrentStation().passengersOnStation) {
//
//                        if(bus.getBusCurrentStation().passengersOnStation == null)
//                            break;
//
//                        if (bus.canTakePassenger(passenger)){
//                            bus.getPassengers(passenger);
//                            bus.getBusCurrentStation().passengersOnStation.remove(passenger);
//                            passIn += 1;
//                        }
//                        else break;
//                    }

//                        System.out.println("Bus " + bus + " took off - " + passOff + ", took in - " + passIn + ". On board - " + bus.getCurrentCapacity());
                        log.info(GlobalTime.getInstance().timeForPrint() + " - Bus " + bus + " took off - " + passOff + ", took in - " + passIn + ". On board - " + bus.getCurrentCapacity());
                        bus.busTime += bus.stopTime(passOff, passIn);
                        bus.busCurrentStation = bus.getBusNextStation();
                        bus.busTime += bus.getTimeInRoad(bus.getBusCurrentStation());
                    }
                }

                //System.out.println(GlobalTime.getInstance().getGlobalTime());
                GlobalTime.getInstance().timeRuns();
            }

//            System.out.println(dayOfWeek + " is over! ");
            log.info("Week №" + weekNumber + " 22:00 PM. " + dayOfWeek + " traffic is over!");

            for (Bus bus : bustoMove) {
                log.info("Bus " + bus + " made " + bus.routePerDayCounter + " rounds this day.");
//                System.out.println("Bus " + bus + " made " + bus.routePerDayCounter + " rounds this day.");
            }

            TotalResults.getInstance().increaseNumberOfDaysInSimulation();
            dayOfWeek = dayOfWeek.nextDay();
            GlobalTime.getInstance().timerDropStart();
            dayCounter++;

        }
    }
}