# spring-cloud-contract quick-tour
spring cloud contract quick tour

## 本地方式使用契约
- 在 `producer` 项目中生成契约  
执行生成契约命令  
```
  mvn install -D -DskipTests
```
该命令会在本地 `maven` 仓库中生成 `*stub` 包  
也可在项目中的 `target` 目录下找到 `*stub` 包  
部分执行结果如下  
```
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ producer ---
[INFO] Installing E:\workspace\photon\test\cloud-contract\producer\target\producer-0.0.1-SNAPSHOT.jar to E:\workspace\repository\c\s\contract\producer\0.0.1-SNAPSHOT\producer-0.0.1-SNAPSHOT.jar
[INFO] Installing E:\workspace\photon\test\cloud-contract\producer\pom.xml to E:\workspace\repository\c\s\contract\producer\0.0.1-SNAPSHOT\producer-0.0.1-SNAPSHOT.pom
[INFO] Installing E:\workspace\photon\test\cloud-contract\producer\target\producer-0.0.1-SNAPSHOT-stubs.jar to E:\workspace\repository\c\s\contract\producer\0.0.1-SNAPSHOT\producer-0.0.1-SNAPSHOT-stubs.jar
```  

- 在 `consumer` 项目中使用契约  
需要将生产者(`producer`项目)生成的 `stub` 包引入项目中  
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
在 `src/main/test` 目录下,运行测试类`c.s.consumer.mock.FeignTest`

## `git` 远程方式使用契约
由于上述操作比较复杂,可以使用 `git` 方式将契约存储在远程仓库中  

### `producer-git` 项目
- 修改配置  
  [pom.xml](producer-git/pom.xml)  
```
  三个标签
  <contractsRepositoryUrl/>          -- git仓库地址
  <contractsRepositoryUsername/>     -- git远程仓库用户名
  <contractsRepositoryPassword/>     -- git 远程仓库密码
```
- 生成契约  
```
  mvn install -D -DskipTests
```
- 推送契约至远程仓库  
  由于 `mvn install` 不会自动推送,所以需要手动推送
```
  mvn pushStubsToScm
```

### `consumer` 项目
- 修改配置  
  `application.yml`
```
  spring.cloud.contract.stubs.git.url
  spring.cloud.contract.stubs.git.username
  spring.cloud.contract.stubs.git.password
```
- 执行测试类  
[FeignGitTest.java](consumer/src/test/java/c/s/consumer/mock/FeignGitTest.java)
