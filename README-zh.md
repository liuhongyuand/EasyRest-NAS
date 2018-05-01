# EasyRest-NAS

English doc:
> https://github.com/liuhongyuand/EasyRest-NAS/blob/master/README.md

### EasyRest 与 Netty, Akka 和 Spring 的整合.

* ##### 这是一个为快速开发而设计的高性能RESTful框架，极易搭建集群和使用分布式。你可以完全专注在你的业务逻辑上。

* ##### 不需要 Tomcat，不需要 web.xml 配置，只需一个有 main 函数的 jar 包，你就能拥有一个完美的分布式系统。

* ##### 你可以不知道 Netty，也可以不知道 akka，甚至不熟悉 Spring，但仍然可以使用该框架。 

## 快速开始:

* REST接口定义

```java
@BindURL("/rest/{TENANT}/stock")
public interface StockInfoRest {

    @Post("/personal/{USER_ID}/favorite/{CODE}")
    void addFavorite(String TENANT, String USER_ID, String CODE, long time);

    @Post
    ResponseEntity addStocks(int userNumber, String userName, List<Stock> stockList);

    @Get("/personal/{USER_ID}/favorite/list")
    List<Stock> getStockList(String USER_ID);

}
```

* 使用@Service注解，将该类交给Spring生成bean并管理，该框架可以和spring无缝对接使用。  

```java
@Service
public class StockInfoRestController implements StockInfoRest {

    @Override
    public void addFavorite(String TENANT, String USER_ID, String CODE, long time) {
        System.out.println(TENANT + " " + USER_ID + " " + CODE + " " + time);
    }

    @Override
	@AllDefined
    public ResponseEntity addStocks(int userNumber, String userName, List<Stock> stockList) {
        return ResponseEntity.buildOkResponse(Lists.asList(userNumber, userName, new List[]{stockList}));
    }

    @Override
    public List<Stock> getStockList(String USER_ID) {
        return Lists.newArrayList(new Stock(100000, "stock1"), new Stock(100001, "stock2"), new Stock(100002, "stock3"));
    }
}
```

* 主函数类，用于启动以及配置。

```java
public class Example {

    public static void main(String[] args) {
        EasyRest easyRest = new EasyRest("classpath:MyExampleApplicationContext.xml");
        easyRest.startup("EasyRestServer");
    }

}
```

* 一个基础的spring配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <context:annotation-config/>
    <context:component-scan base-package="com.example">
    </context:component-scan>
    <context:annotation-config/>
