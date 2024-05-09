package camp.view;
import camp.service.StudentManager;
import camp.utility.ConsoleIO;
import camp.model.Student;

import java.util.Arrays;
import java.util.List;



public class StudentView {


    // 필드
    private final StudentManager studentManager; // 수강생 관리 객체
    private final ConsoleIO consoleIO; // 입출력 유틸리티 객체

    // 생성자
    public StudentView(StudentManager studentManager, ConsoleIO consoleIO) {
        this.studentManager = studentManager;
        this.consoleIO = consoleIO;
    }

    // 수강생 관리 메뉴 핸들링 메서드
    public void handleStudentMenu() {
        boolean running = true;
        while (running) {
            consoleIO.print("==================================");
            consoleIO.print("수강생 관리 실행 중...");
            consoleIO.print("1. 수강생 등록");
            consoleIO.print("2. 수강생 목록 조회");
            consoleIO.print("3. 수강생 정보(수정/삭제)");
            consoleIO.print("4. 메인 화면 이동");

            int choice = consoleIO.getIntInput("관리 항목을 선택하세요...");
            switch (choice) {
                case 1:
                    createStudentProcess(); // 수강생 등록 로직
                    break;
                case 2:
                    displayStudents();  // 수강생 조회 로직
                    break;
                case 3:
                    searchAndDisplayStudent(); // 수강생 정보 검색 로직
                    break;
                case 4:
                    running = false; // 수강생 관리 반복 로직 탈출
                    break;
                default:
                    consoleIO.print("잘못된 입력입니다. 다시 시도하세요."); // 잘못된 입력에 대한 메시지 출력
                    break;
            }
        }
    }


    // 수강생 등록 로직
    private void createStudentProcess() {
        consoleIO.print("\n수강생을 등록합니다...");
        String studentName;
        while (true) {
            studentName = consoleIO.getStringInput("수강생 이름 입력: ");
            if (studentName != null && !studentName.trim().isEmpty()) {
                break; // 유효한 이름이 입력되면 반복 종료
            } else {
                consoleIO.print("수강생 이름은 비어 있을 수 없습니다. 다시 입력해주세요.");
            }
        }
        List<String> allSubjectNames = studentManager.selectSubjectNamesList();
        studentManager.createStudent(studentName, allSubjectNames);
        consoleIO.print("수강생 등록 성공!\n");
    }

    // 수강생 조회 로직
    private void displayStudents() {
        boolean running = true;
        while (running) {
            consoleIO.print("\n수강생 목록 조회:");
            consoleIO.print("1. 전체 수강생 조회");
            consoleIO.print("2. 상태별 수강생 목록 조회");
            consoleIO.print("3. 수강생 관리 메뉴로 돌아가기");

            int choice = consoleIO.getIntInput("조회 항목을 선택하세요:");
            switch (choice) {
                case 1:
                    displayAllStudents();// 전체 수강생 목록 조회 메서드 호출
                    running = false; // 메서드 호출 후 수강생 조회 로직 반복 로직 탈출
                    break;
                case 2:
                    displayStudentsByStatus(); // 상태별 수강생 목록 조회 메서드 호출
                    running = false; // 메서드 호출 후 수강생 조회 로직 반복 로직 탈출
                    break;
                case 3:
                    running = false; // 수강생 조회 로직 반복 로직 탈출
                    break;
                default:
                    consoleIO.print("잘못된 입력입니다. 다시 시도하세요."); // 수강생 조회 로직 다시 반복
                    break;
            }
        }
    }

    // 전체 수강생 목록 조회 메서드
    private void displayAllStudents() {
        List<Student> students = studentManager.getAllStudents();
        if (students.isEmpty()) {
            consoleIO.print("등록된 수강생이 없습니다.");
        } else {
            students.forEach(student -> {
                // 아이디와 이름만 출력
                String studentInfo = String.format("ID: %s, Name: %s", student.getStudentId(), student.getStudentName());
                consoleIO.print(studentInfo);
            });
        }
    }

    // 상태별 수강생 목록 조회 메서드
    private void displayStudentsByStatus() {
        boolean running = true;
        while (running) {
            consoleIO.print("\n상태별 수강생 목록 조회:");
            consoleIO.print("1. Green");
            consoleIO.print("2. Yellow");
            consoleIO.print("3. Red");
            consoleIO.print("4. Unknown");
            consoleIO.print("5. 수강생 관리 메뉴로 돌아가기");

            int choice = consoleIO.getIntInput("조회 항목을 선택하세요:");
            if (choice == 5) {
                running = false;  // 수강생 조회 로직 탈출
            } else {
                String status = switch (choice) {
                    case 1 -> "Green";
                    case 2 -> "Yellow";
                    case 3 -> "Red";
                    case 4 -> "Unknown";
                    default -> {
                        consoleIO.print("잘못된 입력입니다. 다시 시도하세요.");
                        yield "";
                    }
                };

                if (!status.isEmpty()) {
                    List<Student> filteredStudents = studentManager.getStudentsByStatus(status);
                    if (filteredStudents.isEmpty()) {
                        consoleIO.print("해당 상태의 수강생이 없습니다.");
                    } else {
                        filteredStudents.forEach(student -> {
                            String studentInfo = String.format("ID: %s, Name: %s, Status: %s",
                                    student.getStudentId(), student.getStudentName(), student.getStudentStatus());
                            consoleIO.print(studentInfo);
                        });
                    }
                    running = false; // 수강생 조회 로직 탈출
                }
            }
        }
    }

