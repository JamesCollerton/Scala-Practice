package org.example.application.basic;

// We declare a class with the 'class' keyword
class ExampleClass {
    // We declare a field and give it a value
    public String field = "Field";

    // We declare a static field and give it a value
    public static String staticField = "Static field";

    // We declare a method returning a string using the below syntax
    public String method() {
        return "This string is returned from the method";
    }

    // We declare a static method returning a string using the below syntax
    public static String staticMethod() {
        return "This string is returned from the static method";
    }
}

public class Main {
    public static void main(String[] args) {

        // Here we access the static fields and methods of the example class without an instance of the object
        System.out.println(ExampleClass.staticField);
        System.out.println(ExampleClass.staticMethod());

        // Here we create an instance of the class
        ExampleClass exampleClass = new ExampleClass();

        // Here we access the fields and methods of the instance
        System.out.println(exampleClass.field);
        System.out.println(exampleClass.method());
    }
}
