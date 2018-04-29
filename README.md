# EasyRest-NAS

### EasyRest integration with Netty, Akka and Spring.

* ##### This is the high performance RESTful framework, designed for the fast development. Easy to cluster and distributed. You can focus on your business logic.

## Quick start:

* The rest definition

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

* The controller is the bean of spring, you can integrate with spring  

```java
@Controller
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

* The main class.

```java
public class Example {

    public static void main(String[] args) {
        EasyRest easyRest = new EasyRest("classpath:MyExampleApplicationContext.xml");
        easyRest.startup("EasyRestServer");
    }

}
```

* An empty spring config file

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

* <B>@BindURL("/rest/{TENANT}/stock")</B> will bind this endpoint at "/rest/{TENANT}/stock"

* <B>@AllDefined</B> will check all parameters not be null, if any parameter is null, the framework will reject the request directly. 

* <B>@Controller</B> is spring annotation, that will create bean by spring.

* <B>ResponseEntity</B> is the generic response entity, you can put any thing you want in it.

* If you have own spring properties, you can create EasyRest by

```java
EasyRest easyRest = new EasyRest("classpath:MyApplicationContext-01.xml", "classpath:MyApplicationContext-02.xml"...);
```

* All over the rest interface will be detected by EasyRest automatically, you just need start the server. 

```java
easyRest.startup("EasyRestServer");
```

***

### REST CALL EXAMPLE

* Methd 1

```java
@Post("/personal/{USER_ID}/favorite/{CODE}")
void addFavorite(String TENANT, String USER_ID, String CODE, long time);
```
Call it at:
> http://127.0.0.1:8080/rest/100000001/stock/personal/001/favorite/100001

Content-Type is 'application/json'

POST body is:

```json
{"time":1524827542}
```

The output is:

```java
100000001 001 100001 1524827542
```

And the response is:

```java
{
    "code": "1",
    "message": "ok"
}
```

***

* Methd 2

```java
@Post
@AllDefined
ResponseEntity addStocks(int userNumber, String userName, List<Stock> stockList);
```

Call it at:
> http://127.0.0.1:8080/rest/100000001/stock/addStocks

Content-Type is 'application/json'

POST body is:

```json
{"userNumber":1, "userName":"Louie", "stockList":[{"code":100001, "name":"stock1"}, {"code":100002, "name":"stock2"}]}
```

And the response is:

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

The method has annotation <B>@AllDefined</B>, so if any one of the parameter is missing, e.g. "userName".
The response will be:

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

* Methd 3

```java
@Get("/personal/{USER_ID}/favorite/list")
List<Stock> getStockList(String USER_ID);
```

Call it at:
> http://127.0.0.1:8080/rest/100000001/stock/personal/001/favorite/list

And the response is:

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

* For the content type, 'multipart/form-data' is also supported.

* Distributed is supported and very easily.

## Distribute service example

### All of the code are in the Example module.


	- Example-Distributed-Service-1
	
		- example-service-1-api
		
		- example-service-1-main
		

	- Example-Distributed-Service-2
	
		- example-service-2-api
		
		- example-service-2-main
	

	- Example-Distributed-Service-Model
	

> Example-Distributed-Service-1 will get request from the rest call, then will invoke Example-Distributed-Service-2 to create a People and response the rest call with this People. 

#### Example-Distributed-Service-Model

* People class

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

* Interface definition

```java
@BindURL("/service1")
public interface Service1 {

    @Post
    @AllDefined
    ResponseEntity createPeople(String name, int age, long birthday, List<String> skills, People boss);

}
```

##### example-service-1-main

* Interface Implement

```java
@Controller
public class Service1Impl implements Service1 {

    @Override
    public ResponseEntity createPeople(String name, int age, long birthday, List<String> skills, People boss) {
        Service2 service2 = EasyRestServiceLookup.lookup(Service2.class);
        return ResponseEntity.buildOkResponse(service2.getPeople(name, age, birthday, skills, boss));
    }

}
```

> <B>EasyRestServiceLookup</B> has a static method <B>lookup</B>. You can use this method to get any service instance, even this service on the other server!

* The main class

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

> EasyRestDistributedServiceBind.loadConfiguration(Startup.class.getClassLoader().getResourceAsStream("services-mapping-01.json")); will load the service mapping for the framework.

* akka config file: <B>application.conf</B>

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

> Akka system will detect this file, and open the port to listening remote request.

* Distributed service mapping file:(services-mapping-01.json)

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

> Service mapping file only need two fields:
> <B>Self</B> to record local system info.
> <B>Services</B> is an array to record all system info, including self.
>
> <B>The akkaSystemName must be the same with systemName in Main class.</B> 

* An empty spring config file:

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

* Interface definition

```java
@BindURL("/service2")
public interface Service2 {

    @Get
    People getPeople(String name, int age, long birthday, List<String> skills, People boss);

}

```

##### example-service-2-main

* Interface Implement

```java
@Controller
public class Service2Impl implements Service2 {

    @Override
    public People getPeople(String name, int age, long birthday, List<String> skills, People boss) {
        return new People(name, age, birthday, skills, boss);
    }
}
```

* The main class

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

* akka config file: <B>application.conf</B>

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

* Distributed service mapping file:(services-mapping-02.json)

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

* An empty spring config file:

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

#### start Service 1 and Service 2.

##### When you see this log on each console:

```java
[example-service-1-akka.actor.default-dispatcher-5] INFO com.easyrest.utils.LogUtils - From com.easyrest.actors.remote.RemoteServiceExchangeActor: Service mapping init success.
[example-service-1-akka.actor.default-dispatcher-5] INFO com.easyrest.utils.LogUtils - example-service-2 is running on the port 8001.
```

```java
[example-service-2-akka.actor.default-dispatcher-3] INFO com.easyrest.utils.LogUtils - From com.easyrest.actors.remote.RemoteServiceExchangeActor: Service mapping init success.
[example-service-2-akka.actor.default-dispatcher-3] INFO com.easyrest.utils.LogUtils - example-service-2 is running on the port 8002.
```

#### That means services are ready now!

##### Now, We will invoke the service1 via rest call.

> http://127.0.0.1:8001/service1/createPeople
> Content-Type:application/json
> Body:
> {"name":"Louie", "age":18, "birthday":763401600, "skills":["java", "netty", "akka", "spring"], "boss":{"name":"Louie_B", "age":18, "birthday":763401600}}

##### And then we can get response:

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
##### Not the end...