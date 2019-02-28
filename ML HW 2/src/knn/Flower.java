package knn;

public class Flower {
int speciesNumber;
String speciesName;
double petalWidth;
double petalLength;
double sepalWidth;
double sepalLength;
double distance;
public Flower(int speciesNumber, String speciesName, double petalWidth, double petalLength, double sepalWidth,
		double sepalLength) {

	this.speciesNumber = speciesNumber;
	this.speciesName = speciesName;
	this.petalWidth = petalWidth;
	this.petalLength = petalLength;
	this.sepalWidth = sepalWidth;
	this.sepalLength = sepalLength;
}

public double getDistance() {
	return distance;
}

public void setDistance(double distance) {
	this.distance = distance;
}

public int getSpeciesNumber() {
	return speciesNumber;
}
public void setSpeciesNumber(int speciesNumber) {
	this.speciesNumber = speciesNumber;
}
public String getSpeciesName() {
	return speciesName;
}
public void setSpeciesName(String speciesName) {
	this.speciesName = speciesName;
}
public double getPetalWidth() {
	return petalWidth;
}
public void setPetalWidth(double petalWidth) {
	this.petalWidth = petalWidth;
}
public double getPetalLength() {
	return petalLength;
}
public void setPetalLength(double petalLength) {
	this.petalLength = petalLength;
}
public double getSepalWidth() {
	return sepalWidth;
}
public void setSepalWidth(double sepalWidth) {
	this.sepalWidth = sepalWidth;
}
public double getSepalLength() {
	return sepalLength;
}
public void setSepalLength(double sepalLength) {
	this.sepalLength = sepalLength;
}
@Override
public String toString() {
	return "Flower [speciesNumber=" + speciesNumber + ", speciesName=" + speciesName + ", petalWidth=" + petalWidth
			+ ", petalLength=" + petalLength + ", sepalWidth=" + sepalWidth + ", sepalLength=" + sepalLength + "]";
}

}
