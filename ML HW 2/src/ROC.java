import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ROC {
	
 public static void main(String[] args) throws FileNotFoundException {
	Scanner scanner = new Scanner(new File("input.txt"));
	ArrayList<SoundData> data = loadData(scanner);
	double [][] allTPR_FPR= getAllDataWiseTPR_FPR(data);
	printAllTPR_FPRWithThreshold(allTPR_FPR, data);
	double AUCFromDataPoints  = getAUCFromDataPoints(allTPR_FPR, data);
	System.out.println("AUC = "+AUCFromDataPoints);
	scanner.close();
 }

private static double getAUCFromDataPoints(double[][] allTPR_FPR, ArrayList<SoundData> data) {
	int row = allTPR_FPR.length;
	
	double area = 0;
	double previousX = allTPR_FPR[0][1] ;
	double previousY = allTPR_FPR[0][0];
	for(int i = 1; i<row;i++) {
		double newX = allTPR_FPR[i][1];
		double newY = allTPR_FPR[i][0];
		if(newX!= previousX && newY!= previousY) {
			area+= previousY* (Math.abs(previousX - newX));
			System.out.println(previousY +" multiplied by ("+previousX+ "-" + newX+")");
			previousX = newX;
			
			System.out.println(" Area = " + area);
		}
		previousY = newY;
	}
	return area;
}

private static void printAllTPR_FPRWithThreshold(double[][] allTPR_FPR, ArrayList<SoundData> data) {
	int row = allTPR_FPR.length;
	int column = allTPR_FPR[0].length;
	for(int i = 0; i<row;i++) {
		System.out.print(data.get(i).getScore()+"\t");
		for(int j = 0; j<column;j++) {
			System.out.print(round(allTPR_FPR[i][j])+"\t");
		}
		System.out.println();
	}
}

private static void printAllTPR_FPR(double[][] allTPR_FPR) {
	int row = allTPR_FPR.length;
	int column = allTPR_FPR[0].length;
	for(int i = 0; i<row;i++) {
		for(int j = 0; j<column;j++) {
			System.out.print(round(allTPR_FPR[i][j])+"\t");
		}
		System.out.println();
	}
}

private static double[][] getAllDataWiseTPR_FPR(ArrayList<SoundData> data) {
	double [][]allTPR_FPR = new double[data.size()][2];
	int i = 0;
	for(SoundData dataPoint: data) {
		allTPR_FPR[i] = calculateTPR_FPR(data, dataPoint.getScore());
		i++;
	}
	return allTPR_FPR;
}

private static double[] calculateTPR_FPR(ArrayList<SoundData> data, double threshold) {
	double [] TPR_FPR = new double[2];
	double tp = 0, fp = 0, fn = 0, tn = 0;
	for(SoundData dataPoint: data) {
		String inferredLabel = "";
		if(dataPoint.getScore()<threshold) {
			inferredLabel = "a";
		}
		else {
			inferredLabel = "d";
		}
		if(dataPoint.getLabel().equals("d")&&dataPoint.getLabel().equals(inferredLabel)) {
			tp++;
		}
		else if(dataPoint.getLabel().equals("d")&& !dataPoint.getLabel().equals(inferredLabel)) {
			fn++;
		}
		else if(dataPoint.getLabel().equals("a") && !dataPoint.getLabel().equals(inferredLabel)) {
			fp++;
		}
		else {
			tn++;
		}
	}
	TPR_FPR[0] = tp/(tp + fn);
	TPR_FPR[1] = fp/(tn + fp);
	return TPR_FPR;
}
private static double round(double d) {
	return Math.round(d*1000)/1000.0;
}
private static ArrayList<SoundData> loadData(Scanner scanner) {
	ArrayList<SoundData> data = new ArrayList<>();
	scanner.nextLine();
	while(scanner.hasNextLine()) {
		String inputLine = scanner.nextLine();
		String [] inputParts = inputLine.split(" ");
		String id = inputParts[0];
		double score = Double.parseDouble(inputParts[1]);
		String label = inputParts[2];
		SoundData inputData = new SoundData(id, score, label);
		data.add(inputData);
	}
	return data;
}
}
