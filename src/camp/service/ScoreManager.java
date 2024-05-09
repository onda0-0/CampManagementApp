package camp.service;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import camp.utility.ConsoleIO;

import java.util.List;
import java.util.Optional;

public class ScoreManager {
    private final List<Score> scoreStore;
    private final List<Subject> subjectStore;
    private final List<Student> studentStore;
    private final ConsoleIO consoleIO;

    public ScoreManager(List<Score> scoreStore, List<Subject> subjectStore, List<Student> studentStore, ConsoleIO consoleIO) {
        this.scoreStore = scoreStore;
        this.subjectStore = subjectStore;
        this.studentStore = studentStore;
        this.consoleIO = consoleIO;
    }
    public List<Student> getStudentStore() {return studentStore;}
    public List<Subject> getSubjectStore() {return subjectStore;}
    public List<Score> getScoreStore() {return scoreStore;}
    //김현성 2024.05.07수정
    // 수강생의 과목별 회차 점수 수정
    public void updateRoundScoreBySubject(){
        consoleIO.print("========점수 수정=======");
        String studentId = consoleIO.getStringInput("수강생의 고유 번호를 입력하세요 : ");
        String subjectId = consoleIO.getStringInput("과목의 고유 번호를 입력하세요 : ");
        int scoreId = consoleIO.getIntInput("수정 할 회차 입력 : ");
        int findFlag = 0;

        if (scoreStore.isEmpty()){
            consoleIO.print("현재 어떤 정보 점수도 기록되어있지 않습니다.");
        } else {
            for (Score findingScore : scoreStore) {
                if (findingScore.returnFindingStudentId().equals(studentId)) { //학생 고유번호가 같은걸 찾고
                    if (findingScore.returnFindingSubjectId().equals(subjectId)) { // 과목 번호가 같은걸 찾고
                        if (findingScore.returnFindingScoreId() == scoreId) { //회자 번호가 같은걸 찾았다면
                            consoleIO.print("해당하는 회자를 잦았습니다. 현재점수(" + findingScore.returnFindingTestScore() + "점)");
                            int score = consoleIO.getIntInput("수정할 시험점수 입력( 0 ~ 100 ) : ");
                            if (score >= 0 && score <= 100) {
                                findingScore.modifiScore(score); //class 안에있는 수정 method
                                for (Subject subject : subjectStore) {
                                    if (subject.getSubjectId().equals(subjectId)) {
                                        findingScore.modifiGrade(makeScoreGrade(subject.getSubjectType(), score));
                                        consoleIO.print("해당하는 "+subjectId+"학생의 "+subjectId+"과목의 "+scoreId+"회차 점수("+findingScore.getTestRate()+")로 수정했습니다.");
                                        findFlag++;
                                        break;
                                    }
                                }
                            }else {
                                consoleIO.print("---수정할 시험점수가 0~100사이의 정수가 아닙니다.---");
                            }
                        }
                    }
                }
            }
        }
        if (findFlag == 0){
            consoleIO.print("해당 정보를 찾지 못해 수정하지 못했습니다.");
        }
    }



    // 수강생의 과목별 시험 회차 및 점수 등록
    public void createScore() {
        String studentId = consoleIO.getStringInput("관리 할 수강생의 ID를 입력하세요"); // 관리할 수강생 고유 번호
        consoleIO.print("시험 점수를 등록합니다...");
        // 기능 구현


        Student selectedStudent = null;


        for (Student student : getStudentStore() ) {
            if (student.getStudentId().equals(studentId)) {
                selectedStudent = student;
                break;
            }
        }
        if (selectedStudent == null) {
            System.out.println("다시 확인해주세요");
            return;
        }


        consoleIO.print("등록할 과목을 선택하세요:");
        for (int i = 0; i < getSubjectStore().size(); i++) {
            Subject subject = getSubjectStore().get(i);
            System.out.println(i + 1 + ". " + subject.getSubjectName());
        }

        int selectedIndex = consoleIO.getIntInput("과목번호를 입력하세요");
        if (selectedIndex < 0 || selectedIndex > getSubjectStore().size()) {
            System.out.println("잘못된 과목 번호입니다.");
            return;
        }
        Subject selectedSubject = getSubjectStore().get(selectedIndex - 1);



        int iteration = consoleIO.getIntInput("등록할 시험 회차를 입력하세요 (1회차부터 10회차까지 가능): ");
        if (iteration < 1 || iteration > 10) {
            consoleIO.print("잘못된 회차입니다.");
            return;
        }

        // 이미 회차에 점수가 등록되어 있는지 확인
        boolean iterationExit = false;
        for (Score testscore : getScoreStore()) {
            if (testscore.returnFindingStudentId().equals(studentId) && testscore.returnFindingSubjectId().equals(selectedSubject.getSubjectId()) && testscore.getIteration() == iteration) {
                iterationExit = true;
                break;
            }
        }
        if (iterationExit) {
            consoleIO.print("이미 해당 회차에 점수가 등록되어 있습니다.");
            return;
        }

        int testsscore;
        do{
            testsscore =consoleIO.getIntInput("점수를 입력하세요:");
            if (testsscore < 0 || testsscore > 100) {
                System.out.println("올바른 점수를 입력해주세요");
            }
        } while (testsscore < 0 || testsscore > 100);

        char scoreGrade = makeScoreGrade(selectedSubject.getSubjectType(), testsscore);


        Score score = new Score(studentId, selectedSubject.getSubjectId(), iteration, testsscore, scoreGrade);
        scoreStore.add(score);


        consoleIO.print(selectedStudent.getStudentId()+"."+selectedStudent.getStudentName()+"님의"+iteration + "회차 " + selectedSubject.getSubjectName() + "시험 점수:" + testsscore + " 등급:" +scoreGrade + " 등록 완료");
    }