</beans>
```

***

* <B>@BindURL("/rest/{TENANT}/stock")</B> 会绑定该类监听 "/rest/{TENANT}/stock" 路径的请求。

* <B>@AllDefined</B> 会要求该方法所有的参数在请求中都被赋予非空值，如果检测到有null，框架将会直接拒绝这个请求。可以减少用户的空值判断。

* <B>@Service</B> 这个是spring的annotation，将交给spring生成bean并管理。

* <B>ResponseEntity</B> 是一个通用的返回格式，你能将所有格式的数据放进去。（你也可以不使用这个，直接返回任何你想返回的格式）

* 如果你有很多其他的spring配置文件，你可以这样启动EasyRest：

```java
EasyRest easyRest = new EasyRest("classpath:MyApplicationContext-01.xml", "classpath:MyApplicationContext-02.xml"...);
```

* 所有你想暴露的数据接口，EasyRest都会自动检测到，你只需要启动server。

```java
easyRest.startup("EasyRestServer");
```

***

### 接口调用示例

* 函数 1

```java
@Post("/personal/{USER_ID}/favorite/{CODE}")
void addFavorite(String TENANT, String USER_ID, String CODE, long time);
```
调用地址:
> http://127.0.0.1:8080/rest/100000001/stock/personal/001/favorite/100001

Content-Type is 'application/json'

请求内容:

```json
{"time":1524827542}
```

控制台输出:

```java
100000001 001 100001 1524827542
```

收到的响应内容:

```java
{
    "code": "1",
    "message": "ok"
}
```

***

* 函数 2

```java
@Post
@AllDefined
ResponseEntity addStocks(int userNumber, String userName, List<Stock> stockList);
```

调用地址:
> http://127.0.0.1:8080/rest/100000001/stock/addStocks

Content-Type is 'application/json'

请求内容:

```json
{"userNumber":1, "userName":"Louie", "stockList":[{"code":100001, "name":"stock1"}, {"code":100002, "name":"stock2"}]}
```

响应内容:

```java
{
    "code": "1",
    "data": [
        1,
        "Louie",
        [
            {
                "code": 100001,
                "name": "stock1"
            },
            {
                "code": 100002,
                "name": "stock2"
            }
        ]
    ]
}
```

这个函数有一个 <B>@AllDefined</B> 的注解，所以如果任何参数的值为null，比如：“UserName”，那么响应结果将会如下：

```java
{
    "code": "-1",
    "message": "Failed",
    "data": {
        "errorType": "ParameterNotFoundException",
        "errorMessage": "userName is not defined."
    }
}
``` 

***

* 函数 3

```java
@Get("/personal/{USER_ID}/favorite/list")
List<Stock> getStockList(String USER_ID);
```

调用地址:
> http://127.0.0.1:8080/rest/100000001/stock/personal/001/favorite/list

响应内容:

```java
[
    {
        "code": 100000,
        "name": "stock1"
    },
    {
        "code": 100001,
        "name": "stock2"
    },
    {
        "code": 100002,
        "name": "stock3"
    }
]
```

***

* 对于 content type, 'multipart/form-data' 也是支持的.

* 框架支持分布式服务，并且十分容易搭建.

## 分布式服务示例

### 所有的代码都在 Example 的模块中 

#### 代码结构

	- Example-Distributed-Service-1
	
		- example-service-1-api
		
		- example-service-1-main
		

	- Example-Distributed-Service-2
	
		- example-service-2-api
		
		- example-service-2-main
	

	- Example-Distributed-Service-Model
	
> Example-Distributed-Service-1 会收到请求，然后会调用 Example-Distributed-Service-2 的服务去创建一个 People，然后将这个 People 做为响应数据返回出去。

#### Example-Distributed-Service-Model

* People 类

```java
public class People {

    private String name;
    private int age;
    private long birthday;
    private List<String> skills;
    private People boss;

    public People(String name, int age, long birthday, List<String> skills, People boss) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.skills = skills;
        this.boss = boss;
    }

}
```

#### Example-Distributed-Service-1
##### example-service-1-api

* 接口定义

```java
@BindURL("/service1")
public interface Service1 {

    @Post
    @AllDefined
    ResponseEntity createPeople(String name, int age, long birthday, List<String> skills, People boss);

}
```

##### example-service-1-main

* 接口实现

```java
@Service
public class Service1Impl implements Service1 {

    @Override
    public ResponseEntity createPeople(String name, int age, long birthday, List<String> skills, People boss) {
        Service2 service2 = EasyRestServiceLookup.lookup(Service2.class);
        return ResponseEntity.buildOkResponse(service2.getPeople(name, age, birthday, skills, boss));
    }

}
```

> <B>EasyRestServiceLookup</B> 有一个静态方法 <B>lookup</B>. 你能使用这个函数获得任何交给 EasyRest，或者spring 的bean实例，包括在其他服务器上的实例，你都能直接调用。

* 主函数

```java
public class Startup {

	private static String systemName = "example-service-1";

    public static void main(String[] args) throws IOException {
        EasyRestDistributedServiceBind.loadConfiguration(Startup.class.getClassLoader().getResourceAsStream("services-mapping-01.json"));
        EasyRest easyRest = new EasyRest("classpath:MyExampleApplicationContext-01.xml");
        easyRest.startup(systemName, new NettyInit(8001));
    }

}
```

> EasyRestDistributedServiceBind.loadConfiguration(Startup.class.getClassLoader().getResourceAsStream("services-mapping-01.json")); 将会为框架载入服务映射的关系配置文件。

* akka 配置文件: <B>application.conf</B>

```java
akka {
  	actor {
    	provider = "akka.remote.RemoteActorRefProvider"
  	}
    remote {
        transport = "akka.remote.netty.NettyRemoteTransport"
        netty {
            tcp {
                hostname = "127.0.0.1"
                port = 2551
            }
        }
    }
}
```

> Akka 系统会检测到这个配置文件，然后在指定的端口监听远程请求。

* 分布式服务映射关系表:(services-mapping-01.json)

```json
{
  "self": {
    "akkaSystemName": "example-service-1",
    "host": "127.0.0.1",
    "port": "2551"
  },
  "services" : [
    {
      "akkaSystemName": "example-service-1",
      "host": "127.0.0.1",
      "port": "2551"
    },
    {
      "akkaSystemName": "example-service-2",
      "host": "127.0.0.1",
      "port": "2552"
    }
  ]
}
```

> 服务映射关系表只需要2个字段:
> <B>Self</B> 记录本地的服务器信息.
> <B>Services</B> 是一个数组，记录所有的服务器信息，包括自己本身.
>
> <B>字段 akkaSystemName 的值必须和主函数中 systemName 的值一致！！！</B> 

*一个基本的spring配置文件:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <context:annotation-config/>
    <context:component-scan base-package="com.example">
    </context:component-scan>
    <context:annotation-config/>
</beans>
```

