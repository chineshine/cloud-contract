# spring cloud contract
本项目用于讲解 spring cloud contract  
并编写了三个 sample ==> producer,producer-git,consumer

## 基本概念
- 作用  
spring cloud contract 是用于 spring cloud 微服务项目中,spring-feign 的测试  
- 场景  
如 spring cloud 微服务项目中,A项目利用 spring-feign 调用了 B项目的接口,此时在开发过程中,A项目要测试,然而B项目并未启动,那么A项目在跑测试类的时候就会报错,此时就需要用到 spring-cloud-contract
- 概念解析  
spring-cloud-contract 将提供接口的B项目称之为生产者(producer),同时将调用接口的A项目称之为消费者(consumer),开发人员需要为为每一个被调用的接口编写票根(stub),生产者通过票根(stub)生成契约(contract);消费者根据契约(contract)调用接口(restful接口).注意通过契约调用的接口,返回的值是固定值,只供测试的时候使用

## 三个 sample 的作用
- producer  
  编写票根,提供契约的生产者
- producer-git  
  编写票根,提供契约的生产者,同时会将票根和契约上传到 git仓库 上,消费者通过从 git仓库 拉取契约,调用接口
- consumer  
  接口调用的消费者

## 上手实践
参考 [quick-tour.md](./quick-tour.md)

## 参考地址
### `maven` 插件参考地址   
https://cloud.spring.io/spring-cloud-contract/spring-cloud-contract-maven-plugin/index.html  
### `spring` 官方的简单介绍
https://cloud.spring.io/spring-cloud-contract/
### 官方文档
http://cloud.spring.io/spring-cloud-contract/2.0.x/single/spring-cloud-contract.html
### `spring gitHub demo`
https://github.com/spring-cloud-samples/spring-cloud-contract-samples
### 官方文档相关 `DSL` 讲解
https://cloud.spring.io/spring-cloud-contract/multi/multi__contract_dsl.html
