#  캠프 관리 프로그램

## 프로젝트 설명
내배캠 스프링 수강생들을 관리하는 프로그램

- 캠프에는 필수 과목과 선택 과목이 존재합니다.
    - 필수 과목
      1.Java 2.객체지향 3.Spring 4.JPA 5.MySQL
    - 선택 과목
      1.디자인 패턴 2.Spring Security 3.Redis 4.MongoDB

- 점수에 따라 등급이 매겨집니다
    - 필수 과목
      A : 95 ~ 100
      B : 90 ~ 94
      C : 80 ~ 89
      D : 70 ~ 79
      F : 60 ~ 69
      N : 60점 미만
    - 선택 과목
      A : 90 ~ 100
      B : 80 ~ 89
      C : 70 ~ 79
      D : 60 ~ 69
      F : 50 ~ 59
      N : 50점 미만

- 최소 3개 이상의 필수 과목, 2개 이상의 선택 과목을 선택합니다.
- 캠프 기간동안 선택한 과목별로 총 10회의 시험을 봅니다.
- 캠프 매니저는 수강생을 등록 및 관리할 수 있습니다.
- 캠프 매니저는 수강생들의 과목과 시험 점수를 등록 및 관리할 수 있습니다.
- 점수 데이터 타입 : 정수형


## Model

### 데이터 모델

#### 수강생(Student)
- [ ] studentId (고유번호, Primary Key)
- [ ] studentName(이름)
- [ ] subjectNames (과목목록)
- [ ] studentStatus (상태,  Green/Red/Yellow)  
  
#### 과목(Subject)
- [ ] subjectId (고유번호, Primary Key)
- [ ] subjectName (과목명)
- [ ] subjectType (과목타입, 필수/선택)
 
#### 점수(Score)
- [ ] subjectId (과목 고유번호, Foreign Key)
- [ ] studentId (수강생 고유번호, Foreign Key)
- [ ] iteration (회차)
- [ ] score (점수)
- [ ] grade (등급, A/B/C/D/E/F/N)

### 관계형 데이터베이스 모델 (ERD)
- 수강생(Student) 테이블은 여러 점수(Score) 엔티티와 일대다(One-to-Many) 관계를 갖습니다. 즉, 한 명의 수강생이 여러 과목의 점수를 받을 수 있습니다.
- 과목(Subject) 테이블도 여러 점수(Score) 엔티티와 일대다 관계를 갖습니다. 즉, 한 개의 과목에 대해 여러 수강생이 점수를 받을 수 있습니다.
- 점수(Score) 테이블은 수강생(Student) 및 과목(Subject) 테이블과 각각 다대일(Many-to-One) 관계를 가집니다. 점수는 특정 수강생과 특정 과목에 대한 정보를 담기 때문입니다.

## 기능

### 수강생 관리
Create
- [x] 수강생 정보를 등록할 수 있습니다. (수강생의 고유번호는 중복될 수 없습니다.)

Read
- [x] 수강생 목록을 조회할 수 있습니다. (조회 형식은 자유입니다.)
- [x] 수강생 정보를 조회할 수 있습니다. (조회 형식은 자유입니다.)
- [x] 상태별 수강생 목록을 조회할 수 있습니다. (고유번호, 이름은 필수 정보이고 나머지 형식은 자유입니다.)

Update
- [x] 수강생 정보를 수정할 수 있습니다. (이름 또는 상태를 입력받아 수정)
- [x] 수강생 상태를 관리할 수 있습니다.(Green / Red / Yellow)

Delete
- [x] 수강생을 삭제할 수 있습니다.


### 점수 관리
Create
- [ ] 수강생의 과목별 시험 회차 및 점수를 등록할 수 있습니다. (점수를 등록하면 자동으로 등급이 추가 저장됩니다, 등록하려는 과목의 회차 점수가 이미 등록되어 있다면 등록할 수 없습니다. 과목의 회차 점수가 중복되어 등록될 수 없습니다.

Read
- [ ] 수강생의 특정 과목 회차별 등급을 조회할 수 있습니다. (조회 형식은 자유입니다.)
- [ ] 수강생의 과목별 평균 등급을 조회할 수 있습니다.  (조회 형식은 자유입니다.)
- [ ] 특정 상태 수강생들의 필수 과목 평균 등급을 조회합니다.(조회 형식은 자유입니다.)

Update
- [ ] 수강생의 과목별 회차 점수를 수정할 수 있습니다.
  Delete
- [ ] 수강생의 과목별 시험 점수를 삭제할 수 있습니다.


## 기본 파일구조
데이터 저장소 및 초기 데이터 설정:
    - studentStore, subjectStore, scoreStore는 수강생, 과목, 점수를 각각 저장하는 List입니다.
    - setInitData() 메서드는 프로그램 초기화 시에 초기 데이터를 설정합니다.
인덱스 관리:
    - sequence() 메서드를 사용하여 각 엔티티의 고유번호를 자동으로 증가시키고 있습니다.
메인 메뉴 및 서브 메뉴:
    - displayMainView() 메서드가 메인 메뉴를 보여주고, 해당하는 기능을 실행하도록 분기합니다.
    - displayStudentView() 메서드가 수강생 관리 메뉴를, displayScoreView() 메서드가 점수 관리 메뉴를 처리합니다.
수강생 관리 기능:
    - createStudent() 메서드가 수강생을 등록하는 기능입니다. 
    - inquireStudent() 메서드가 수강생 목록을 조회하는 기능입니다.
점수 관리 기능:
    - createScore() 메서드가 수강생의 과목별 시험 회차 및 점수를 등록하는 기능입니다.
    - updateRoundScoreBySubject() 메서드가 수강생의 과목별 회차 점수를 수정하는 기능입니다.
    - inquireRoundGradeBySubject() 메서드가 수강생의 특정 과목 회차별 등급을 조회하는 기능입니다.