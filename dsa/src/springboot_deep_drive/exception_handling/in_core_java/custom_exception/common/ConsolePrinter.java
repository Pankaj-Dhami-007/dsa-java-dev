package springboot_deep_drive.exception_handling.in_core_java.custom_exception.common;

public final class ConsolePrinter {

    private ConsolePrinter() {

    }

    public static void print(ErrorResponse response) {
        System.out.println(response);
    }

}
