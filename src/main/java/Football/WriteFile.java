package Football;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {


    private String path;
    private boolean appendToFile;

    public WriteFile(String filePath) {

        appendToFile = false;
        path = filePath;
    }

    public WriteFile(String filePath, boolean appendValue) {

        path = filePath;
        appendToFile = appendValue;
    }

    public void writeToFile(String text) {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(path, appendToFile);
            bw = new BufferedWriter(fw);
            bw.write(text);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
