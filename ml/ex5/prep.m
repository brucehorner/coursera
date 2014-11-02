clear ; close all; clc
load ('ex5data1.mat');

m = size(X, 1);
theta = [1 ; 1];

X1 = [ones(m, 1) X];
lambda =1;
H = X1*theta;



