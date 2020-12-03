package org.wigo.fileManager;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileReader {

    String pathName;
    String file;


    public FileReader(String pathName) {
        this.pathName = pathName;
        try {
            this.file = IOUtils.toString(getClass().getResourceAsStream(pathName), "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getSplitFile(String splitter) {
        List<String> splitList = new ArrayList<>();

        for(String str : this.getFile().split(splitter)) {
            splitList.add(str.trim());
        }

        return splitList;
    }

    private boolean isContainSpecialChar(String splitter) {
        String regular = "[\\!-\\/\\:-\\@\\[-\\`\\{-\\~]";
        Pattern pattern = Pattern.compile(regular);

        return pattern.matcher(splitter).matches();
    }

    public List<String> getSplitFileByLine() { return this.getSplitFile("\\n"); }
    public String getFile() { return this.file; }
}
