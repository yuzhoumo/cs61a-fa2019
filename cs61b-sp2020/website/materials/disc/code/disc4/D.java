class A {
    int x = 5;
    public void m1() {System.out.println("Am1-> " + x);}
    public void m2() {System.out.println("Am2-> " + this.x);}
    public void update() {x = 99;}
}
class B extends A {
    int x = 10;
    public void m2() {System.out.println("Bm2-> " + x);}
    public void m3() {System.out.println("Bm3-> " + super.x);}
    public void m4() {System.out.print("Bm4-> "); super.m2();}
}
class C extends B {
    int y = x + 1;
    public void m2() {System.out.println("Cm2-> " + super.x);}
    /* public void m3() {System.out.println("Cm3-> " + super.super.x);} */
    // Explanation: super.super is not valid.
    public void m4() {System.out.println("Cm4-> " + y);}
    /* public void m5() {System.out.println("Cm5-> " + super.y);} */
    // Explanation: C's superclass B and B's superclass A both don't have the variable y.
}
class D {
    public static void main (String[] args) {
        A b0 = new B();
        System.out.println(b0.x);   // 5 (looks at A.x)
        b0.m1();                    // Am1->5
        b0.m2();                    // Bm2->10
        /* b0.m3(); */              // Explanation: A does not have method m3.

        B b1 = new B();
        b1.m3();                    // Bm3->5
        b1.m4();                    // Bm4->Am2->5

        A c0 = new C();
        c0.m1();                    // Am1->5

        A a1 = (A) c0;
        C c2 = (C) a1;
        c2.m4();                    // Cm4->11
        ((C) c0).m3();              // Bm3->5

        b0.update();
        b0.m1();                    // Am1->99
    }
}
