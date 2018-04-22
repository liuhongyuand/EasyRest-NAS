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
    public ResponseEntity getAllName() {
        return ResponseEntity.buildCustomizeResponse(1, "ok", Lists.newArrayList("First", "Second"));
    }
    
    @Override
    public ResponseEntity getPeople(String name, int age) {
        return ResponseEntity.buildOkResponse(new People(name, age));
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
        EasyRest easyRest = new EasyRest();
        easyRest.registerServiceAndStartup(PeopleRestEndPoint.class);
    }

}
```

####
* @BindURL({"/people"}) will bind this endpoint at "/people"

* @BindController(PeopleController.class) to tell the framwork which controller should use.

* @Controller is spring annotation, that will create bean by spring.

* 'ResponseEntity' is the generic response entity, you can put any thing you want in it.

* If you have own spring properties, you can create EasyRest by
> EasyRest easyRest = new EasyRest("classpath:applicationContext.xml", "classpath:applicationContext-01.xml"...);

* Register your method to the EasyRest 
> easyRest.registerServiceAndStartup(PeopleRestEndPoint.class...);

For the methd 
```java
@Get
ResponseEntity getAllName();
```
You can call it by 
```java
GET http://hostname:port/people/getAllName
```
and the response is:
```java
{
    "code": "1",
    "message": "ok",
    "data": [
        "First",
        "Second"
    ]
}
```


