package io.telesens.ua.statistic;

import java.sql.*;

public class TotalResults {
    private static TotalResults instance;

    private int numberOfPassLeft;
    private int numberOfPassTravel;
    private int numberOfRounds;
    private int numberOfDaysInSimul;

    public synchronized static TotalResults getInstance() {
        if (instance == null)
            instance = new TotalResults();
        return instance;
    }

    public void increaseNumberOfPassLeft(){
        numberOfPassLeft += 1;
    }

    public void increaseNumberOfPassTravelled(int number){
        numberOfPassTravel += number;
    }

    public void increaseNumberOfRounds(){
        numberOfRounds += 1;
    }

    public void increaseNumberOfDaysInSimulation(){
        numberOfDaysInSimul += 1;
    }

    public void printResults(long startTime){
        numberOfPassLeft = numberOfPassLeft - numberOfPassTravel;
        float endTime = (System.currentTimeMillis() - startTime)/1000F;
        System.out.println("\n The results of simulation is following :");
        System.out.println("Simulation took : " + endTime + " seconds");
        System.out.println("Number of days passed in the simulation: " + numberOfDaysInSimul);
        System.out.println("All buses in total completed " + numberOfRounds + " rounds");
        System.out.println("Number of passengers that traveled: " + numberOfPassTravel);
        System.out.println("Number of passengers that left: " + numberOfPassLeft);

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
        ResultSet resultSet;
        statement = connection.createStatement();

            String sql = "create table if not exists result_table (N int primary key auto_increment, `DurationLocal` double not null, " +
                    "`DaysInSimulation` int not null," +
                    "`TotalRounds` int not null, " +
                    "`PassTraveled` int not null, " +
                    "`PassLeft` int not null); ";

            statement.executeUpdate(sql);

            sql = "insert into  result_table (DurationLocal, DaysInSimulation, TotalRounds, PassTraveled, PassLeft) " +
                    " values ("+endTime+", "+numberOfDaysInSimul+", "+numberOfRounds+", "+numberOfPassTravel+", "+numberOfPassLeft+") ";

            statement.executeUpdate(sql);

            }connection.close();
            if(connection.isClosed()){
                System.out.println("Connection was closed. Bye, bye...");
            }

    } catch (SQLException e) {
        System.out.println(e);
    }

    }
}