    public void removeRoundScoreBySubject() {
        consoleIO.print("========점수 삭제=======");
        String studentId = consoleIO.getStringInput("수강생의 고유 번호를 입력하세요 : ");
        String subjectId = consoleIO.getStringInput("과목의 고유 번호를 입력하세요 : ");
        int scoreId = consoleIO.getIntInput("삭제 할 회차 입력 : ");
        int findFlag = 0;
        // 기능 구현 (삭제 할 과목 및 회차, 점수)
        String removeCheck = consoleIO.getStringInput("해당하는 "+subjectId+"학생의 "+subjectId+"과목의 "+scoreId+"회차 점수를 삭제하시겠습니까?(y/n) : ");
        if(removeCheck.equals("y") || removeCheck.equals("Y") ){
            for (Score findingScore : scoreStore){
                if(findingScore.returnFindingStudentId().equals(studentId)) {
                    if(findingScore.returnFindingSubjectId().equals(subjectId)) {
                        if (findingScore.returnFindingScoreId() == scoreId){
                            scoreStore.remove(findingScore);
                            consoleIO.print("삭제 완료!");
                            findFlag++;
                            break;
                        }
                        else{
                            consoleIO.print("해당하는 학생의 과목 점수가 없습니다.");
                        }
                    }
                }
            }
        }
        else{
            consoleIO.print("---취소를 선택하셨습니다. 삭제를 취소합니다.---");
        }
        if (findFlag == 0){
            consoleIO.print("해당 정보를 찾지 못해 삭제하지 못했습니다.");
        }
    }


    // 수강생의 특정 과목 회차별 등급 조회
    public void inquireRoundGradeBySubject() {
        String studentId = consoleIO.getStringInput("\n관리할 수강생의 번호를 입력하시오..."); // 관리할 수강생 고유 번호
        String subjectId = consoleIO.getStringInput("\n조회하고 싶은 과목 번호을 입력하시오..."); // 조회할 과목 고유 번호
        System.out.println("회차별 등급을 조회합니다...");
        List<Score> filterScore = scoreStore.stream()
                .filter(score -> score.returnFindingStudentId().equals(studentId) && score.returnFindingSubjectId().equals(subjectId))
                .toList();

        if(filterScore.isEmpty()){
            System.out.println("조회할 정보가 없습니다.");
        } else {
            for(Score score : filterScore){
                System.out.println(score.returnFindingScoreId() + "회차" + "등급: " + score.getTestRate());
            }
            System.out.println("\n등급 조회 성공!");
        }
    }
    // 수강생의 과목별 평균 등급 조회
    public void inquireAvgRateBySubject() {
        String studentId = consoleIO.getStringInput("\n관리할 수강생의 번호를 입력하시오..."); // 관리할 수강생 고유 번호
        String subjectId = consoleIO.getStringInput("\n조회하고 싶은 과목 번호을 입력하시오..."); // 조회할 과목 고유 번호
        // 수강생의 과목별 평균 등급 조회
        System.out.println("과목별 평균 등급을 조회합니다...");
        double avgScore = scoreStore.stream().filter(score -> score.returnFindingStudentId().equals(studentId) && score.returnFindingSubjectId().equals(subjectId))
                .mapToDouble(Score::returnFindingTestScore)
                .average().getAsDouble();
        // 과목 이름
        subjectStore.stream().filter(name -> name.getSubjectId().equals(subjectId))
                .forEach(f -> System.out.println(f.getSubjectName()));

        // 과목 타입
        for(Subject subject : subjectStore){
            if(subject.getSubjectId().equals(subjectId)){
                String subjectType = subject.getSubjectType();
                if(subjectType.equals("MANDATORY")){
                    if(avgScore>=95){
                        System.out.println('A');
                    }else if(avgScore>=90){
                        System.out.println('B');
                    }else if(avgScore>=80){
                        System.out.println('C');
                    }else if(avgScore>=70){
                        System.out.println('D');
                    }else if(avgScore>=60){
                        System.out.println('F');
                    }else if(avgScore<60){
                        System.out.println('N');
                    }
                }
                if(subjectType.equals("CHOICE")) {
                    if (avgScore >= 90) {
                        System.out.println('A');
                    } else if (avgScore >= 80) {
                        System.out.println('B');
                    } else if (avgScore >= 70) {
                        System.out.println('C');
                    } else if (avgScore >= 60) {
                        System.out.println('D');
                    } else if (avgScore >= 50) {
                        System.out.println('F');
                    } else if (avgScore < 50) {
                        System.out.println('N');
                    }
                }
            }
        }
        System.out.println("\n평균 등급 조회 성공!");

    }
    // 특정 상태 수강생들의 필수 과목 평균 등급을 조회
    public void inquireStatusAvgBySubject() {
        System.out.println("아직,,");

    }

}
