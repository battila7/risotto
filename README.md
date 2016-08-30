# Risotto
[![Build Status](https://travis-ci.org/battila7/risotto.svg?branch=master)](https://travis-ci.org/battila7/risotto)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e5d73d8dcd744407ac39a7ed53e72deb)](https://www.codacy.com/app/bagossyattila_2/risotto?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=battila7/risotto&amp;utm_campaign=Badge_Grade)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/battila7/risotto/blob/master/LICENSE)
[![Beerpay](https://beerpay.io/battila7/risotto/badge.svg)](https://beerpay.io/battila7/risotto)

Risotto allows you to create delicious applications whatever your ingredients are.

### What is Risotto?

Risotto is a dependency injection container designed for applications using the Java SE 8 API.

### How is Risotto different than other solutions?

Risotto encourages intelligent container design through its container tree. Developers can easily create a complex container structure that supports dependency isolation and fast dependency-lookup. With parent-child relationships and binding scopes, Risotto allows developers to design in detail how dependency injection should be performed in their application.

When writing Java applications, we pay special attention to encapsulation and correct visibility. Why don't we pay attention to visibility when using DI? That's what we had in mind when designed Risotto. 

### That sounds like a lot of work!

Actually, it's not! Cooking some tasty Risotto is as easy as:

~~~~Java
    Container rootContainer = Risotto.addRootContainer(MyRootContainer.class);
~~~~

When adding a root container, Risotto configures the whole container tree and performs dependency resolution. Then you can request instances:

~~~~
    rootContainer.getInstance(Application.class)
                 .ifPresent(Application::run);
~~~~

### Show me a container!

Right away, sir! A simple container would look somehow like this:

~~~~Java
public final class MyContainer extends Container {
    @BindingSupplier
    public Application application() {
        return new Application();
    }
}
~~~~

This container utilizes a *binding supplier* method that will be lazily called upon the first request to an `Application` instance.

### May I ask for some resources?

While the Wiki is being written, you can check out the Javadoc and the [Hello Risotto](https://github.com/battila7/hello-risotto) example project.
