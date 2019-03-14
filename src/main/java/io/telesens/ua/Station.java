package io.telesens.ua;

import io.telesens.ua.config.DaysOfWeek;
import io.telesens.ua.statistic.TotalResults;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Station {
    private int id;
    private String name;
    ConcurrentLinkedQueue <Passenger> passengersOnStation = new ConcurrentLinkedQueue();
    private boolean big;

    public Station(){}

    public Station(int id, String name, boolean big){
        this.id = id;
        this.name = name;
        this.big = big;
    }

    //Или сделать список маршрутов которым она принадлежит?
    public boolean belongToRoute(Route route){
        boolean checker = false;

        for (Arc arc: route.getRouteList()){
            if(arc.containsStation(this)){
                checker = true;
                break;
            }
        }
        return checker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBig(boolean big) {
        this.big = big;
    }

    public boolean isBig() {
        return big;
    }

    public ConcurrentLinkedQueue<Passenger> getPassengersOnStation() {
        return passengersOnStation;
    }

    public void checkStation() {
        int passengersLeft = 0;
        //Checking waiting time. If someone already left the station.

//        for(int i = 0; i < passengersOnStation.size(); i++){
//            if(passengersOnStation.get(i).getWaitingTime() > GlobalTime.getInstance().getGlobalTime()){
//                passengersOnStation.remove(i);
//                passengersLeft++;
//            }
//        }
        Iterator<Passenger> iterator = passengersOnStation.iterator();

        while (iterator.hasNext()) {
            Passenger pass = iterator.next();
            if (pass.getWaitingTime() > GlobalTime.getInstance().getGlobalTime()) {
                iterator.remove();
                passengersLeft++;
                TotalResults.getInstance().increaseNumberOfPassLeft();
            }
            //System.out.println("Passengers left - " + passengersLeft);
//            fillStation();
        }
    }

    public void fillStation(DaysOfWeek day){
        Random rnd = new Random(System.currentTimeMillis());

        switch (day){

            case Monday:{
                if(GlobalTime.getInstance().getGlobalTime() < 90){
                    for(int i = 0; i < rnd.nextInt(50)+20; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        for (int i = 0; i < rnd.nextInt(30)+10; i ++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 90)&&(GlobalTime.getInstance().getGlobalTime() < 240)){
                    for(int i = 0; i < rnd.nextInt(20); i++)
                        passengersOnStation.add(new Passenger(this));
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 240)&&(GlobalTime.getInstance().getGlobalTime() < 420)){
                    for(int i = 0; i < rnd.nextInt(15)+5; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 420)&&(GlobalTime.getInstance().getGlobalTime() < 540)){
                    for(int i = 0; i < rnd.nextInt(24)+8; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        for(int i = 0; i < rnd.nextInt(10)+5; i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 540)&&(GlobalTime.getInstance().getGlobalTime() < 660)){
                    for(int i = 0; i < rnd.nextInt(40)+20; i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(10)+5; i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 660)&&(GlobalTime.getInstance().getGlobalTime() < 720)){
                    for(int i = 0; i < rnd.nextInt(15)+5; i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(6); i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 720)&&(GlobalTime.getInstance().getGlobalTime() < 840)){
                    for(int i = 0; i < rnd.nextInt(10); i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(5)+2; i++){
                            passengersOnStation.add(new Passenger(this));
                        }
                    }
                }
            }break;

            case Tuesday:{

                if(GlobalTime.getInstance().getGlobalTime() < 60){
                    for(int i = 0; i < rnd.nextInt(35)+15; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        for (int i = 0; i < rnd.nextInt(25)+10; i ++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 60)&&(GlobalTime.getInstance().getGlobalTime() < 240)){
                    for(int i = 0; i < rnd.nextInt(10); i++)
                        passengersOnStation.add(new Passenger(this));
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 240)&&(GlobalTime.getInstance().getGlobalTime() < 420)){
                    for(int i = 0; i < rnd.nextInt(10)+2; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 420)&&(GlobalTime.getInstance().getGlobalTime() < 540)){
                    for(int i = 0; i < rnd.nextInt(15) + 5; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        for(int i = 0; i < rnd.nextInt(5)+2; i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 540)&&(GlobalTime.getInstance().getGlobalTime() < 660)){
                    for(int i = 0; i < rnd.nextInt(35) + 10; i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(6)+2; i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 660)&&(GlobalTime.getInstance().getGlobalTime() < 720)){
                    for(int i = 0; i < rnd.nextInt(10); i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(5)+1; i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 720)&&(GlobalTime.getInstance().getGlobalTime() < 840)){
                    for(int i = 0; i < rnd.nextInt(5)+1; i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(5)+1; i++){
                            passengersOnStation.add(new Passenger(this));
                        }
                    }
                }
            }break;

            case Wensday:{

                if(GlobalTime.getInstance().getGlobalTime() < 90){
                    for(int i = 0; i < rnd.nextInt(40)+20; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        for (int i = 0; i < rnd.nextInt(35)+15; i ++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 90)&&(GlobalTime.getInstance().getGlobalTime() < 240)){
                    for(int i = 0; i < rnd.nextInt(15)+5; i++)
                        passengersOnStation.add(new Passenger(this));
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 240)&&(GlobalTime.getInstance().getGlobalTime() < 420)){
                    for(int i = 0; i < rnd.nextInt(15); i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 420)&&(GlobalTime.getInstance().getGlobalTime() < 540)){
                    for(int i = 0; i < rnd.nextInt(24)+12; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        for(int i = 0; i < rnd.nextInt(15)+2; i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 540)&&(GlobalTime.getInstance().getGlobalTime() < 660)){
                    for(int i = 0; i < rnd.nextInt(40)+20; i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(15); i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 660)&&(GlobalTime.getInstance().getGlobalTime() < 720)){
                    for(int i = 0; i < rnd.nextInt(17)+3; i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(10)+2; i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 720)&&(GlobalTime.getInstance().getGlobalTime() < 840)){
                    for(int i = 0; i < rnd.nextInt(5)+1; i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(5)+2; i++){
                            passengersOnStation.add(new Passenger(this));
                        }
                    }
                }
            }break;

            case Thirsday:{

                if(GlobalTime.getInstance().getGlobalTime() < 90){
                    for(int i = 0; i < rnd.nextInt(37)+15; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        for (int i = 0; i < rnd.nextInt(20)+10; i ++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 90)&&(GlobalTime.getInstance().getGlobalTime() < 240)){
                    for(int i = 0; i < rnd.nextInt(17)+3; i++)
                        passengersOnStation.add(new Passenger(this));
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 240)&&(GlobalTime.getInstance().getGlobalTime() < 420)){
                    for(int i = 0; i < rnd.nextInt(15)+2; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 420)&&(GlobalTime.getInstance().getGlobalTime() < 540)){
                    for(int i = 0; i < rnd.nextInt(15)+5; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        for(int i = 0; i < rnd.nextInt(10)+3; i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 540)&&(GlobalTime.getInstance().getGlobalTime() < 660)){
                    for(int i = 0; i < rnd.nextInt(25) + 14; i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(10)+3; i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 660)&&(GlobalTime.getInstance().getGlobalTime() < 720)){
                    for(int i = 0; i < rnd.nextInt(12)+2; i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(10); i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 720)&&(GlobalTime.getInstance().getGlobalTime() < 840)){
                    for(int i = 0; i < rnd.nextInt(5)+1; i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(5)+2; i++){
                            passengersOnStation.add(new Passenger(this));
                        }
                    }
                }
            }break;

            case Friday:{

                if(GlobalTime.getInstance().getGlobalTime() < 90){
                    for(int i = 0; i < rnd.nextInt(20)+20; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        for (int i = 0; i < rnd.nextInt(15)+5; i ++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 90)&&(GlobalTime.getInstance().getGlobalTime() < 240)){
                    for(int i = 0; i < rnd.nextInt(15); i++)
                        passengersOnStation.add(new Passenger(this));
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 240)&&(GlobalTime.getInstance().getGlobalTime() < 420)){
                    for(int i = 0; i < rnd.nextInt(10) + 1; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 420)&&(GlobalTime.getInstance().getGlobalTime() < 540)){
                    for(int i = 0; i < rnd.nextInt(20)+12; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        for(int i = 0; i < rnd.nextInt(10); i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 540)&&(GlobalTime.getInstance().getGlobalTime() < 660)){
                    for(int i = 0; i < rnd.nextInt(21)+11; i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(15); i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 660)&&(GlobalTime.getInstance().getGlobalTime() < 720)){
                    for(int i = 0; i < rnd.nextInt(20); i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(10); i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 720)&&(GlobalTime.getInstance().getGlobalTime() < 840)){
                    for(int i = 0; i < rnd.nextInt(10); i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(10); i++){
                            passengersOnStation.add(new Passenger(this));
                        }
                    }
                }
            }break;

            case Saturday:{

                if(GlobalTime.getInstance().getGlobalTime() < 90){
                    for(int i = 0; i < rnd.nextInt(25); i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        for (int i = 0; i < rnd.nextInt(15)+5; i ++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 90)&&(GlobalTime.getInstance().getGlobalTime() < 240)){
                    for(int i = 0; i < rnd.nextInt(20); i++)
                        passengersOnStation.add(new Passenger(this));
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 240)&&(GlobalTime.getInstance().getGlobalTime() < 420)){
                    for(int i = 0; i < rnd.nextInt(15); i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 420)&&(GlobalTime.getInstance().getGlobalTime() < 540)){
                    for(int i = 0; i < rnd.nextInt(10)+2; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        for(int i = 0; i < rnd.nextInt(15); i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 540)&&(GlobalTime.getInstance().getGlobalTime() < 660)){
                    for(int i = 0; i < rnd.nextInt(20)+5; i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(15); i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 660)&&(GlobalTime.getInstance().getGlobalTime() < 720)){
                    for(int i = 0; i < rnd.nextInt(10); i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(8); i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 720)&&(GlobalTime.getInstance().getGlobalTime() < 840)){
                    for(int i = 0; i < rnd.nextInt(5); i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(5); i++){
                            passengersOnStation.add(new Passenger(this));
                        }
                    }
                }
            }break;

            case Sunday:{

                if(GlobalTime.getInstance().getGlobalTime() < 90){
                    for(int i = 0; i < rnd.nextInt(20); i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        for (int i = 0; i < rnd.nextInt(20)+5; i ++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 90)&&(GlobalTime.getInstance().getGlobalTime() < 240)){
                    for(int i = 0; i < rnd.nextInt(10); i++)
                        passengersOnStation.add(new Passenger(this));
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 240)&&(GlobalTime.getInstance().getGlobalTime() < 420)){
                    for(int i = 0; i < rnd.nextInt(10); i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 420)&&(GlobalTime.getInstance().getGlobalTime() < 540)){
                    for(int i = 0; i < rnd.nextInt(10)+2; i++)
                        passengersOnStation.add(new Passenger(this));

                    if(big){
                        for(int i = 0; i < rnd.nextInt(10); i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 540)&&(GlobalTime.getInstance().getGlobalTime() < 660)){
                    for(int i = 0; i < rnd.nextInt(10)+5; i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(15); i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 660)&&(GlobalTime.getInstance().getGlobalTime() < 720)){
                    for(int i = 0; i < rnd.nextInt(10); i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(10); i++)
                            passengersOnStation.add(new Passenger(this));
                    }
                }

                if((GlobalTime.getInstance().getGlobalTime() >= 720)&&(GlobalTime.getInstance().getGlobalTime() < 840)){
                    for(int i = 0; i < rnd.nextInt(10); i++){
                        passengersOnStation.add(new Passenger(this));
                    }

                    if(big){
                        for(int i = 0; i < rnd.nextInt(5); i++){
                            passengersOnStation.add(new Passenger(this));
                        }
                    }
                }
            }break;

            default:{
                break;
            }
        }

    }

    @Override
    public String toString(){
        if(big)
            return "\"" + this.name+ "\"" + " big station";
        else
            return "\"" + this.name + "\"" + " station";
    }
}
