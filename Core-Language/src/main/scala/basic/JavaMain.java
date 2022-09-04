package org.example.application.basic;

import java.util.function.Function;

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

    // We declare a function using the access modifier, the return type, the argument type and
    // argument. Here we return a new string including the argument.
    public String methodWithArgument(String argument) {
        return "This argument " + argument + " is returned from the method";
    }

    public void assigningFunctionsToVariables() {
        Function<String, String> methodWithArgument1 = this::methodWithArgument;
        System.out.println(methodWithArgument1.apply("'calling the method with this argument'"));
    }

    // We declare a static method returning a string using the below syntax
    public static String staticMethod() {
        return "This string is returned from the static method";
    }
}

public class JavaMain {
    public static void main(String[] args) {

        String string = "String";
        int integer = 1;

        // Here we access the static fields and methods of the example class without an instance of the object
        System.out.println(ExampleClass.staticField);
        System.out.println(ExampleClass.staticMethod());

        // Here we create an instance of the class
        ExampleClass exampleClass = new ExampleClass();

        // Here we access the fields and methods of the instance
        System.out.println(exampleClass.field);
        System.out.println(exampleClass.method());
        exampleClass.assigningFunctionsToVariables();
    }
}
