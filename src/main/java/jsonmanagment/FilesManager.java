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
        if (!dataLocation.startsWith("C:")) {
            String[] dataLocationList = dataLocation.split("\\.");
            String lastItem = dataLocationList[dataLocationList.length - 1];
            return Paths.get("").toAbsolutePath() + "\\" + dataLocation.replace(".", "\\").replace("\\" + lastItem, "." + lastItem);
        }
        return dataLocation;
    }
}
