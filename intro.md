# spring-Cloud-contract 介绍

### 流程1 -- 生产者
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

`yml` 必须放在该位置:  
```
${project.basedir}/src/test/resources/contracts
# 该位置可通过插件配置参数修改 -> contractsDirectory
```
如果是 `REMOTE` 模式,则必须为 `yml` 配置一个远程仓库  
远程模式配置参考项目 `producer-git`  
远程仓库目录形式:
```
  META-INF/<group-id>/<artifact-id>/<version>/contracts  

  group-id: 对应 maven 项目的 groupId
  artifact-id: 对应 maven 项目的 artifactId
  version: 对应 maven 项目的版本
```
`yml` 文件具体命名方式
```
  # / 代表一个文件夹或目录
  业务名称/rest/实际请求.yml
```

### 流程3 -- 生产者
利用编写的 `yml` 生成测试类,测试生产者端的逻辑
```
  mvn generate-test-sources
```
运行测试  
```
  mvn test
```
注意:  
1) 生成的测试类的位置在
```
  ${project}/target/generated-test-sources
```
2) 测试生成的测试类注意对应 `controller` 的传参类型  
   如果在 `yml` 文件中定义了 `request.headers.contentType` 或 `response.headers.contentType`  
   则在对应 `controller` 的对应方法的 `@*Mapping`(如 `@PostMapping`,`@GetMapping` 等)  
   需要添加参数 `consumes`,`produces`  
   `consumes` 对应 `request`, `produces` 对应 `response`  

### 流程4 -- 消费者
`pom.xml` 中引入:
```
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-contract-stub-runner</artifactId>
    <scope>test</scope>
  </dependency>
```
在消费者端编写测试用例进行测试

### 总结
大致就是在生产者端编写 `yml` 文件之后  
生成测试类对自身应用逻辑进行测试  
测试完成通过之后,通过已写好的 `yml` 文件生成 `*stub` 包  
消费者端引入 `*stub` 之后,编写测试用例对其测试  
达到生产者消费者两边逻辑一致  
