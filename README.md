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
将生成的 `stub` 包引入项目中,`eclipse` 利用 `build path`  
在 `src/main/test` 中,执行运行测试类  

## producer-git
契约的生产者  
利用 `git` 方式远程存储和下载 `stub` 包  
**未试验成功,待验证**
