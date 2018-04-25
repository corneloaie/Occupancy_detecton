import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class DataImport {


    public static Instances getInstancesFromARFF(String filePath) throws Exception {
        DataSource source = new DataSource(filePath);

        return source.getDataSet();

    }
}