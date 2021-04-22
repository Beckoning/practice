# Java 基础面试题
[TOC]

## 一、面向对象
### 基础


    





pring/Springmvnc/Springboot
  beanFactory 和ApplicationContext的区别
    是Spring里面最低层的接口，提供了最简单的容器的功能，只提供了实例化对象和拿对象的功能；
    bean加载 :BeanFactory在启动的时候不会去实例化Bean，中有从容器中拿Bean的时候才会去实例化；/ApplicationContext在启动的时候就把所有的Bean全部实例化了。还可以配置懒加载
    applicationContext特有的功能:
       spring国际化例子（MessageSource）
       访问资源，如URL和文件（ResourceLoader）
       载入多个（有继承关系）上下文
       消息发送、响应机制（ApplicationEventPublisher）
       AOP（拦截器）

  ico /Aop
  cglib 、java 动态代理
  bean生命周期
  beanFactory 和FactoryBean。BeanFaactory 是beanFactory,也就是IOC容器或对象工厂，FactoryBean是个Bean.在Spring中，所有的Bean都是由BeanFactory(也就是IOC容器)来进行管理的。但对FactoryBean而言，为IOC容器中Bean的实现提供了更加灵活的方式, 这个Bean不是简单的Bean，而是一个能生产或者修饰对象生成的工厂Bean,它的实现与设计模式中的工厂模式和修饰器模式类似 .

  Spring事务/声明式事务、编程式事务  事务的隔离机制、传播方式
  bean工厂的后置处理
  bean的后置处理器
  Springmvnc的工作流程
  spring解决循环依赖(三级缓存)

  为何使用三级缓存而非二级:
    使用三级缓存而非二级缓存并不是因为只有三级缓存才能解决循环引用问题，其实二级缓存同样也能很好解决循环引用问题。使用三级而非二级缓存并非出于IOC的考虑，而是出于AOP的考虑，即若使用二级缓存，在AOP情形下，注入到其他bean的，不是最终的代理对象，而是原始对象。

















