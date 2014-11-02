clear ; close all; clc

%% Setup the parameters you will use for this exercise
input_layer_size  = 400;  % 20x20 Input Images of Digits
hidden_layer_size = 25;   % 25 hidden units
num_labels = 10;          % 10 labels, from 1 to 10   
load('ex4data1.mat');
m = size(X, 1);
load('ex4weights.mat');
lambda = 1;
Theta1_grad = zeros(size(Theta1));
Theta2_grad = zeros(size(Theta2));

A1 = [ones(m,1) X];
Z2 = A1*Theta1';
A2 = [ones(m,1) sigmoid(Z2)];
Z3 = A2*Theta2';
H  = sigmoid(Z3);

Y = eye(num_labels)(y,:);

T1 = Theta1(:,2:end);   % remove theta(0) for regularization
T2 = Theta2(:,2:end);   

Jterm = (1/m) * (-Y.*log(H) - (1-Y).*log(1-H));

Jreg1 = (lambda/(2*m)) * (T1.*T1);
Jreg2 = (lambda/(2*m)) * (T2.*T2);

J = sum(sum(Jterm)) + sum(sum(Jreg1)) + sum(sum(Jreg2));






