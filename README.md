# Problem Statement: #

  In recent days, skin cancer is seen as one of the most Hazardous form of the Cancers found in Humans. Skin cancer is found in various types such as Melanoma, Basal and Squamous cell Carcinoma among which Melanoma is the most unpredictable. With millions of new cases each year it accounts for 75% of skin cancer deaths . The detection of Melanoma cancer in early stage can be helpful to cure it.


# Solution: #

  In this hack we present a mobile application for detection of Melanoma Skin Cancer using Deep learning techniques. We detect if a certain mole is Melanoma or Non-melanoma using a CNN model which has an accuracy of 75%. The model is trained over an image dataset of 11 GB.


# Using Deep Learning in our App: #
 
   We have retrained the final layer of the Google’s inception v3 model which is ***48 layers deep with an image dataset of 11 GB*** with learning rate 0.01 and 12000 steps. After retraining we attained us ***75 % final test accuracy***.
The image dataset contains two classification “Melanoma” and “Non Melanoma”.
The image dataset was divided in ratio of 8:1:1 for training , validation and testing respectively.
The model is used in the app by creating ***TensorFlowImageClassifier***. When the user clicks the image it is taken as bitmap input by the classifier.


# History of previous cases is also stored: #
  
   When the user scans the output percentage is stored as history in his profile which is done offline for future reference.


# Nearby Medical Services Are Available on One Click: #
   
   This feature enables the user to directly view all the nearby hospitals on Map.


# THE BIG FEATURE #
    
   The image recognition is On-device making the ***app fully function offline***.
   This helps the people to use our app even in remote areas.
