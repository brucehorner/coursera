clear ; close all; clc
load ('ex8_movies.mat');
%  Y is a 1682x943 matrix, containing ratings (1-5) of 1682 movies on 
%  943 users
%
%  R is a 1682x943 matrix, where R(i,j) = 1 if and only if user j gave a
%  rating to movie i

%  Load pre-trained weights (X, Theta, num_users, num_movies, num_features)
load ('ex8_movieParams.mat');

num_users = 4; num_movies = 5; num_features = 3;
X = X(1:num_movies, 1:num_features);
Theta = Theta(1:num_users, 1:num_features);
Y = Y(1:num_movies, 1:num_users);
R = R(1:num_movies, 1:num_users);

J = 0;
X_grad = zeros(size(X));
Theta_grad = zeros(size(Theta));


X_grad = R.*((Theta*X')'-Y) * Theta

(R.*((Theta*X')'-Y))'*X
