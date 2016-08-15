package com.dijon.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.dijon.CustomContainer;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Repeatable(Children.class)
public @interface Child {
  Class<? extends CustomContainer> containerClass();

  String name();
}
