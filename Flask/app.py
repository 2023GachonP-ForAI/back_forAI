from flask import Flask, request, jsonify
import os
import time
import random  # random 모듈 임포트

app = Flask(__name__)

@app.route('/ai', methods=['GET'])
def ai_endpoint():
    # request.args.get을 사용하여 'record' 파라미터 값 가져오기
    record_value = request.args.get('record')
    
    # 녹음 파일이 있는지 확인
    record_path = os.path.join('/home/t23304/record/', record_value)
    file_exists = os.path.exists(record_path)
    
    # 1초 동안 대기
    time.sleep(1)
    
    # record_value가 None이 아니고 파일이 존재하면 0을 반환, 그렇지 않으면 랜덤으로 0 또는 1을 반환
    result = 0 if record_value is not None and file_exists else random.choice([0, 1])
    
    # 결과를 JSON 형식으로 반환
    print(result)
    return jsonify({'sweet': result})

if __name__ == '__main__':
    # 포트 7000에서 애플리케이션 실행
    app.run(port=7000)