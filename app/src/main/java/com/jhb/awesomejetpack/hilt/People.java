package com.jhb.awesomejetpack.hilt;

/**
 * @author jhb
 * @date 2020/9/9
 */
public class People {

    private final Student student;

//    @Inject
    Student student2;

    public People() {
        student = new Student();
    }

    public People(Student student) {
        this.student = student;
    }
}
