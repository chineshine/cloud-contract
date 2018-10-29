# cloud-contract
spring cloud contract samples

## producer
契约的生产者  
执行生成契约命令:  
```
  mvn install -D -DskipTests
```
该命令会在本地 `maven` 仓库中生成 `*stub` 包,部分执行结果如下  
```
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ producer ---
[INFO] Installing E:\workspace\photon\test\cloud-contract\producer\target\producer-0.0.1-SNAPSHOT.jar to E:\workspace\repository\c\s\contract\producer\0.0.1-SNAPSHOT\producer-0.0.1-SNAPSHOT.jar
[INFO] Installing E:\workspace\photon\test\cloud-contract\producer\pom.xml to E:\workspace\repository\c\s\contract\producer\0.0.1-SNAPSHOT\producer-0.0.1-SNAPSHOT.pom
[INFO] Installing E:\workspace\photon\test\cloud-contract\producer\target\producer-0.0.1-SNAPSHOT-stubs.jar to E:\workspace\repository\c\s\contract\producer\0.0.1-SNAPSHOT\producer-0.0.1-SNAPSHOT-stubs.jar
```  

## consumer
契约的消费端  
将生成的 `stub` 包引入项目中  
`eclipse`
```
  右键项目
  build path -> configure build path...
  打开的窗口右边 -> Libraries -> add External JARs  
```
`IDEA`   
```
  file -> Project Structure (ctrl+alt+shift+s)
  打开的窗口左侧菜单 -> modules -> 中间部分选择项目
  右侧 -> 选项卡 dependeccies
  点击窗口最右边中间的小"+"号
```
在 `src/main/test` 中,运行测试类  

## producer-git
契约的生产者  
利用 `git` 方式远程存储和下载 `stub` 包  
完整例子参考: [pom.xml](producer-git/pom.xml)  
以及 [FeignGitTest.java](consumer/src/test/java/c/s/consumer/mock/FeignGitTest.java)

## 参考地址
#### `maven` 插件参考地址   
https://cloud.spring.io/spring-cloud-contract/spring-cloud-contract-maven-plugin/index.html  
#### `spring` 官方的简单介绍
https://cloud.spring.io/spring-cloud-contract/
#### 官方文档
http://cloud.spring.io/spring-cloud-contract/2.0.x/single/spring-cloud-contract.html
#### `spring gitHub demo`
https://github.com/spring-cloud-samples/spring-cloud-contract-samples
#### 官方文档相关 `DSL` 讲解
https://cloud.spring.io/spring-cloud-contract/multi/multi__contract_dsl.html
