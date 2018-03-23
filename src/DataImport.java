import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.IOException;

public class DataImport {



    public static Instances getInstancesFromCSV(String filePath) throws IOException {
        try {
            DataSource source = new DataSource(filePath);
            Instances data = source.getDataSet();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }



    }


    public static Instances getInstancesFromARFF(String filePath) throws Exception {
        DataSource source = new DataSource(filePath);

        return source.getDataSet();

    }
}