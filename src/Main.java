import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AdditiveRegression;
import weka.classifiers.trees.M5P;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.gui.beans.ModelPerformanceChart;
import weka.gui.beans.ThresholdDataEvent;
import weka.gui.visualize.PlotData2D;


public class Main {

    public static void main(String[] args) {

        try {
            Instances train = DataImport.getInstancesFromCSV("C:/Users/Cornel-PC/Desktop/occupancy_data/datatraining - Copy.csv");
            Instances test = DataImport.getInstancesFromCSV("C:/Users/Cornel-PC/Desktop/occupancy_data/datatest - Copy.csv");
            train.setClassIndex(train.numAttributes() - 1);
            test.setClassIndex(test.numAttributes() -1);
            Classifier cls = new AdditiveRegression();
            cls.buildClassifier(train);
            Evaluation evaluation = new Evaluation(train);
            evaluation.evaluateModel(cls, test);
            System.out.println(evaluation.toSummaryString("\nResults\n======\n", false));
            final javax.swing.JFrame jf = new javax.swing.JFrame();
            jf.getContentPane().setLayout(new java.awt.BorderLayout());
            final ModelPerformanceChart as = new ModelPerformanceChart();
            PlotData2D pd = new PlotData2D(test);
            pd.setPlotName(train.relationName());
            ThresholdDataEvent roc = new ThresholdDataEvent(as, pd);
            as.acceptDataSet(roc);

            jf.getContentPane().add(as, java.awt.BorderLayout.CENTER);
            jf.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    jf.dispose();
                    System.exit(0);
                }
            });
            jf.setSize(800, 600);
            jf.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
        }


    }


