package org.wizunion.jvm;

import java.rmi.server.ExportException;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) throws Exception {
        ClassExplorer ce=new ClassExplorer("/Users/woodslee/research/gitee/JavaClassExplorer/target/classes/org/wizunion/jvm/ClassExplorer.class");
        System.out.println(ce);
    }


}
