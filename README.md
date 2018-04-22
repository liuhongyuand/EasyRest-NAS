# EasyRest-NAS

EasyRest with Netty, Akka and Spring.

This is RESTful framework for fast development.

Quick start:

* The rest definition
```java
@BindURL({"/people"})
@BindController(PeopleController.class)
public interface PeopleRestEndPoint {

    @Get
    ResponseEntity getAllName();

    @Get
    ResponseEntity getPeople(String name, int age);

    @Post
    ResponseEntity addPeople(People people);

    @Post
    ResponseEntity addBatch(List<People> peoples);

    @Post
    ResponseEntity addBatchWithDetails(List<People> peoples, List<String> name, List<Integer> age, long birth);
}
```

* The controller is the bean of spring, you can integrate with spring  
```java
@Controller
public class PeopleController implements PeopleRestEndPoint {

    @Override
    public ResponseEntity getPeople(String name, int age) {
        return ResponseEntity.buildOkResponse(new People(name, age));
    }

    @Override
    public ResponseEntity getAllName() {
        return ResponseEntity.buildCustomizeResponse(1, "ok", Lists.newArrayList("First", "Second"));
    }

    @Override
    public ResponseEntity addPeople(People people) {
        return ResponseEntity.buildOkResponse(people);
    }

    @Override
    public ResponseEntity addBatch(List<People> peoples) {
        return ResponseEntity.buildOkResponse(peoples);
    }

    @Override
    public ResponseEntity addBatchWithDetails(List<People> peoples, List<String> name, List<Integer> age, long birth) {
        return ResponseEntity.buildOkResponse(Lists.newArrayList(peoples, name, age, birth));
    }
}
```

* The main class.
```java
public class Example {

    public static void main(String[] args) {
        EasyRest easyRest = new EasyRest("classpath:applicationContext.xml");
        easyRest.registerServiceAndStartup(PeopleRestEndPoint.class);
    }

}
```


