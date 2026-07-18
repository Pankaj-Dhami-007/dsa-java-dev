package interviewQ;


class Test{
    int a, b;
    public Test(int a, int b){
        this.a = a;
        this.b = b;
    }

    int add(){
        return a+b;
    }
}
public class ObjectClass {

    public static void main(String[] args) {
     Test t1 = new Test(1, 2);
        System.out.println(t1.getClass());

        Test t2 = new Test(1, 2);
        Test t3 = t1;
        System.out.println(t1.equals(t2));
        System.out.println(t1.hashCode());
        System.out.println(t2.hashCode());
        System.out.println(t1.equals(t3));

    }
}
