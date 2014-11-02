function [J, grad] = costFunctionReg(theta, X, y, lambda)
%COSTFUNCTIONREG Compute cost and gradient for logistic regression with regularization
%   J = COSTFUNCTIONREG(theta, X, y, lambda) computes the cost of using
%   theta as the parameter for regularized logistic regression and the
%   gradient of the cost w.r.t. to the parameters. 

% Initialize some useful values
m = length(y); % number of training examples

% You need to return the following variables correctly 
J = 0;
grad = zeros(size(theta));

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost of a particular choice of theta.
%               You should set J to the cost.
%               Compute the partial derivatives and set grad to the partial
%               derivatives of the cost w.r.t. each parameter in theta


H = sigmoid(X*theta);
T = theta(2:end,:);		% remove theta(0) for regularization

J1 = ( (-y'*log(H)) - (1-y')*log(1-H) ) / m;
J2 = (lambda/(2*m)) * T'*T;

J = J1 + J2;


grad1 = ( X'*(H-y) ) / m;
grad2 = (lambda/m) * theta;
grad2(1) = 0;			% remove 1st value for regularization

grad = grad1 + grad2;


% =============================================================

end
