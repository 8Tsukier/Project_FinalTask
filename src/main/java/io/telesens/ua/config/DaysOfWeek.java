package io.telesens.ua.config;

public enum DaysOfWeek {
    Monday(88.1),
    Tuesday(80.2),
    Wensday(76.2),
    Thirsday(77.4),
    Friday(70.9),
    Saturday(50.5),
    Sunday(37.3);

    private double loadPercent;

    private DaysOfWeek(double loadPercent){
        this.loadPercent = loadPercent;
    }

    double getLoadPercent(){
        return loadPercent;
    }

    public DaysOfWeek nextDay(){
        DaysOfWeek nextDay = null;
            for(int i = 0; i < DaysOfWeek.values().length; i++){
                if((DaysOfWeek.values()[i] == this)&(i < DaysOfWeek.values().length - 1)){
                    nextDay = DaysOfWeek.values()[i+1];
                    break;
                }
            }

            if(nextDay == null)
                nextDay = Monday;

        return nextDay;
    }

    @Override
    public String toString() {
        return name() + " : passenger average load percentage - " + loadPercent + "%";
    }
}
