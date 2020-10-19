package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageWraper {
    private BufferedImage image;
    private Character Symbol;
    private double[][] imagePixelData;

    public ImageWraper(BufferedImage image, Character symbol) {
        this.image = image;
//        this.image = new BufferedImage(15,15, image.getType());
//        Graphics2D g2d = this.image.createGraphics();
//        g2d.drawImage(image, 0, 0, 15, 15, null);
//        g2d.dispose();
        Symbol = symbol;
//        System.out.println(this.image.getWidth()+" "+this.image.getHeight());
        imagePixelData = ImageControl.convertTo2DUsingGetRGB(this.image);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Character getSymbol() {
        return Symbol;
    }

    public void setSymbol(Character symbol) {
        Symbol = symbol;
    }

    public double[] getConvertedImagePixelData() {
        double[] converted = new double[imagePixelData.length*2];
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                converted[x] += imagePixelData[x][y];
                converted[100+y] += imagePixelData[y][x];
            }
        }
        return converted;
    }
//    public double[] getConvertedImagePixelData() {
//        double[] converted = new double[imagePixelData.length*imagePixelData[0].length];
//        for (int x = 0; x < 15; x++) {
//            for (int y = 0; y < 15; y++) {
//                converted[y+(15*x)] += imagePixelData[x][y];
////                converted[15+y] += imagePixelData[y][x];
//            }
//        }
//        return converted;
//    }
}
