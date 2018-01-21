package Football;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ReadFile {

    private String path;

    public ReadFile(String file_path) {
        path = file_path;
    }

    public ArrayList<String> readFromFile() {
        FileReader fr = null;
        BufferedReader br = null;

        ArrayList<String> textData = new ArrayList<>();

        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                textData.add(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return textData;
    }


}
