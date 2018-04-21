# EasyRest-NAS

EasyRest with Netty, Akka and Spring.

Quick restful framework.

Quick start:

* The main class.
```java
public class Example {

    public static void main(String[] args) {
        EasyRest easyRest = new EasyRest();
        easyRest.registerServiceAndStartup(HomeRestModel.class);
    }

}
```

* The rest definition
```java
@BindURL({"/rest"})
public interface HomeRestModel {

    @AllDefined
    @Post(url = "/addHomeList", controller = HomeController.class)
    ResponseEntity addHomeList(List<String> list, @Optional String name);

    @Get(url = "/getHome", controller = HomeController.class)
    ResponseEntity getHomeList(@Optional String name);

}
```

* The controller is the bean of spring, you can integrate with spring  
```java
@Controller
public class HomeController implements HomeRestModel{

    @Override
    public ResponseEntity addHomeList(List<String> list, String name) {
        return ResponseEntity.buildOkResponse("It Works! addHomeList");
    }

    @Override
    public ResponseEntity getHomeList(String name) {
        return ResponseEntity.buildOkResponse("It Works! getHomeList");
    }

}
```

That's ok.

