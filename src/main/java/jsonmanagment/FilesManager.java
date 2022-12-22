package jsonmanagment;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Paths;

public class FilesManager {

    public static File getFile(String dataLocation) {
        return Paths.get(formatPathToJsonFile(dataLocation)).toFile();
    }

    @NotNull
    private static String formatPathToJsonFile(String dataLocation) {
        if(!dataLocation.startsWith("C:") && !dataLocation.startsWith("/")){
            String pd = "/";
            if (System.getProperty("os.name").contains("Windows")) {
                pd = "\\";
            }
            String[] dataLocationList = dataLocation.split("\\.");
            String lastItem = dataLocationList[dataLocationList.length - 1];
            return Paths.get("").toAbsolutePath() + pd + dataLocation.replace(".", pd)
                    .replace(pd + lastItem, "." + lastItem);
        }
        return dataLocation;
    }
}
