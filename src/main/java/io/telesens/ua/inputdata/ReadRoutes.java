package io.telesens.ua.inputdata;

import io.telesens.ua.Arc;
import io.telesens.ua.Route;
import io.telesens.ua.Station;
import io.telesens.ua.config.AllRoutesList;
import io.telesens.ua.config.AllStationList;
import org.xml.sax.Attributes;
        import org.xml.sax.SAXException;
        import org.xml.sax.XMLReader;
        import org.xml.sax.helpers.DefaultHandler;

        import javax.xml.parsers.ParserConfigurationException;
        import javax.xml.parsers.SAXParser;
        import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

import static io.telesens.ua.inputdata.CountRouteFiles.countFiles;
import static io.telesens.ua.inputdata.WriteToDB.writeToDB;

public class ReadRoutes{
    final static String SOMEPATH = "D:\\Projects\\IntelliJ IDEA proj\\FinalTask\\src\\main\\java\\io\\telesens\\ua\\inputdata";

    public static void readXMLs() throws IOException, ParserConfigurationException, SAXException{

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        MyHandler handler = new MyHandler();
        xmlReader.setContentHandler(handler);

        int numberOfFiles = countFiles(new File(SOMEPATH));
        for(int i = 1; i < numberOfFiles + 1; i++) {
            String docName = "D://Projects/IntelliJ IDEA proj/FinalTask/src/main/java/io/telesens/ua/inputdata/Route" + i + ".xml";
            xmlReader.parse(docName);

            int routeNumber = handler.getRouteNumber();
            List<Arc> arcList = handler.getListOfArc();
            int roundAbout = handler.getRoundAbout();
            int nBuses = handler.getnBuses();

            Route route = new Route(arcList, routeNumber, roundAbout);
            route.setNumberOfBuses(nBuses);
            AllRoutesList.getInstance().inputRoute(route);

//        for(int j = 0; j < route.getRouteList().size(); j++){
//            System.out.println(route.getRouteList().get(j));
//        }
//        System.out.println();
        }

        writeToDB();
    }

    private static class MyHandler extends DefaultHandler {
        static final String ROUTE_TAG = "Route";
        static final String ARC_TAG = "Arc";
        static final String STATION_TAG = "Station";
        static final String NAME_TAG = "name";

        static final String ROUTE_NUMBER_ATTRIBUTE = "number";
        static final String ROUTE_ROUNDABOUT_ATTRIBUTE = "roundabout";
        static final String ROUTE_NUMBER_OF_BUSES_ATTRIBUTE = "nbuses";
        static final String ARC_TIME_ATTRIBUTE = "time";
        static final String STATION_ID_ATTRIBUTE = "id";
        static final String STATION_BIG_ATTRIBUTE = "big";

        private List<Arc> list = new ArrayList();
        private Arc currentArc;
        private Station currentStation;
        private String currentElement;

        int routeNumber;
        int roundAbout;
        int nBuses;
        int helpCounter = 0;

        List<Arc> getListOfArc(){
            return list;
        }

        int getRouteNumber(){
            return routeNumber;
        }

        int getRoundAbout(){
            return roundAbout;
        }

        int getnBuses(){
            return nBuses;
        }

        public void startDocument() throws SAXException{
//            System.out.println("Starting XML parsing... Good Luck!");
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes){
            currentElement = qName;

            switch (currentElement){

                case ROUTE_TAG:{
                    //HelpClass helpClass = new HelpClass();
                    list = new ArrayList<>();

                    routeNumber = Integer.valueOf(attributes.getValue(ROUTE_NUMBER_ATTRIBUTE));
                    roundAbout = Integer.valueOf(attributes.getValue(ROUTE_ROUNDABOUT_ATTRIBUTE));
                    nBuses = Integer.valueOf(attributes.getValue(ROUTE_NUMBER_OF_BUSES_ATTRIBUTE));
                } break;

                case ARC_TAG:{
                    currentArc = new Arc();
                    currentArc.setTime(Integer.valueOf(attributes.getValue(ARC_TIME_ATTRIBUTE)));
                }break;

                case STATION_TAG:{
                    if((helpCounter == 0)||(helpCounter == 1)){
                        currentStation = new Station ();
                        currentStation.setId(Integer.valueOf(attributes.getValue(STATION_ID_ATTRIBUTE)));
                        currentStation.setBig(Boolean.valueOf(attributes.getValue(STATION_BIG_ATTRIBUTE)));
                    }

                }break;

                default:{ }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException{
            String text = new String(ch, start, length);

            if(text.contains("<") || currentElement == null){
                return;
            }

            switch (currentElement){

                case NAME_TAG:{
                    currentStation.setName(text);
                }break;

                default:{
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException{

            switch (qName){
                case STATION_TAG:{
                    if(AllStationList.getInstance().containStation(currentStation) == null) {
                        if (helpCounter == 0) {
                            AllStationList.getInstance().inputStation(currentStation);
                            currentArc.setFromStation(currentStation);
                            currentStation = null;
                            helpCounter++;
                        } else if (helpCounter == 1) {
                            AllStationList.getInstance().inputStation(currentStation);
                            currentArc.setToStation(currentStation);
                            currentStation = null;
                            helpCounter = 0;
                        }
                    }else {
                        if (helpCounter == 0) {
                            currentArc.setFromStation(AllStationList.getInstance().containStation(currentStation));
                            currentStation = null;
                            helpCounter++;
                        } else if (helpCounter == 1) {
                            AllStationList.getInstance().inputStation(AllStationList.getInstance().containStation(currentStation));
                            currentArc.setToStation(currentStation);
                            currentStation = null;
                            helpCounter = 0;
                        }
                    }

                }break;

                case ARC_TAG:{
                    list.add(currentArc);
                    currentArc = null;
                }break;

                default:{
                    //no-no
                }
            }

            currentElement = null;
        }

        public void endDocument() throws SAXException{
//            System.out.println("Mission complete!");
        }

    }
}