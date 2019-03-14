package io.telesens.ua.inputdata;

import io.telesens.ua.Station;
import io.telesens.ua.config.AllRoutesList;
import io.telesens.ua.config.AllStationList;

import java.sql.*;

public class WriteToDB {

    public static void writeToDB(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/trafficsimulation" +
                "?verifyServerCertificate=false" + "&useSSL=false" +
                "&requireSSL=false" + "&useLegacyDatetimeCode=false" +
                "&amp"+ "&serverTimezone=UTC";

        try {
            Connection connection = DriverManager.getConnection(url,"root","root");
            if(!connection.isClosed()){
                System.out.println("Connected!");

                Statement statement = null;
                //ResultSet resultSet;
                statement = connection.createStatement();

                //Writing routes in DB
                //Creating table with all routes //////////////////////////////////////////////////////////////////////////

                String sql = "DROP table if exists routes_table";
                statement.executeUpdate(sql);

                sql = "create table routes_table (RouteNumber int primary key, `Roundabout` varchar(5) not null, " +
                        "`NumberOfBuses` int not null," +
                        "`NumberOfStations` int not null, " +
                        "`ListOfStations` VARCHAR(200) not null); ";

                statement.executeUpdate(sql);

                int listSize = AllRoutesList.getInstance().getNumberOfAllRoutes();
                for(int i = 0; i < listSize; i++){
                    int routeID = AllRoutesList.getInstance().getListOfAllRoutes().get(i).getRouteNumber();
                    String roundAbout = "false";

                    if(AllRoutesList.getInstance().getListOfAllRoutes().get(i).isRoundabout())
                        roundAbout = "true";

                    int nBuses = AllRoutesList.getInstance().getListOfAllRoutes().get(i).getNumberOfBuses();
                    int nStations = AllRoutesList.getInstance().getListOfAllRoutes().get(i).getAllStations().size();
                    StringBuilder sb = new StringBuilder();

                    for(int j = 0; j < nStations; j++){
                        String stationName = AllRoutesList.getInstance().getListOfAllRoutes().get(i).getAllStations().get(j).getName();

                            if(j == nStations - 1)
                                sb.append(stationName);
                            else
                                sb.append(stationName + ", ");

                            if((j != 0)&&(j%3 == 0))
                                sb.append("\n");
                    }

                       String stations = sb.toString();


                    sql = "insert into routes_table (RouteNumber, Roundabout, NumberOfBuses, NumberOfStations, ListOfStations) " +
                            " values ("+routeID+", '"+roundAbout+"', "+nBuses+", "+nStations+", '"+stations+"') ";
                    statement.executeUpdate(sql);
                }

                //Creating table with all stations ////////////////////////////////////////////////////////////////////////////
                sql="drop table if exists stations_table";
                statement.executeUpdate(sql);

                sql = "create table stations_table (Id int primary key, `Name` varchar(25) not null, " +
                        "`Big` varchar(5) not null," +
                        "`BelongToRoutes` varchar(100) not null); ";
                statement.executeUpdate(sql);

                int numberOfStations = AllStationList.getInstance().getNumberOfAllStations();

                for(int i = 0; i < numberOfStations; i++){
                    Station tempStation = AllStationList.getInstance().getListOfAllStations().get(i);
                    int stID = tempStation.getId();
                    String stName = tempStation.getName();

                    String big = "NO";
                        if(tempStation.isBig())
                            big = "YES";

                    StringBuilder sb = new StringBuilder();
                        for(int j = 0; j < listSize; j++){
                            if(AllRoutesList.getInstance().getListOfAllRoutes().get(j).containStation(tempStation))
                                sb.append(AllRoutesList.getInstance().getListOfAllRoutes().get(j).getRouteNumber() + " ");
                        }

                    String routesBelongTo = sb.toString();

                    sql = "insert into stations_table (Id, Name, Big, BelongToRoutes) " +
                            " values ("+stID+", '"+stName+"', '"+big+"', '"+routesBelongTo+"') ";
                    statement.executeUpdate(sql);
                }

            }connection.close();
            if(connection.isClosed()){
                System.out.println("Connection was closed. Bye, bye...");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

    }
}
