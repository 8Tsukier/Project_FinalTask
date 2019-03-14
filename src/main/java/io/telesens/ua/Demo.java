package io.telesens.ua;

import io.telesens.ua.config.InitialBuses;
import io.telesens.ua.statistic.TotalResults;

import java.util.Scanner;

public class Demo{
    private static long startTime;

    public static void main(String[] args) {

        Traffic myTraffic = new Traffic(InitialBuses.initialAllBus());

//        System.out.println("How many weeks to simulate: ");
//        Scanner scanner = new Scanner(System.in);
//        myTraffic.getHowLong(scanner.nextInt());

        int n = Integer.valueOf(args[0]);
        myTraffic.getHowLong(n);
        startTime = System.currentTimeMillis();
//        System.out.println("Ok!");
        //log.info("Ok!");
        myTraffic.run();

        TotalResults.getInstance().printResults(startTime);

    }
}