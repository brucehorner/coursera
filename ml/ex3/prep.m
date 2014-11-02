clear;clc;
input_layer_size  = 400;  % 20x20 Input Images of Digits
num_labels = 10;
load('ex3data1.mat'); % training data stored in arrays X, y
m = size(X, 1);
n = size(X, 2);
all_theta = zeros(num_labels, n + 1);
X = [ones(m, 1) X];
lambda = 0.1;
initial_theta = zeros(n + 1, 1);