#### Example-Distributed-Service-2
##### example-service-2-api

* 接口定义

```java
@BindURL("/service2")
public interface Service2 {

    @Get
    People getPeople(String name, int age, long birthday, List<String> skills, People boss);

}

```

##### example-service-2-main

* 接口实现

```java
@Service
public class Service2Impl implements Service2 {

    @Override
    public People getPeople(String name, int age, long birthday, List<String> skills, People boss) {
        return new People(name, age, birthday, skills, boss);
    }
}
```

* 主函数

```java
public class Startup {

	private static String systemName = "example-service-2";

    public static void main(String[] args) throws IOException {
        EasyRestDistributedServiceBind.loadConfiguration(Startup.class.getClassLoader().getResourceAsStream("services-mapping-02.json"));
        EasyRest easyRest = new EasyRest("classpath:MyExampleApplicationContext-02.xml");
        easyRest.startup(systemName, new NettyInit(8002));
    }

}
```

* akka 配置文件: <B>application.conf</B>

```java
akka {
  	actor {
    	provider = "akka.remote.RemoteActorRefProvider"
  	}
    remote {
        transport = "akka.remote.netty.NettyRemoteTransport"
        netty {
            tcp {
                hostname = "127.0.0.1"
                port = 2552
            }
        }
    }
}
```

* 分布式服务映射表:(services-mapping-02.json)

```json
{
  "self": {
    "akkaSystemName": "example-service-2",
    "host": "127.0.0.1",
    "port": "2552"
  },
  "services" : [
    {
      "akkaSystemName": "example-service-1",
      "host": "127.0.0.1",
      "port": "2551"
    },
    {
      "akkaSystemName": "example-service-2",
      "host": "127.0.0.1",
      "port": "2552"
    }
  ]
}
```

* 一个基本的spring配置文件:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <context:annotation-config/>
    <context:component-scan base-package="com.example">
    </context:component-scan>
    <context:annotation-config/>
</beans>
```
***

#### 启动 Service 1 和 Service 2.

##### 当你在两边的控制台分别看到如下的日志:

```java
[example-service-1-akka.actor.default-dispatcher-5] INFO com.easyrest.utils.LogUtils - From com.easyrest.actors.remote.RemoteServiceExchangeActor: Service mapping init success.
[example-service-1-akka.actor.default-dispatcher-5] INFO com.easyrest.utils.LogUtils - example-service-2 is running on the port 8001.
```

```java
[example-service-2-akka.actor.default-dispatcher-3] INFO com.easyrest.utils.LogUtils - From com.easyrest.actors.remote.RemoteServiceExchangeActor: Service mapping init success.
[example-service-2-akka.actor.default-dispatcher-3] INFO com.easyrest.utils.LogUtils - example-service-2 is running on the port 8002.
```

#### 这表示两个service现在已经就绪了!

##### 现在我们将通过rest call调用service1.

> http://127.0.0.1:8001/service1/createPeople
> Content-Type:application/json
> Body:
> {"name":"Louie", "age":18, "birthday":763401600, "skills":["java", "netty", "akka", "spring"], "boss":{"name":"Louie_B", "age":18, "birthday":763401600}}

##### 收到的响应内容:

```java
{
    "code": "1",
    "data": {
        "name": "Louie",
        "age": 18,
        "birthday": 763401600,
        "skills": [
            "java",
            "netty",
            "akka",
            "spring"
        ],
        "boss": {
            "name": "Louie_B",
            "age": 18,
            "birthday": 763401600
        }
    }
}
```

### That's work!

***
##### 持续跟新...
