# spring-Cloud-contract 介绍
### 流程1 --生产者
#### 1.引入包
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-contract-verifier</artifactId>
    <scope>test</scope>
  </dependency>
```
#### 2.配置插件  
插件配置属性参考地址:  
https://cloud.spring.io/spring-cloud-contract/spring-cloud-contract-maven-plugin/convert-mojo.html
```
  <properties>
    <spring-cloud-contract.version>2.0.2.BUILD-SNAPSHOT</spring-cloud-contract.version>
  </properties>

  <plugin>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-contract-maven-plugin</artifactId>
      <version>${spring-cloud-contract.version}</version>
      <extensions>true</extensions>
      <configuration>
        <baseClassForTests>c.s.producer.controller.HelloControllerBase</baseClassForTests>
      </configuration>
    </plugin>
```

### 流程2 -- 生产者
编写 `yml` 文件,参考地址:  
https://cloud.spring.io/spring-cloud-contract/multi/multi__contract_dsl.html#contract-dsl-http-top-level-elements

### 流程3 -- 生产者
利用编写的 `yml` 生成测试,测试生产者端的逻辑
```
  mvn generate-test-sources
```
运行测试  
```
  mvn test
```
注意: 生成的测试类的位置在
```
  ${project}/target/generated-test-sources
```

### 流程4 -- 消费者
在消费者端编写测试用例进行测试

### 总结
大致就是在生产者端编写 `yml` 文件之后  
生成测试类对自身应用逻辑进行测试  
测试完成通过之后,通过已写好的 `yml` 文件生成 `*stub` 包  
消费者端引入 `*stub` 之后,编写测试用例对其测试  
达到生产者消费者两边逻辑一致  