    // 수강생 정보 검색 로직
    private void searchAndDisplayStudent() {
        String studentId = consoleIO.getStringInput("조회할 수강생의 ID를 입력하세요: ");
        studentManager.getStudentById(studentId)
                // Optional 의 ifPresentOrElse 사용 :Optional 객체에 값이 존재하는 경우와 그렇지 않은 경우에 대해 각각 다른 로직을 실행
                .ifPresentOrElse(
                        student -> {
                            consoleIO.print(student.toString()); // 수강생 정보 출력
                            displayStudentInformation(student);// 수강생 정보 관리 옵션 메서드
                        },
                        () -> consoleIO.print("해당 ID의 수강생을 찾을 수 없습니다.") // 수강생 id 가 존재하지 않을경우
                );
    }


    // 수강생 정보 관리 옵션 제공 메서드
    private void displayStudentInformation(Student student) {
        boolean running = true;
        while (running) {
            consoleIO.print("\n수강생 정보 관리:");
            consoleIO.print("1. 수강생 이름 수정");
            consoleIO.print("2. 수강생 상태 수정");
            consoleIO.print("3. 수강생 삭제");
            consoleIO.print("4. 이전 메뉴로 돌아가기");

            int choice = consoleIO.getIntInput("옵션을 선택하세요:");
            switch (choice) {
                case 1:
                    updateStudentName(student); // 수강생 이름 수정 메서드
                    break;
                case 2:
                    updateStudentStatus(student); // 수강생 상태 수정 메서드
                    break;
                case 3:
                    deleteStudent(student); // 수강생 삭제 메서드
                    running = false; // 옵션 메뉴 종료
                    break;
                case 4:
                    running = false; // 옵션 메뉴 종료
                    break;
                default:
                    consoleIO.print("잘못된 입력입니다. 다시 시도하세요.");
                    break;
            }
        }
    }

    // 수강생 이름을 업데이트하는 메서드
    private void updateStudentName(Student student) {
        String newName;
        while (true) {
            newName = consoleIO.getStringInput("새로운 이름을 입력하세요:");
            if (newName != null && !newName.trim().isEmpty()) {
                break; // 비어 있지 않은 이름이 입력되면 반복 종료
            } else {
                consoleIO.print("이름이 비어 있을 수 없습니다. 다시 입력해주세요.");
            }
        }

        studentManager.updateStudentName(student, newName);
        consoleIO.print("수강생 이름이 업데이트되었습니다.");
        consoleIO.print("업데이트된 수강생 정보: " + student);
    }

    // 수강생 상태를 업데이트하는 메서드
    private void updateStudentStatus(Student student) {
        // 유효한 상태 목록
        List<String> validStates = Arrays.asList("GREEN", "YELLOW", "RED");

        // 새로운 상태 입력 및 유효성 검사
        String newStatus;
        while (true) {
            newStatus = consoleIO.getStringInput("새로운 상태를 입력하세요 (GREEN, YELLOW, RED):");
            newStatus = newStatus.toUpperCase(); // 입력값을 대문자로 변환

            if (validStates.contains(newStatus)) {
                break; // 유효한 상태가 입력되면 반복 종료
            } else {
                consoleIO.print("입력한 상태가 유효하지 않습니다. GREEN, YELLOW, RED 중 하나를 입력해주세요.");
            }
        }

        // 상태 업데이트
        studentManager.updateStudentStatus(student, newStatus);
        consoleIO.print("수강생 상태가 업데이트되었습니다.");
        consoleIO.print("업데이트된 수강생 정보: " + student);
    }


    // 수강생 정보를 삭제하는 메서드
    private void deleteStudent(Student student) {
        consoleIO.print("삭제할 수강생 정보: " + student.toString());
        String confirm = consoleIO.getStringInput("이 수강생을 정말로 삭제하시겠습니까? (y/n):").trim().toLowerCase();

        while (!confirm.equals("y") && !confirm.equals("n")) {
            consoleIO.print("잘못된 입력입니다. 'y' 또는 'n'을 입력해주세요.");
            confirm = consoleIO.getStringInput("이 수강생을 정말로 삭제하시겠습니까? (y/n):").trim().toLowerCase();
        }

        if (confirm.equals("n")) {
            consoleIO.print("수강생 정보 삭제가 취소되었습니다.");
            return;
        }

        studentManager.deleteStudent(student);
        consoleIO.print("수강생 정보가 삭제되었습니다.");
    }



}