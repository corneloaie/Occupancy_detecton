import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.gui.beans.ModelPerformanceChart;
import weka.gui.beans.ThresholdDataEvent;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
import weka.gui.visualize.PlotData2D;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        try {

            Instances train = DataImport.getInstancesFromARFF("C:/Users/Cornel-PC/Desktop/occupancy_data/datatraining - Copy.arff");
            Instances test = DataImport.getInstancesFromARFF("C:/Users/Cornel-PC/Desktop/occupancy_data/datatest - Copy.arff");
            BufferedReader attrfile = new BufferedReader(new FileReader("C:/Users/Cornel-PC/Desktop/occupancy_data/attributes.txt"));
            List<Integer> myList = new ArrayList<>();
            String line;

            while ((line = attrfile.readLine()) != null) {
                for (int n = 0; n < train.numAttributes(); n++) {
                    if (train.attribute(n).name().equalsIgnoreCase(line)) {
                        if (!myList.contains(n))
                            myList.add(n);
                    }
                }
            }

            int[] attrs = myList.stream().mapToInt(i -> i).toArray();
            Remove remove = new Remove();
            remove.setAttributeIndicesArray(attrs);
            remove.setInvertSelection(false);
            remove.setInputFormat(train); // init filter

            Instances filtered = Filter.useFilter(train, remove);


            filtered.setClassIndex(filtered.numAttributes() - 1);
            test.setClassIndex(test.numAttributes() -1);
            Classifier cls = new J48();
            cls.buildClassifier(filtered);
            Evaluation evaluation = new Evaluation(filtered);
            evaluation.evaluateModel(cls, test);
            System.out.println(evaluation.toSummaryString("\nResults\n======\n", false));


            // display graphs
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


            // display classifier
            final javax.swing.JFrame jFrame =
                    new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
            jFrame.setSize(4000, 4000);
            jFrame.getContentPane().setLayout(new BorderLayout());
            TreeVisualizer tv = new TreeVisualizer(null, ((J48) cls).graph(),
                    new PlaceNode2());
            jFrame.getContentPane().add(tv, BorderLayout.CENTER);
            jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    jFrame.dispose();
                }
            });

            jFrame.setVisible(true);
            tv.fitToScreen();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
        }


    }


