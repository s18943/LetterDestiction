package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImageControl {
    //to get basic input
    public void CutAlphabet(){

    }
    //to save neiron matrix
    public void Memorise(){

    }
    // Method to GetMatrixFromImage WithRGB
    public static double[][] convertTo2DUsingGetRGB(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[][] result = new double[height][width];

        for (int row = 0; row < height; row++) {
//            System.out.print("[");
            for (int col = 0; col < width; col++) {
                result[row][col] = ((0xFFFFFF - image.getRGB(col, row)) | 0xFF000000);
//                System.out.print(result[row][col]+",");
            }
//            System.out.println("]");
        }
        return result;
    }
public static Color colorToBlackAndWhite(Color input){
    int red = (int) (input.getRed() * 0.299);
    int green = (int) (input.getGreen() * 0.587);
    int blue = (int) (input.getBlue() * 0.114);
    return new Color(
            red + green + blue,
            red + green + blue,
            red + green + blue);
}
    // Method to GetMatrixFromImage ComplexAlgorithm
    public static double[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        double[][] result = new double[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (( pixels[pixel] & 0xff) << 24); // alpha
                argb += ( pixels[pixel + 1] & 0xff); // blue
                argb += (( pixels[pixel + 2] & 0xff) << 8); // green
                argb += (( pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ( pixels[pixel] & 0xff); // blue
                argb += (( pixels[pixel + 1] & 0xff) << 8); // green
                argb += (( pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }
        return result;
    }
}
