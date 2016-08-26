# Risotto
[![Build Status](https://travis-ci.org/battila7/risotto.svg?branch=master)](https://travis-ci.org/battila7/risotto)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e5d73d8dcd744407ac39a7ed53e72deb)](https://www.codacy.com/app/bagossyattila_2/risotto?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=battila7/risotto&amp;utm_campaign=Badge_Grade)
[![Stories in Ready](https://badge.waffle.io/battila7/risotto.png?label=ready&title=Ready)](https://waffle.io/battila7/risotto)
[![Beerpay](https://beerpay.io/battila7/risotto/badge.svg)](https://beerpay.io/battila7/risotto)

Risotto allows you to create delicious applications whatever your ingredients are.

### What is Risotto?

Risotto is a dependency injection container designed for applications using the Java SE 8 API. 

### How is Risotto different than other solutions?

Instead of having one big container with all the dependencies and bindings of your application, Risotto maintains a tree of individual containers. 

When writing Java applications, developers pay special attention to encapsulation and correct visibility. Why don't we pay attention to visibility when using DI? That's what we had in mind when designed Risotto. 

With parent-child relationships and binding scopes, Risotto allows developers to design in detail how dependency injection should be performed in their application.

### Should I use Risotto?

Definitely, but just for experimenting because Risotto but does not have a stable release yet. Check back regularly for updates!

### How to use Risotto?

While the Wiki and the Javadoc is being updated, please take a look at the [Hello Risotto](https://github.com/battila7/hello-risotto) example project.
