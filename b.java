package javabugs;

import org.extendj.ast.VarAccess;

import java.util.function.Consumer;

public class Bug1 {
  public static void main(String[] args) {
    Bug1.<VarAccess>f(var -> var.decl()); // Works.
    f(VarAccess::decl); // Throws IllegalAccessError.
  }

  private static <T> void f(Consumer<T> consumer) {
  }
}
