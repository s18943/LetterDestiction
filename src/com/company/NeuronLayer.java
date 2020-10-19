package com.company;

public class NeuronLayer {
    //name: string; // Тут название нейрона – буква, с которой он ассоциируется
    //input: SmartVector[0..99,0..99] of integer; // Тут входной массив 100х100
    //output: integer; // Сюда он будет говорить, что решил
    //memory: W[0..99,0..99] of integer;
    //String name;

    public int size;
    public double[] neurons;
    public double[] biases;
    public double[][] weights;

    public NeuronLayer(int size, int nextSize) {
        this.size = size;
        neurons = new double[size];
        biases = new double[size];
        weights = new double[size][nextSize];
        for (int i = 0; i < size; i++) {
            biases[i] = Math.random() * 2.0 - 1.0;
            for (int j = 0; j < nextSize; j++) {
                weights[i][j] = Math.random() * 2.0 - 1.0;
            }
        }
    }
    public NeuronLayer feedforward(NeuronLayer input) {
            for (int j = 0; j < size; j++) {
                neurons[j] = 0;
                for (int k = 0; k < size; k++) {
                    neurons[j] += input.neurons[k] * input.weights[k][j];
                }
                neurons[j] += biases[j];
                neurons[j] = sigmoid(neurons[j]);
            }
        return this;
    }

    public double[] getNeurons() {
        return neurons;
    }

    public void setNeurons(double[] neurons) {
        System.arraycopy(neurons, 0, this.neurons, 0, neurons.length);
    }

    public static double sigmoid(double z) {
//            """The sigmoid function."""
        return 1.0 / (1.0 + Math.exp(-z));
    }

}
