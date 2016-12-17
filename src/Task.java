import Jama.Matrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smile on 14-Dec-15.
 */
public class Task {
    private int imageWidth;
    private int imageHight;
    private int n;
    private int m;
    private int p;
    private double e;
    private BufferedImage inputImage = Main.DEFAULT_IMAGE;
    private List<VectorIn> list;
    private Matrix W;
    private Matrix W_;

    public Task(int n, int m, int p, double e ){
            imageWidth = inputImage.getWidth();
            imageHight = inputImage.getHeight();
            this.n = n;
            this.e = e;
            this.m = m;
            this.p = p;
    }

    public int decomposeImage(){
        list = new ArrayList<VectorIn>();
        VectorIn vector;
        int x = 0;
        while (x < imageWidth){
            int y = 0;
            while (y < imageHight){
                vector = new VectorIn(x, y, n, m);
                for (int i = x; i < x + n; i++){
                    for (int j = y; j < y + m; j++) {
                        if (i < imageWidth && j < imageHight){
                            Color colorPixel = new Color(inputImage.getRGB(i, j));
                            vector.addElement(setNewColor(colorPixel.getRed()));
                            vector.addElement(setNewColor(colorPixel.getGreen()));
                            vector.addElement(setNewColor(colorPixel.getBlue()));

                        } else {
                            vector.addElement(-1);
                            vector.addElement(-1);
                            vector.addElement(-1);
                        }
                    }
                }
                vector.createMatrixX();
                list.add(vector);
                y += m;
            }
            x += n;
        }
        return list.size();
    }

    private double setNewColor(double color){
        return 2*color/255-1;
    }

    public void doAction(){
        createWeightMatrix();
        init();
    }

    private void createWeightMatrix(){
        double bufferW[][] = new double[n*m*3][p];
        for (int i = 0; i < n*m*3; i++) {
            for (int j = 0; j < p; j++) {
                bufferW[i][j] = Math.random()*2 - 1;
            }
        }
        W = new Matrix(bufferW);
        W_ = W.transpose();
        normalize(W);
        normalize(W_);

    }

    private void init(){
        int t = 1;
        double E=Double.MAX_VALUE;
        while (E>e){
            int l = list.size();
            double alpha;
            double alpha_;
            E=0;
            Matrix X ;
            Matrix X_ ;
            for (int i = 0; i < l; i++) {
                X = list.get(i).getX();
                Matrix Y = X.times(W);
                X_ = Y.times(W_);
                Matrix deltaX = X_.minus(X);
                alpha=1/calculateVectorSum(X);
                alpha_=1/calculateVectorSum(Y);
                //alpha = alpha_ = 0.001;
                W = W.minus(X.transpose().times(alpha).times(deltaX).times(W_.transpose()));
                W_ = W_.minus(Y.transpose().times(alpha_).times(deltaX));
                normalize(W);
                normalize(W_);
            }
            for (int i = 0; i < l; i++) {
                X = list.get(i).getX();
                Matrix Y = X.times(W);
                X_ = Y.times(W_);
                E += getError(X, X_);
            }
            System.out.println("Iteration: "+t+"; Error: "+E);
            t++;
        }
    }

    private double getError(Matrix X, Matrix X_){
        double e=0;
        for (int i = 0; i < X.getColumnDimension(); i++) {
            e += (X_.get(0, i) - X.get(0, i))*(X_.get(0, i) - X.get(0, i));
        }
        return e;
    }
    private void normalize(Matrix matrix){
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            double sum = 0;
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                sum +=matrix.get(i, j)*matrix.get(i, j);
            }
            sum = Math.sqrt(sum);
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                matrix.set(i, j, matrix.get(i, j) / sum);
            }
        }
    }

    private void printMatrix(Matrix matrix){
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                System.out.print(matrix.get(i, j) + " ");
            }
            System.out.println();
        }
    }

    public double calculateVectorSum(Matrix currentMatrix){
        if (currentMatrix.getRowDimension()>1) {
            return 0;
        }
        double sum=1000;
        for (int i = 0; i < currentMatrix.getColumnDimension(); i++) {
            sum+=currentMatrix.get(0, i)*currentMatrix.get(0, i);
        }
        return sum;
    }

    public BufferedImage createOutputImage(){
        BufferedImage answer = new BufferedImage(imageHight, imageWidth, BufferedImage.TYPE_INT_RGB);
        for (VectorIn currVector : list){
            Matrix X = currVector.getX();
            Matrix Y = X.times(W);
            Matrix X_ = Y.times(W_);
            int xx = currVector.getStartX();
            int yy = currVector.getStartY();
            int l=0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    int r = (int) convertOut(X_.get(0, l++));
                    int g = (int) convertOut(X_.get(0, l++));
                    int b = (int) convertOut(X_.get(0, l++));
                    Color currentColor = new Color(r,g,b);
                    if (xx+i<imageHight && yy+j<imageWidth){
                        answer.setRGB(xx+i, yy+j, currentColor.getRGB());
                    }
                }
            }
        }
        return answer;
    }

    private double convertOut(double rgb){
        double ans = 255*(rgb+1)/2;
        if (ans<0) {
            ans=0;
        }
        if (ans>255) {
            ans=255;
        }
        return ans;
    }
}

