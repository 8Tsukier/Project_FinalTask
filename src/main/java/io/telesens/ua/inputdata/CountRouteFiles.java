package io.telesens.ua.inputdata;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountRouteFiles {
//    final static String SOMEPATH = "D:\\Projects\\IntelliJ IDEA proj\\FinalTask\\src\\main\\java\\io\\telesens\\ua\\inputdata";
    final static String MASK = "^*.xml";
    public static int numberOfRouteFiles = 0;

    public static int countFiles(File dir) throws IOException {
        Pattern pattern = Pattern.compile(MASK);

        for (File f : dir.listFiles()) {
            if (!f.isDirectory()) {
                Matcher matcher = pattern.matcher(f.getName());
                if (matcher.find()) {
                    numberOfRouteFiles += 1;
                }
            }
        }
        return numberOfRouteFiles;
    }
}
