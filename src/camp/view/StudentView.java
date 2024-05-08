package camp.view;

import camp.service.StudentManager;
import camp.utility.ConsoleIO;


import camp.model.Student;
import camp.service.StudentManager;
import camp.utility.ConsoleIO;

import java.util.ArrayList;
import java.util.List;


public class StudentView {

    // 필드
    private StudentManager studentManager; // 수강생 관리 객체
    private ConsoleIO consoleIO; // 입출력 유틸리티 객체

    // 생성자
    public StudentView(StudentManager studentManager, ConsoleIO consoleIO) {
        this.studentManager = studentManager;
        this.consoleIO = consoleIO;
    }

    // 수강생 관리 메뉴 핸들링 메서드
    public void handleStudentMenu() {
        boolean running = true;
        while (running) {
            displayStudentMenu();
            int choice = consoleIO.getIntInput("관리 항목을 선택하세요...");
            switch (choice) {
                case 1:
                    //createStudentProcess();
                    break;
                case 2:
                    //displayAllStudents();
                    break;
                case 3:
                    //searchAndDisplayStudent();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    consoleIO.print("잘못된 입력입니다. 다시 시도하세요.");
                    break;
            }
        }
    }

    // 수강생 관리 입출력
    private void displayStudentMenu() {
        consoleIO.print("==================================");
        consoleIO.print("수강생 관리 실행 중...");
        consoleIO.print("1. 수강생 등록");
        consoleIO.print("2. 수강생 목록 조회");
        consoleIO.print("3. 수강생 정보(수정/삭제)");
        consoleIO.print("4. 메인 화면 이동");
    }

    /*// 1번(수강생 등록) 선택후 요구되는 입출력 처리(비지니스로직은 studentManager처리후 처리)
        private void createStudentProcess() {
        consoleIO.print("\n수강생을 등록합니다...");
        String studentName = consoleIO.getStringInput("수강생 이름 입력: ");
        List<String> allSubjectNames = studentManager.selectSubjectNamesList();
        studentManager.createStudent(studentName, allSubjectNames);
        consoleIO.print("수강생 등록 성공!\n");
    }*/




}
