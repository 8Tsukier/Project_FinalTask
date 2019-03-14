package io.telesens.ua;

public class Arc {
    private Station fromStation;
    private Station toStation;
    private long time;

    public Arc(){}

    public Arc(Station from, Station to, long time){
        fromStation = from;
        toStation = to;
        this.time = time;
    }

    public void setFromStation(Station fromStation) {
        this.fromStation = fromStation;
    }

    public void setToStation(Station toStation) {
        this.toStation = toStation;
    }

    public Station getFromStation(){
        return fromStation;
    }

    public Station getToStation(){
        return toStation;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean containsStation(Station node) {
        return node.equals(fromStation) || node.equals(toStation);
    }

    public Station getNeighbourStation(Station node){
        if (node.equals(fromStation))
            return toStation;
        else return fromStation;
    }

    @Override
    public String toString() {
        return "Arc: " + fromStation + " - " + toStation + ". Approx time: " + time + " min.";
    }
}