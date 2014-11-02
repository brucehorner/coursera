%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Section 3.4
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

clear ; close all; clc
load ('ex5data1.mat');
m = size(X,1);
lambda = 3;
p = 8;

% Map X onto Polynomial Features and Normalize
X_poly = polyFeatures(X, p);
[X_poly, mu, sigma] = featureNormalize(X_poly);  % Normalize
X_poly = [ones(m, 1), X_poly];                   % Add Ones

% Map X_poly_test and normalize (using mu and sigma)
X_poly_test = polyFeatures(Xtest, p);
X_poly_test = bsxfun(@minus, X_poly_test, mu);
X_poly_test = bsxfun(@rdivide, X_poly_test, sigma);
X_poly_test = [ones(size(X_poly_test, 1), 1), X_poly_test];         % Add Ones

% Map X_poly_val and normalize (using mu and sigma)
X_poly_val = polyFeatures(Xval, p);
X_poly_val = bsxfun(@minus, X_poly_val, mu);
X_poly_val = bsxfun(@rdivide, X_poly_val, sigma);
X_poly_val = [ones(size(X_poly_val, 1), 1), X_poly_val];           

theta = trainLinearReg(X_poly, y, lambda);

Jtrain = linearRegCostFunction(X_poly, y, theta, 0)
Jval   = linearRegCostFunction(X_poly_val, yval, theta, 0)
Jtest  = linearRegCostFunction(X_poly_test, ytest, theta, 0)


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Section 3.5
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
clear ; close all; clc
load ('ex5data1.mat');
lambda = 0.01;
p = 8;
m = size(X,1);


for i=2:m

  cycles = 50;
  Jtrain = 0;
  Jval = 0;  
  for c=1:cycles
  
    indices = randperm(m)(1:i);
    Xcycle = X(indices);    
    ycycle = y(indices);
    X_poly = polyFeatures(Xcycle, p);
    [X_poly, mu, sigma] = featureNormalize(X_poly);  % Normalize
    X_poly = [ones(i,1), X_poly];                    % Add Ones

    theta = trainLinearReg(X_poly, ycycle, lambda);
    
    Jtrain = Jtrain + linearRegCostFunction(X_poly, ycycle, theta, 0);        
    
    Xvalcycle = Xval(indices);    
    yvalcycle = yval(indices);
    X_poly_val = polyFeatures(Xvalcycle, p);
    X_poly_val = bsxfun(@minus, X_poly_val, mu);
    X_poly_val = bsxfun(@rdivide, X_poly_val, sigma);
    X_poly_val = [ones(i,1), X_poly_val];           
    Jval = Jval + linearRegCostFunction(X_poly_val, yvalcycle, theta, 0);
  
  end
    
  JtrainVec(i) = Jtrain / cycles;
  JvalVec(i) = Jval / cycles;

end


plot(1:m, JtrainVec, 1:m, JvalVec);
title('Learning curve for linear regression');
legend('Train', 'Cross Val');
xlabel('Number of training examples');
ylabel('Error');
axis([0 13 0 100]);



