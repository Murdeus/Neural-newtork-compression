/**
 * Created by smile on 14-Dec-15.
 */
import Jama.Matrix;

import java.util.ArrayList;
import java.util.List;

public class VectorIn {
    private int hight;
    private int width;
    private int startX;
    private int startY;
    private List<Double> vectorX;
    private Matrix X;

    public VectorIn(int startX, int startY, int width, int hight){
        vectorX = new ArrayList<Double>();
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.hight = hight;
    }

    public void createMatrixX(){
        double bufferX[][] = new double[1][vectorX.size()];
        for (int i = 0; i < vectorX.size(); i++){
            bufferX[0][i] = vectorX.get(i);
        }
        X = new Matrix(bufferX);
    }

    public void addElement(double newElement){
        vectorX.add(newElement);
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    private void printSize(){
        System.out.println(startX + " " + startY + " " + width + " " + hight);
    }

    public Matrix getX() {
        return X;
    }
}
