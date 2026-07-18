package springboot_deep_drive.exception_handling.in_core_java.custom_exception.model;

public class Student {

    private int id;
    private String name;
    private int age;

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return """
                ----------------------------
                Student
                ----------------------------
                Id   : %d
                Name : %s
                Age  : %d
                ----------------------------
                """.formatted(id, name, age);
    }

}
