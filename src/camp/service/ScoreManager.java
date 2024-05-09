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
    public void updateRoundScoreBySubject(){
        String studentId = consoleIO.getStringInput("수강생의 고유 번호를 입력하세요 : ");
        String subjectId = consoleIO.getStringInput("과목의 고유 번호를 입력하세요 : ");
        int scoreId = consoleIO.getIntInput("수정 할 회차 입력 : ");

        if (scoreStore.isEmpty()){
            consoleIO.print("현재 어떤 정보 점수도 기록되어있지 않습니다.");
        }
        else {
            for (Score findingScore : scoreStore){
                if(findingScore.returnFindingStudentId().equals(studentId)){ //학생 고유번호가 같은걸 찾고
                    if (findingScore.returnFindingScoreId() == scoreId){ //회자 번호가 같은걸 찾았다면
                        consoleIO.print("해당하는 회자를 잦았습니다. 현재점수("+ findingScore.returnFindingTestScore()+"점)");
                        int score = consoleIO.getIntInput("수정할 시험점수 입력( 0 ~ 100 ) : ");
                        if(score >= 0 && score <= 100){
                            findingScore.modifiScore(score); //class 안에있는 수정 method
                            for (Subject subject : subjectStore){
                                if (subject.getSubjectType().equals(subjectId)){
                                    findingScore.modifiGrade(makeScoreGrade(subject.getSubjectType(),score));
                                }
                            }
                        }
                        else {
                            consoleIO.print("---수정할 시험점수가 0~100사이의 정수가 아닙니다.---");
                        }
                    }
                }
                else {
                    consoleIO.print("---해당되는 학생 정보가 없습니다.---");
                }
            }
        }
    }

    public void removeRoundScoreBySubject() {
        String studentId = consoleIO.getStringInput("수강생의 고유 번호를 입력하세요 : ");
        String subjectId = consoleIO.getStringInput("과목의 고유 번호를 입력하세요 : ");
        int scoreId = consoleIO.getIntInput("삭제 할 회차 입력 : ");
        // 기능 구현 (삭제 할 과목 및 회차, 점수)
        String removeCheck = consoleIO.getStringInput("해당 학생의 과목별 시험점수를 삭제하시겠습니까?(y/n) : ");
        if(removeCheck.equals("y") || removeCheck.equals("Y") ){
            for (Score findingScore : scoreStore){
                if(findingScore.returnFindingStudentId().equals(studentId)) {
                    if(findingScore.returnFindingSubjectId().equals(subjectId)) {
                        if (findingScore.returnFindingScoreId() == scoreId){
                            scoreStore.remove(findingScore);
                            System.out.println("해당하는 "+subjectId+"학생의 "+subjectId+"과목의 "+scoreId+"회차 점수를 삭제했습니다.");
                        }
                        else{
                            System.out.println("해당하는 학생의 과목 점수가 없습니다.");
                        }
                    }
                }
            }
        }
        else{
            System.out.println("---취소를 선택하셨습니다. 삭제를 취소합니다.---");
        }
    }

    public char makeScoreGrade(String subjectType, int score){
        char grade='N';
        if (subjectType.equals("MANDATORY")){
            if (score >= 95)
                grade = 'A';
            else if (score >= 90)
                grade = 'B';
            else if (score >= 80)
                grade = 'C';
            else if (score >= 70)
                grade = 'D';
            else if (score >= 60)
                grade = 'F';
            else
                grade = 'N';
        } else if (subjectType.equals("CHOICE")) {
            if (score >= 90)
                grade = 'A';
            else if (score >= 80)
                grade = 'B';
            else if (score >= 70)
                grade = 'C';
            else if (score >= 60)
                grade = 'E';
            else if (score >= 50)
                grade = 'F';
            else
                grade = 'N';
        }
        return grade;
    }

}
