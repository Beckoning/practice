# Java kafka
[TOC]

## 一、kafka
### 基础


   
#### 1、运行时数据区为什么Kafka中topic越多效率可能会变低:

```
1、kafka是向分区中写数据,而rocket是统一向commitlog写数据
2、RocketMq 和 Kafka 的性能基线就不在一个水准上，Kafka 利用顺序读写已经把一个 MQ 的性能压榨到比较极限的水准了，超高的性能也带来了一些问题，
   每一个 topic 在硬盘上用一个 logfile 表示，通过顺序读写可以使性能非常高，但当多个 topic 的多个 partition 分布在同一台机器上时，
   原本的顺序读写就变为随机读写，partition 越多，随机就越严重，apache pulsar 解决了这个问题是使用 bookkeeper 做 store，这样做同时也降低
   了性能基线，业务需要很高的性能时，用 Kafka 没错，堆机器就行了，没钱堆机器时也不需要那么高的性能。 
```



















