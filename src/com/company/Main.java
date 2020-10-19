package com.company;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        String path="../TrainSet";
//        String path2="../ReTestSet";
//        char c;
////        System.out.println(new File(path).mkdir());
//        System.out.println(new File(path2).mkdir());
//        for(int i=97;i <= 122;i++){
//            c = (char) i;
////            System.out.println(new File(path+"/"+c).mkdir());
//            System.out.println(new File(path2+"/"+c).mkdir());
//        }
        try {
//            BufferedImage bf =
//            ImageIO.read(new File("../TestSet/a/img011-00001.png"));

            //All input data, in train(858 images) and test(858 images) set
            int samples = 858;
            ArrayList<ImageWraper> images = new ArrayList<>();
            ArrayList<Character> alphabet = new ArrayList<>();

            for (int j = 'a'; j <= 'z'; j++) {
                File[] imagesFiles = new File("../TrainSet/"+(char)j).listFiles();
                alphabet.add((char)j);
                System.out.println((char)j+"  "+imagesFiles.length);
                for (int i = ((j - 'a') * samples/26); i < (samples/26)*(j-'a'+1); i++) {
                images.add(new ImageWraper(ImageIO.read(imagesFiles[i-(samples/26)*(j-'a')]),(char)j));
                }
            }
//Adjust values here to change size of input data and layers of NN
            Web nn = new Web(0.001, alphabet,200, 135, 90, 52, 26);
            nn.Learn(images);
            for (int j = 'a'; j <= 'z'; j++) {
                File[] imagesFiles = new File("../TestSet/"+(char)j).listFiles();
                for (int i = ((j - 'a') * samples/26); i < (samples/26)*(j-'a'+1); i++) {
                    ImageWraper tmp = new ImageWraper(ImageIO.read(imagesFiles[i-(samples/26)*(j-'a')]),(char)j);
                    System.out.println("Web says: "+nn.Determ(tmp.getConvertedImagePixelData()));
                    System.out.println("Image is: "+tmp.getSymbol()+"  Character");
                }
            }
//            ImageControl.convertTo2DUsingGetRGB(images.get(0).getImage());
            Scanner in = new Scanner(System.in);
            String str="";
            while(!str.equals("e")){
                System.out.println("input - i");
                System.out.println("exit - e");
                str = in.nextLine();
                if ("i".equals(str)) {
                    System.out.println("input path to your file");
                    str = in.nextLine();
                    System.out.println("input your character");
                    ImageWraper tmp = new ImageWraper(ImageIO.read(new File(str)), in.nextLine().toCharArray()[0]);
                    System.out.println("Web says: "+nn.Determ(tmp.getConvertedImagePixelData()));
                    System.out.println("Image is: "+tmp.getSymbol()+"  Character");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
