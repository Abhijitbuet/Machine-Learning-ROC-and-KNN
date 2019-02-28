package knn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class KNN {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("knn_input.txt"));
		ArrayList<Flower> data = loadData(scanner);
		simulateKFoldsValidation(data, 5);
		scanner.close();
	}
	private static void simulateKFoldsValidation(ArrayList<Flower> data, int folds) {
		Collections.shuffle(data);
		ArrayList<Flower> trainingData = new ArrayList<>();
		ArrayList<Flower> testData = new ArrayList<>();
		int partitionSize = data.size()/5;
		int K = 5;
		double accuracy = 0;
		for(int i = 0; i<5; i++) {
			for(int j = 0; j<data.size(); j++) {
				if(j>=i*partitionSize && j<i*partitionSize + partitionSize  ) {
					testData.add(data.get(j));
				}
				else 
					trainingData.add(data.get(j));
			}
			accuracy += simulateKNN(trainingData, testData, K);
			trainingData.clear();
			testData.clear();
		}
		accuracy = accuracy/folds;
		System.out.println("Average accuracy: "+ accuracy);
	}
	private static double simulateKNN(ArrayList<Flower> trainingData, ArrayList<Flower> testData, int k) {
		int correctLabel = 0;
		Comparator<Flower> flowerComparator = new Comparator<Flower>() {
			
			@Override
			public int compare(Flower o1, Flower o2) {
				
				if(o1.getDistance()<o2.getDistance())return -1;
				else return 1;
			}
		};
		for(Flower testFlower: testData) {
			for(Flower trainFlower: trainingData) {
				double distance = calculateDistance(testFlower, trainFlower);
				trainFlower.setDistance(distance);
			}
			Collections.sort(trainingData, flowerComparator);
			String speciesName = getKNNDecision(trainingData, k);
			if(speciesName.equals(testFlower.getSpeciesName()))
				correctLabel++;
		}
		return correctLabel*1.0/testData.size();
	}
	private static String getKNNDecision(ArrayList<Flower> trainingData, int k) {
		HashMap<String, Integer> speciesVotes = new HashMap<>();
		for(int i = 0; i<k ; i++) {
			Flower nearestFlower = trainingData.get(i);
			if(speciesVotes.containsKey(nearestFlower.getSpeciesName())) {
				speciesVotes.put(nearestFlower.getSpeciesName(), speciesVotes.get(nearestFlower.getSpeciesName())+1);
			}
			else {
				speciesVotes.put(nearestFlower.getSpeciesName(),1);
				
			}
		}
		String decisionSpecies = "";
		int maxVote = -1;
		for(String speciesName: speciesVotes.keySet()) {
			if(speciesVotes.get(speciesName)>maxVote) {
				maxVote = speciesVotes.get(speciesName);
				decisionSpecies = speciesName;
			}
		}
		return decisionSpecies;
	}
	private static double calculateDistance(Flower testFlower, Flower trainFlower) {
		double a = testFlower.petalLength - trainFlower.petalLength;
		double b = testFlower.petalWidth - trainFlower.petalWidth;
		double c = testFlower.sepalLength - trainFlower.sepalLength;
		double d = testFlower.sepalWidth - trainFlower.sepalWidth;
		return Math.sqrt(a*a + b*b + c*c + d*d );
	}
	private static ArrayList<Flower> loadData(Scanner scanner) {
		ArrayList<Flower> flowerData = new ArrayList<>();
		scanner.nextLine();
		while(scanner.hasNextLine()) {
			String inputLine = scanner.nextLine();
			inputLine = inputLine.replaceAll(" ", "");
			String [] inputParts = inputLine.split("\t");
			int speciesNumber = Integer.parseInt(inputParts[0]);
			String speciesName = inputParts[1];
			double petalWidth = Double.parseDouble(inputParts[2]);
			double petalLength = Double.parseDouble(inputParts[3]);
			double sepalWidth = Double.parseDouble(inputParts[4]);
			double sepalLength = Double.parseDouble(inputParts[5]);
			
			Flower flower = new Flower(speciesNumber, speciesName, petalWidth, petalLength, sepalWidth, sepalLength);
			flowerData.add(flower);
			System.out.println(flower);
		}
		return flowerData;
	}
}
