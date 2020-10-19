package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Web {
    ArrayList<Character> alphabet;
    private double learningRate;
    private NeuronLayer[] layers;
    double[] outputs;

    public Web(double learningRate, ArrayList<Character> alphabet, int... sizes) {
        this.alphabet=alphabet;
        this.learningRate = learningRate;
        layers = new NeuronLayer[sizes.length];
        for (int i = 0; i < sizes.length-1; i++) {
            layers[i] = new NeuronLayer(sizes[i], sizes[i + 1]);
        }
        layers[sizes.length-1] = new NeuronLayer(sizes[sizes.length-1], sizes[sizes.length-1]);
    }

    public void Learn(ArrayList<ImageWraper> trainSet){
        Collections.shuffle(trainSet);
       int epochs = 5000;
        for (int i = 1; i < epochs; i++) {
            int right = 0;
            double errorSum = 0;
            int batchSize = 50;
            for (int j = 0; j < batchSize; j++) {
                int imgIndex = (int)(Math.random()*trainSet.size());
                double[] targets = new double['z'-'a'+1];
                char symbol = trainSet.get(imgIndex).getSymbol();
                targets[symbol-'a'] = 1;

                outputs = trainSet.get(imgIndex).getConvertedImagePixelData();

                if(symbol-'a' == maxActivity(outputs)) right++;
                for (int k = 0; k < 26; k++) {
                    errorSum += (targets[k] - outputs[k]) * (targets[k] - outputs[k]);
                }
                backpropagation(targets);
            }
            System.out.println("epoch: " + i + ". correct: " + right + ". error: " + errorSum);
        }
    }

    public char Determ(double[] outputs){
        return (char)(maxActivity(outputs)+'a');
    }

    public void backpropagation(double[] targets) {
        double[] errors = new double[layers[layers.length - 1].size];
        for (int i = 0; i < layers[layers.length - 1].size; i++) {
            errors[i] = targets[i] - layers[layers.length - 1].neurons[i];
        }
        for (int k = layers.length - 2; k >= 0; k--) {
            NeuronLayer l = layers[k];
            NeuronLayer l1 = layers[k + 1];
            double[] errorsNext = new double[l.size];
            double[] gradients = new double[l1.size];
            for (int i = 0; i < l1.size; i++) {
                gradients[i] = errors[i] * sigmoid_prime(layers[k + 1].neurons[i]);
                gradients[i] *= learningRate;
            }
            double[][] deltas = new double[l1.size][l.size];
            for (int i = 0; i < l1.size; i++) {
                for (int j = 0; j < l.size; j++) {
                    deltas[i][j] = gradients[i] * l.neurons[j];
                }
            }
            for (int i = 0; i < l.size; i++) {
                errorsNext[i] = 0;
                for (int j = 0; j < l1.size; j++) {
                    errorsNext[i] += l.weights[i][j] * errors[j];
                }
            }
            errors = new double[l.size];
            System.arraycopy(errorsNext, 0, errors, 0, l.size);
            double[][] weightsNew = new double[l.weights.length][l.weights[0].length];
            for (int i = 0; i < l1.size; i++) {
                for (int j = 0; j < l.size; j++) {
                    weightsNew[j][i] = l.weights[j][i] + deltas[i][j];
                }
            }
            l.weights = weightsNew;
            for (int i = 0; i < l1.size; i++) {
                l1.biases[i] += gradients[i];
            }
        }
    }
    private char maxActivity(double[] outputss) {
      for (int l = 1; l < layers.length; l++) {
            layers[l-1].setNeurons(outputss);
          outputss = layers[l].feedforward(layers[l-1]).neurons;
        }
        outputs=outputss;
        int maxActivity = 0;
        double maxNeuronWeight = -1;
        for (int k = 0; k < 26; k++) {
            if(outputs[k] > maxNeuronWeight) {
                maxNeuronWeight = outputs[k];
                maxActivity = k;
            }
        }
        return (char)(maxActivity);
    }
    private double sigmoid_prime(double z) {
//            """Derivative of the sigmoid function."""
        return NeuronLayer.sigmoid(z) * (1 - NeuronLayer.sigmoid(z));
    }
}
