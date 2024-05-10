package camp.view;

import camp.model.Subject;
import camp.service.StudentManager;
import camp.service.ScoreManager;
import camp.utility.ConsoleIO;
import camp.model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentView {

    private final StudentManager studentManager;
    private final ScoreManager scoreManager;
    private final ConsoleIO consoleIO;
    // 상수 필드(고정값)
    private static final String SUBJECT_TYPE_MANDATORY = "MANDATORY";// 필수 과목
    private static final String SUBJECT_TYPE_CHOICE = "CHOICE";//선택과목

    // 생성자
    public StudentView(StudentManager studentManager, ScoreManager scoreManager, ConsoleIO consoleIO) {
        this.studentManager = studentManager;
        this.scoreManager = scoreManager;
        this.consoleIO = consoleIO;
    }
    public void handleStudentMenu() {
        boolean running = true;
        while (running) {
            consoleIO.print("\n==================================");
            consoleIO.print("수강생 관리 실행 중...");
            consoleIO.print("1. 수강생 등록");
            consoleIO.print("2. 수강생 목록 조회");
            consoleIO.print("3. 수강생 정보(수정/삭제)");
            consoleIO.print("4. 메인 화면 이동");

            int choice = consoleIO.getIntInput("관리 항목을 선택하세요:");
            switch (choice) {
                case 1:
                    createStudentProcess();
                    break;
                case 2:
                    displayStudents();
                    break;
                case 3:
                    searchAndDisplayStudent();
                    break;
                case 4:
                    running = false;
                    consoleIO.print("메인 메뉴로 돌아갑니다.");
                    break;
                default:
                    consoleIO.print("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }

    private List<String> selectSubjects(String subjectType, int minSubjects) {
        List<Subject> availableSubjects = studentManager.getSubjectsByType(subjectType);
        List<String> selectedSubjectNames = new ArrayList<>();
        consoleIO.print(subjectType.equals(SUBJECT_TYPE_MANDATORY) ? "\n필수 과목 선택:" : "\n선택 과목 선택:");

        for (int i = 0; i < availableSubjects.size(); i++) {
            consoleIO.print((i + 1) + ". " + availableSubjects.get(i).getSubjectName());
        }

        while (true) {
            int choice = consoleIO.getIntInput("선택하고 싶은 과목 번호를 입력하세요 (선택을 마치려면 0을 입력):");
            if (choice == 0) {
                if (selectedSubjectNames.size() >= minSubjects) {
                    break;  // 최소 과목 선택 수를 충족시 선택 종료
                } else {
                    consoleIO.print("최소 " + minSubjects + "개의 과목을 선택해야 합니다. 계속 선택하세요.");
                }
            } else if (choice > 0 && choice <= availableSubjects.size()) {
                String subjectName = availableSubjects.get(choice - 1).getSubjectName();
                if (!selectedSubjectNames.contains(subjectName)) {
                    selectedSubjectNames.add(subjectName);
                    consoleIO.print(subjectName + " 과목이 선택되었습니다.");
                } else {
                    consoleIO.print("이미 선택된 과목입니다.");
                }
            } else {
                consoleIO.print("잘못된 입력입니다. 다시 시도해 주세요.");
            }
        }
        return selectedSubjectNames;
    }

    private void createStudentProcess() {
        consoleIO.print("\n수강생을 등록합니다...");
        String studentName = consoleIO.getStringInput("수강생 이름 입력: ");
        while (studentName.trim().isEmpty()) {
            consoleIO.print("수강생 이름은 비어 있을 수 없습니다. 다시 입력해주세요.");
            studentName = consoleIO.getStringInput("수강생 이름 입력: ");
        }
        List<String> mandatorySubjects = selectSubjects(SUBJECT_TYPE_MANDATORY, 3);
        List<String> choiceSubjects = selectSubjects(SUBJECT_TYPE_CHOICE, 2);
        List<String> allSubjectNames = new ArrayList<>();
        allSubjectNames.addAll(mandatorySubjects);
        allSubjectNames.addAll(choiceSubjects);
        studentManager.createStudent(studentName, allSubjectNames);
        consoleIO.print("수강생 등록 성공!\n");
    }

    private void displayStudents() {
        boolean running  = true;
        while (running ) {
            consoleIO.print("\n수강생 목록 조회:");
            consoleIO.print("1. 전체 수강생 조회");
            consoleIO.print("2. 상태별 수강생 목록 조회");
            consoleIO.print("3. 수강생 관리 메뉴로 돌아가기");

            int choice = consoleIO.getIntInput("조회 항목을 선택하세요:");
            switch (choice) {
                case 1:
                    displayAllStudents();
                    break;
                case 2:
                    displayStudentsByStatus();
                    break;
                case 3:
                    running  = false;
                    break;
                default:
                    consoleIO.print("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }

    private void displayAllStudents() {
        List<Student> students = studentManager.getAllStudents();
        if (students.isEmpty()) {
            consoleIO.print("등록된 수강생이 없습니다.");
        } else {
            students.forEach(student -> {
                String studentInfo = String.format("ID: %s, Name: %s", student.getStudentId(), student.getStudentName());
                consoleIO.print(studentInfo);
            });
        }
    }

    private void displayStudentsByStatus() {
        consoleIO.print("\n상태별 수강생 목록 조회:");
        consoleIO.print("1. Green");
        consoleIO.print("2. Yellow");
        consoleIO.print("3. Red");
        consoleIO.print("4. Unknown");
        consoleIO.print("5. 수강생 관리 메뉴로 돌아가기");

        int choice = consoleIO.getIntInput("조회 항목을 선택하세요:");
        if (choice == 5) {
            return; // 메뉴로 돌아감
        }
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
        }
    }

    private void searchAndDisplayStudent() {
        String studentId = consoleIO.getStringInput("조회할 수강생의 ID를 입력하세요: ");
        studentManager.getStudentById(studentId)
                .ifPresentOrElse(
                        student -> {
                            consoleIO.print(student.toString());
                            displayStudentInformation(student);
                        },
                        () -> consoleIO.print("해당 ID의 수강생을 찾을 수 없습니다.")
                );
    }

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
                    updateStudentName(student);
                    break;
                case 2:
                    updateStudentStatus(student);
                    break;
                case 3:
                    deleteStudent(student);
                    running = false; // 메뉴 종료
                    break;
                case 4:
                    running = false; // 메뉴 종료
                    break;
                default:
                    consoleIO.print("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }

    private void updateStudentName(Student student) {
        String newName = consoleIO.getStringInput("새로운 이름을 입력하세요:");
        while (newName.trim().isEmpty()) {
            consoleIO.print("이름이 비어 있을 수 없습니다. 다시 입력해주세요.");
            newName = consoleIO.getStringInput("새로운 이름을 입력하세요:");
        }
        studentManager.updateStudentName(student, newName);
        consoleIO.print("수강생 이름이 업데이트되었습니다.");
        consoleIO.print("업데이트된 수강생 정보: " + student);
    }

    private void updateStudentStatus(Student student) {
        List<String> validStates = Arrays.asList("GREEN", "YELLOW", "RED");
        String newStatus = consoleIO.getStringInput("새로운 상태를 입력하세요 (GREEN, YELLOW, RED):").toUpperCase();

        while (!validStates.contains(newStatus)) {
            consoleIO.print("입력한 상태가 유효하지 않습니다. GREEN, YELLOW, RED 중 하나를 입력해주세요.");
            newStatus = consoleIO.getStringInput("새로운 상태를 입력하세요 (GREEN, YELLOW, RED):").toUpperCase();
        }
        studentManager.updateStudentStatus(student, newStatus);
        consoleIO.print("수강생 상태가 업데이트되었습니다.");
        consoleIO.print("업데이트된 수강생 정보: " + student);
    }

    private void deleteStudent(Student student) {
        consoleIO.print("삭제할 수강생 정보: " + student);
        String confirm = consoleIO.getStringInput("이 수강생을 정말로 삭제하시겠습니까? (y/n):").trim().toLowerCase();

        while (!confirm.equals("y") && !confirm.equals("n")) {
            consoleIO.print("잘못된 입력입니다. 'y' 또는 'n'을 입력해주세요.");
            confirm = consoleIO.getStringInput("이 수강생을 정말로 삭제하시겠습니까? (y/n):").trim().toLowerCase();
        }

        if (confirm.equals("y")) {
            scoreManager.removeScoresByStudentId(student.getStudentId());
            studentManager.deleteStudent(student);
            consoleIO.print("수강생 정보가 삭제되었습니다.");
        } else {
            consoleIO.print("수강생 정보 삭제가 취소되었습니다.");
        }
    }
}
