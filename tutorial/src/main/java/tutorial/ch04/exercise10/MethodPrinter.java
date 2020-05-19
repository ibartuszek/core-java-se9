package tutorial.ch04.exercise10;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Use the MethodPrinter program in Section 4.5.1, “Enumerating Class
 * Members” (page 168) to enumerate all methods of the int[] class. Extra
 * credit if you can identify the one method (discussed in this chapter) that is
 * wrongly described.
 */

//    Class<?> cl=Class.forName(className);
//    while(cl!=null){
//    for(Method m:cl.getDeclaredMethods()){
//    System.out.println(
//    Modifier.toString(m.getModifiers())+" "+
//    m.getReturnType().getCanonicalName()+" "+
//    m.getName()+
//    Arrays.toString(m.getParameters()));
//    }cl=
//    cl.getSuperclass();
//    }

public class MethodPrinter {

    public void printMethods(final String className) {
        try {
            Class<?> cl = Class.forName(className);
            while (cl != null) {
                for (Method m : cl.getDeclaredMethods()) {
                    System.out.println(
                        Modifier.toString(m.getModifiers()) + " " +
                            m.getReturnType().getCanonicalName() + " " +
                            m.getName() +
                            Arrays.toString(m.getParameters()));
                }
                cl =
                    cl.getSuperclass();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MethodPrinter().printMethods(int[].class.getName());
    }

}
