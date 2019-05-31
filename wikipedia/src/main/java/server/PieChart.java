package server;

import java.util.LinkedList;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;

import model.People;

public class PieChart extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static DefaultPieDataset chartDataset;

	public DefaultPieDataset getChartDataset() {
		return chartDataset;
	}

	public PieChart(String title, DefaultPieDataset dataset) {
		super(title);
		PieChart.chartDataset = dataset;
		setContentPane(createDemoPanel());
	}

	static LinkedList<People> peopleList = new LinkedList<People>();

	public static LinkedList<People> getPeopleList() {
		return peopleList;
	}

	public void setPeopleList(LinkedList<People> peopleList) {
		PieChart.peopleList = peopleList;
	}

	private static PieDataset createDataset() {
		DefaultPieDataset dataset = chartDataset;
		System.out.println("Intra in create Dataset ");

//		dataset.setValue("IPhone 5s", new Double(20));
//		dataset.setValue("SamSung Grand", new Double(20));
//		dataset.setValue("MotoG", new Double(40));
//		dataset.setValue("Nokia Lumia", new Double(10));
//		

		return dataset;

	}

	private static JFreeChart createChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart("People List", // chart title
				dataset, // data
				true, // include legend
				true, false);

		return chart;
	}

	public static JPanel createDemoPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}

//	public static void main(String[] args) {
//
//		PieChart demo = new PieChart("People List");
//		demo.setSize(560, 367);
//		RefineryUtilities.centerFrameOnScreen(demo);
//		demo.setVisible(true);
//	}

}
