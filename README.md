# EasyRest-NAS

EasyRest with Netty, Akka and Spring.

This is RESTful framework for fast development. Easy to cluster and distributed. Focus on your business logic.

Quick start:

* The rest definition
```java
@BindURL("/rest/{TENANT}/stock")
@BindController(StockInfoRestController.class)
public interface StockInfoRest {

    @Post("/personal/{USER_ID}/favorite/{CODE}")
    void addFavorite(String USER_ID, String CODE, long time);

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
    public void addFavorite(String USER_ID, String CODE, long time) {
        System.out.println(USER_ID + " " + CODE + " " + time);
    }

    @Override
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
        easyRest.registerServiceAndStartup("EasyRestServer", StockInfoRest.class);
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

####
* @BindURL("/rest/{TENANT}/stock") will bind this endpoint at "/rest/{TENANT}/stock"

* @BindController(StockInfoRestController.class) to tell the framework which controller should use.

* @Controller is spring annotation, that will create bean by spring.

* 'ResponseEntity' is the generic response entity, you can put any thing you want in it.

* If you have own spring properties, you can create EasyRest by
```java
EasyRest easyRest = new EasyRest("classpath:MyApplicationContext-01.xml", "classpath:MyApplicationContext-02.xml"...);
```
* Register your interface to the EasyRest 
```java

```
