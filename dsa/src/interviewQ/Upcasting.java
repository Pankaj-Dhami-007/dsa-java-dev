package interviewQ;


class A{

    int b = 200;

    public void hello(){
        System.out.println("helloo");
    }
}
class B extends A{

    int a = 1000;
    @Override
    public void hello(){
        System.out.println("by....");
    }

    public void show(){
        System.out.println("Lokesh");
    }
}

public class Upcasting {
    public static void main(String[] args) {
        A a = new B();
        a.hello();
        System.out.println(a.b);

    }
}
