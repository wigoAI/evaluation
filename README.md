# Evaluation
모델 성능 평가 지표

# 평가 기준
이진 분류에서 성능 지표로 잘 활용되는 오차행렬 사용
![Confusion Matrix](https://mblogthumb-phinf.pstatic.net/MjAyMDAzMTFfMjkx/MDAxNTgzOTMwMjg5OTI2.1ts9m6PERRJOig-RcWnEa08vXXZG64NdemSOP9A-LGwg.s5mg0HnuqqHurnK0FZ3YBkqUfpW0r9EjTLqDhNYaE0cg.PNG.owl6615/confusion.png?type=w800)

# 평가 지표
## 입력 값
- P : 전체 참인 수
- N : 전체 거짓인 수

- TP (True Positive) : 참을 참이라고 한 횟수
- TN (True Negative) : 거짓을 거짓이라고 한 횟수
- FN (False Negative) : 참을 거짓이라고 한 횟수  
- FP (False Positive) : 거짓을 참이라고 한 횟수  

## 계산식
- Accuracy : 정확도, 제대로 분류된 데이터의 비율 
  - ![accuracy](expression/accuracy.gif)  
  

- Error Rate : 오류율, 잘못 분류한 데이터의 비율 
  - ![error_rate](expression/error_rate.gif)  
  

- Precision : 정밀도, 예측한 정답 중 실제 정답인 것
  - 모델 관점
  - ![precision](expression/precision.gif)  
  

- recall : 재현율, 실제 정답 중 예측에 성공한 것 
  - 데이터 관점
  - 참인 정답이 적을 때 유효
  - ![recall](expression/recall.gif)  
  

- F1-Score : Precision과 Recall의 조화평균, 
  - recall과 precision의 조화 평균
  - ![f1_score](expression/f1_score.gif)
  - ![f1_score_2](expression/f1_score_2.gif)  
  

- Geometric Mean : 균형 정확도
  - 참에 대한 정확도와, 거짓에 대한 정확도를 따로 분류해 기하 평균을 구함 
  - ![geometric_mean](expression/geometric_mean.gif)  
  

