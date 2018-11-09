# spring cloud contract 知识点

## 生成测试
生成测试类的命令
```
  generate-test-sources
```
生成测试类需要一个基类,通常以 `*Base` 命名  
如果没有该基类.则生成的测试类会报错  

生成基类的方式有三种
根据生产者端 `maven` 插件的配置不同生成方式不同  
1) `baseClassForTests`
```
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
所有根据 `yml` 文件生成的测试类都会继承该类  
如果该类只指定了一个 `controller`  
则除了对应的 `controller` 测试会通过外,其余都会报错  
可以通过 `exclude` 排除不想生成测试类的 `yml` 文件  
当然也可以在基类中注入 `webApplicationContext` ,运行时激活所有 `controller`  
基类写法参考 `producer` 项目

2) `packageWithBaseClasses`
```
  ...
  <configuration>
    <packageWithBaseClasses>c.s.producer</packageWithBaseClasses>
  </configuration>
  ...
```
注意,这个包名 `c.s.producer` 必须在 `src/test/java` 源码目录下  
所有生成的测试类的基类都必须写在这个包下面  
必须手动声明每个基类  
每个基类的命名规则:
```
  假设契约写在 src/test/resources/contracts/skirt/rest/*.yml
  spring 会去寻找 最后两个文件夹名称+Base 为名称 匹配的基类 即基类名为: skirtRestBase
```
所有生成的测试类都会继承与之相匹配的基类

3) `baseClassMappings`
```
  ...
    <baseClassMapping>
      <contractPackageRegex>.*producer.*</contractPackageRegex>
      <baseClassFQN>c.s.producer.ProducerBase</baseClassFQN>
    </baseClassMapping>
    ...
```
复写 `spring` 的基类匹配规则   
`contractPackageRegex`: 一个正则表达式用于匹配包名  
`baseClassFQN`: 正则表达式对应匹配的基类名称,类的全限定名  
例如:
```
  假设契约写在 .*producer.* 的文件夹下面
  匹配正则相对应的包名下面的 基类 ProducerBase
```
生成的测试类不会继承基类  


## 关于 pushStubsToScm
配置
```
<configuration>
      ...
        <contractsMode>REMOTE</contractsMode>
        <contractsRepositoryUsername>git-username</contractsRepositoryUsername>
        <contractsRepositoryPassword>git-password</contractsRepositoryPassword>
        <contractsRepositoryUrl>git://https://git-remote-address.git</contractsRepositoryUrl>
        <contractDependency>
          <groupId>${project.groupId}</groupId>
          <artifactId>${project.artifactId}</artifactId>
          <version>${project.version}</version>
        </contractDependency>
        <executions>
          <execution>
            <phase>package</phase>
            <goals>
              <goal>pushStubsToScm</goal>
            </goals>
          </execution>
        </executions>
      </configuration>
```
注意点:  
1) `contractsRepositoryUrl` 默认有个前缀 `git://` ,无论是 `http/https` 或 `ssh` 地址,该前缀都 **必须**  
2) 默认 `pushStubsToScm` 在执行过程中不会执行,需要手动执行  
3) `pushStubsToScm` 完整执行命令:  
   **一定** 要执行完整命令,如果只是执行 `mvn pushStubsToScm`,报错  
```
  # 注意使用版本,此处是 2.0.1.RELEASE
  org.springframework.cloud:spring-cloud-contract-maven-plugin:2.0.1.RELEASE:pushStubsToScm
```
4) `pushStubsToScm` 默认会先从远程 download,如果远程是初始化仓库--报错  
    先要把上述配置注掉,然后执行 :
    ```
      org.springframework.cloud:spring-cloud-contract-maven-plugin:2.0.1.RELEASE:convert
    ```
    会在 `target` 下生成 `stubs`,然后打开注释,执行 `pushStubsToScm`,才能上传远程仓库  
5) `pushStubsToScm` 默认会上传 `target/stubs` 内的东西  
6) 远程消费者默认使用与 `contracts` 目录同级的 `mappings` 目录内的文件  
    如果远程只写了 `contracts` 目录中的契约,需要在生产者把契约 `mvn install` 下  
    然后再 `pushStubsToScm`,保证仓库有对应的 `mappings` 目录,才能供远程消费者使用

## `REMOTE` 模式 消费者端用到的属性
插件属性:

| property name | type    | default | description|
|---------------|---------|---------|------------|
| git.branch    | plugin  | master  | git 上的分支|
| git.username  | plugin  |   | 连接 git 的 用户名称|
| git.password  | plugin  |   | 连接 git 的密码 |
| git.no-of-attempts        | plugin | 10 | 每次 Push 到远程的 commit 数量|
| git.wait-between-attempts | plugin | 1000 | 每次 push 之间的等待时间|

系统属性:

| property name | type    | default  | description|
|---------------|---------|----------|------------|
| stubrunner.properties.git.branch   | system | master  | git 上的分支|
| stubrunner.properties.git.username | system |   | 连接 git 的 用户名称|
| stubrunner.properties.git.password | system |   | 连接 git 的密码 |
| stubrunner.properties.git.no-of-attempts | system | 10  | 每次 Push 到远程的 commit 数量|
| stubrunner.properties.git.wait-between-attempts | system | 1000 | 每次 push 之间的等待时间|

环境变量:

| property name | type    | default  | description|
|---------------|---------|----------|------------|
| STUBRUNNER_PROPERTIES_GIT_BRANCH   | env | master  | git 上的分支|
| STUBRUNNER_PROPERTIES_GIT_USERNAME | env |   | 连接 git 的 用户名称|
| STUBRUNNER_PROPERTIES_GIT_PASSWORD | env |   | 连接 git 的密码 |
| STUBRUNNER_PROPERTIES_GIT_NO_OF_ATTEMPTS | env | 10  | 每次 Push 到远程的 commit 数量|
| STUBRUNNER_PROPERTIES_GIT_WAIT_BETWEEN_ATTEMPTS | env | 1000  | 每次 push 之间的等待时间|

## 关于 `maven` 仓库的一些配置
下列属性可通过系统属性或环境变量设置:
```
  # 值为 maven 本地仓库自定义位置--路径(path)
  maven.repo.local

  # maven 用户设置的 setting.xml 路径 -- user-setting
  org.apache.maven.user-settings  

  # maven 全局设置的 setting.xml 路径 --global setting
  org.apache.maven.global-settings
```
系统属性可通过 `application.yml` 文件设置(不一定起效)或 `mvn` 命令运行时通过 `-D` 参数指定  
以上参数由官方提供,但未作测试
