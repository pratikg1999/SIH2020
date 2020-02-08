# SIH2020
# Home Automation 
Control home appliances with your voice/taps and home surveillance using face recognition.

## Code Requirements
The code is in Python (version 3.6 or higher will work).

## Dependencies
- OpenCV
- NumPy
- Dlib
- Pyrebase
- Imageio

## Description
- Detect/identify faces in an image (using a face detection model).
- Predict face landmarks for the identified faces.
- Using data from step 2 and the actual image, calculate face encodings.
- Compare the face encodings of known faces with those from test images to tell who is in the picture.

## Facial landmarks detection
The pre-trained facial landmark detector inside the Dlib library is used to estimate the location of 68 (x, y)-coordinates that map to facial structures on the face. These facial landmarks are used to localize and represent salient regions of the face like:
- Eyes
- Eyebrows
- Nose
- Mouth
- Jawline


