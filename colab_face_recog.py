import dlib
import numpy as np
import os
from matplotlib.pyplot import imread
import imageio
from tqdm import tqdm_notebook
from IPython.display import display, Javascript
from google.colab.output import eval_js
from base64 import b64decode

from google.colab import drive
drive.mount('/content/gdrive')

cd /content/gdrive/My Drive/face_recognition/

ls
face_detector = dlib.get_frontal_face_detector()
shape_predictor = dlib.shape_predictor('shape_predictor_68_face_landmarks.dat')
face_recognition_model = dlib.face_recognition_model_v1('dlib_face_recognition_resnet_model_v1.dat')
TOLERANCE = 0.5
def get_face_encodings(path_to_image):
    image = imageio.imread(path_to_image)
    detected_faces = face_detector(image, 1)
    shapes_faces = [shape_predictor(image, face) for face in detected_faces]
    return [np.array(face_recognition_model.compute_face_descriptor(image, face_pose, 1)) for face_pose in shapes_faces]

def compare_face_encodings(known_faces, face):
    return (np.linalg.norm(known_faces - face, axis=1) <= TOLERANCE)

def find_match(known_faces, names, face):
    matches = compare_face_encodings(known_faces, face)
    count = 0
    for match in matches:
        if match:
            return names[count].split('/')[1]
            
        count += 1
    return 'Not Found'

face_encodings = []
names = []
my_list =['Saanika', 'Himanshu']
for z in my_list:
    image_filenames = filter(lambda x: x.endswith('.jpg'), os.listdir('/content/gdrive/My Drive/face_recognition/train/' + z + '/'))
    image_filenames = sorted(image_filenames)
    paths_to_images = ['train/' + z + '/' + x for x in image_filenames]
    print(paths_to_images)
    for path_to_image in tqdm_notebook(paths_to_images):
        f = 0
        face_encodings_in_image = get_face_encodings(path_to_image)
        if len(face_encodings_in_image) != 1:
            print("Please change image: " + path_to_image + " - it has " + str(len(face_encodings_in_image)) + " faces; it can only have one")
            f = 1
        if(f == 0):
            names.append(path_to_image)
            face_encodings.append(get_face_encodings(path_to_image)[0])

test_filenames = filter(lambda x: x.endswith(('.jpg','.jpeg','JPG')), os.listdir('test/'))
paths_to_test_images = ['test/' + x for x in test_filenames]
for path_to_image in tqdm_notebook(paths_to_test_images):
    face_encodings_in_image = get_face_encodings(path_to_image)
    if len(face_encodings_in_image) == 0:
        print("Please change image: " + path_to_image + " - it has " + str(len(face_encodings_in_image)) + " faces; it can only have one")
    elif(len(face_encodings_in_image) == 1):
        match = find_match(face_encodings, names, face_encodings_in_image[0])
        print(path_to_image, match)
    else:
        for x in range(len(face_encodings_in_image)):
            match = find_match(face_encodings, names, face_encodings_in_image[0])
            print(path_to_image, match)
    print()

def take_photo(filename='photo.jpg', quality=0.8):
  js = Javascript('''
    async function takePhoto(quality) {
      const div = document.createElement('div');
      const capture = document.createElement('button');
      capture.textContent = 'Capture';
      div.appendChild(capture);

      const video = document.createElement('video');
      video.style.display = 'block';
      const stream = await navigator.mediaDevices.getUserMedia({video: true});

      document.body.appendChild(div);
      div.appendChild(video);
      video.srcObject = stream;
      await video.play();

      // Resize the output to fit the video element.
      google.colab.output.setIframeHeight(document.documentElement.scrollHeight, true);

      // Wait for Capture to be clicked.
      await new Promise((resolve) => capture.onclick = resolve);

      const canvas = document.createElement('canvas');
      canvas.width = video.videoWidth;
      canvas.height = video.videoHeight;
      canvas.getContext('2d').drawImage(video, 0, 0);
      stream.getVideoTracks()[0].stop();
      div.remove();
      return canvas.toDataURL('image/jpeg', quality);
    }
    ''')
  display(js)
  data = eval_js('takePhoto({})'.format(quality))
  binary = b64decode(data.split(',')[1])
  with open(filename, 'wb') as f:
    f.write(binary)
  return filename

from IPython.display import Image
try:
  filename = take_photo()
  print('Saved to {}'.format(filename))
  display(Image(filename))
except Exception as err:
  print(str(err))

# cap = cv2.VideoCapture(0)
# while(cap.isOpened()):
#   ret, frame = cap.read()
#   if ret == True:
#     cv2.imshow('Frame', frame)
#      if cv2.waitKey(25) & 0xFF == ord('q'):
#       break
#   else: 
#     break
# cap.release()
 
# cv2.destroyAllWindows()
