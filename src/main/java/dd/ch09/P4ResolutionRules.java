package dd.ch09;

public class P4ResolutionRules {
    public static void main(String[] args) {
        new C().hello();
        new D().hello();
        new E().hello();
    }
}

interface A {
    public default void hello() {
        System.out.println("Hello from A");
    }
}

interface B {
    public default void hello() {
        System.out.println("Hello from B");
    }
}

interface B1 extends A {
    public default void hello() {
        System.out.println("Hello from B1");
    }
}

class K {
    public void hello() {
        System.out.println("Hello from K");
    }
}

// forced to override
class C implements B, A {
    @Override
    public void hello() {
        A.super.hello();
    }
}

// most specific wins
class D implements B {
}

// class wins
class E extends K implements A, B {
}