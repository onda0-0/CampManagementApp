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

    //김현성 2024.05.07수정
    // 수강생의 과목별 회차 점수 수정
    public void updateRoundScoreBySubject(String studentId, String subjectId){
        // 기능 구현 (수정할 과목 및 회차, 점수)
        consoleIO.print("---시험 점수를 수정합니다...---");
        // 기능 구현
        int scoreId = consoleIO.getIntInput("수정 할 회차 입력 : ");

        if (scoreStore.isEmpty()){
            consoleIO.print("현재 어떤 정보 점수도 기록되어있지 않습니다.");
        }
        else {
            for (Score findingScore : scoreStore){
                if(findingScore.returnFindingStudentId().equals(studentId)){ //학생 고유번호가 같은걸 찾고
                    if (findingScore.returnFindingScoreId() == scoreId){ //회자 번호가 같은걸 찾았다면
                        consoleIO.print("해당하는 회자를 잦았습니다. 현재점수("+ findingScore.returnFindingTestScore()+"점)");
                        int testScore = consoleIO.getIntInput("수정할 시험점수 입력( 0 ~ 100 ) : ");
                        if(testScore >= 0 && testScore <= 100){
                            findingScore.modifiScore(testScore); //class 안에있는 수정 method
                        }
                        else {
                            consoleIO.print("---수정할 시험점수가 0~100사이의 정수가 아닙니다.---");
                        }
                    }
                    else{
                        consoleIO.print("---해당되는 회차의 점수가 없습니다.---");
                    }
                }
                else {
                    consoleIO.print("---해당되는 학생 정보가 없습니다.---");
                }
            }
        }
    }

    public void removeRoundScoreBySubject(String studentId, String subjectId) {
        // 기능 구현 (삭제 할 과목 및 회차, 점수)
        String removeCheck = consoleIO.getStringInput("해당 학생의 과목별 시험점수를 삭제하시겠습니까?(y/n) : ");
        if(removeCheck.equals("y") || removeCheck.equals("Y") ){
            for (Score findingScore : scoreStore){
                if(findingScore.returnFindingStudentId().equals(studentId)) {
                    if(findingScore.returnFindingSubjectId().equals(subjectId)) {
                        scoreStore.remove(findingScore);
                        System.out.println("해당하는 학생의 과목 점수를 모두 삭제했습니다.");
                    }
                    else{
                        System.out.println("해당하는 학생의 과목 점수가 없습니다.");
                    }
                }
            }
        }
        else{
            System.out.println("---취소를 선택하셨습니다. 삭제를 취소합니다.---");
        }
    }


    // 수강생의 특정 과목 회차별 등급 조회
    public void inquireRoundGradeBySubject() {
        String studentId = consoleIO.getStringInput("\n관리할 수강생의 번호를 입력하시오..."); // 관리할 수강생 고유 번호
        String subjectId = consoleIO.getStringInput("\n조회하고 싶은 과목 번호을 입력하시오..."); // 조회할 과목 고유 번호

        List<Score> filterScore = scoreStore.stream()
                .filter(score -> score.returnFindingStudentId().equals(studentId) && score.returnFindingSubjectId().equals(subjectId))
                .toList();
        System.out.println("회차별 등급을 조회합니다...");
        // 기능 구현
        System.out.println("\n등급 조회 성공!");
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

    public void inquireStatusAvgBySubject() {
        System.out.println("아직,,");
    }

}
