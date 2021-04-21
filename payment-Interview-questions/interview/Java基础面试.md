# Java 基础面试题
[TOC]

## 一、面向对象

### 1、private修饰的方法可以通过反射访问,那么private的意义是什么? [[参考]](https://www.jianshu.com/p/a328cf491e06)
```
private想表达的不是“安全性”的意思，而是OOP的封装概念。
```
### Error、Exception和RuntimeException的区别，作用又是什么？

```
```
## 二、锁机制
### 1、synchronized特点
```
    （1）是一个非公平锁
    （2）可重入锁
    （3）不可中断
    （4）锁优化（锁升级）无锁->偏向锁->轻量级锁->重量级锁
```

