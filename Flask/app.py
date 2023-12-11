from flask import Flask, request, jsonify
import os
import time
import random  # random 모듈 임포트
import numpy as np
import tensorflow as tf
from tensorflow.keras.models import load_model
import librosa

app = Flask(__name__)

gpus = tf.config.experimental.list_physical_devices('GPU')
if gpus:
    try:
        tf.config.experimental.set_memory_growth(gpus[0], True)
    except RuntimeError as e:
        print(e)

def preprocess_mfcc(audio_file_path, n_mfcc=18, fixed_length=200):
    # Load the audio file
    y, sr = librosa.load(audio_file_path)
    
    # Compute MFCCs
    mfccs = librosa.feature.mfcc(y=y, sr=sr, n_mfcc=n_mfcc)
    
    # Pad or truncate MFCCs to the fixed length
    if mfccs.shape[1] < fixed_length:
        mfccs_padded = np.pad(mfccs, ((0, 0), (0, fixed_length - mfccs.shape[1])), mode='constant')
    elif mfccs.shape[1] > fixed_length:
        mfccs_padded = mfccs[:, :fixed_length]
    else:
        mfccs_padded = mfccs
    
    return mfccs_padded

@app.route('/ai', methods=['GET'])
def ai_endpoint():
    # request.args.get을 사용하여 'record' 파라미터 값 가져오기
    record_value = request.args.get('record')
    
    # 녹음 파일이 있는지 확인
    record_path = os.path.join('/home/t23304/record/', record_value)
    file_exists = os.path.exists(record_path)
    
    # 1초 동안 대기
    # time.sleep(1)
    if file_exists:
        y, sr = librosa.load(record_path)
        mfccs = preprocess_mfcc(record_path)
        mfccs = np.expand_dims(mfccs, axis=0)

        model = load_model('./home/t23304/ai/AI/model/WATERMELON_CNN-4.hdf5')
        result = model.predict(mfccs)

        result = result[0][0]
        print(result)

        if result > 50:
            return jsonify({'sweet': 1}) # 맛있는 수박
        else:
            return jsonify({'sweet': 0}) # 맛없는 수박
    
    # record_value가 None이 아니고 파일이 존재하면 0을 반환, 그렇지 않으면 랜덤으로 0 또는 1을 반환
    # result = 0 if record_value is not None and file_exists else random.choice([0, 1])
    
    # 결과를 JSON 형식으로 반환
    # print(result)
    # return jsonify({'sweet': result})

if __name__ == '__main__':
    # 포트 7000에서 애플리케이션 실행
    app.run(port=7000)