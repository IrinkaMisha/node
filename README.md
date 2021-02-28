# NODE

# rus
Эта библиотека позволяет наделить любые объекты следующей функциональностью:

- контролировать их с помощью истории
- смотреть кто вносил изменения в объект
- организовывать доступ к объектам на основе прав доступа
- любая другая функциональность, которая может потребоваться для разных объектов (может быть расширена здесь)

# eng
This library allows giving any objects next functions:

- control them by history
- trace who changed the object
- give permission to object by permission rules
- any other functionality that will be needed for different objects (can be expanded here)

This library use for help to store and manage different type objects as one entity

For include dependency use 

#1, add repository to pom.xml

 <repositories>       
        <repository>
            <id>node-on-github</id>
            <url>https://raw.github.com/IrinkaMisha/node/master/</url>
        </repository>
 </repositories>
 
 #2, add dependency to pom.xml
 
  <dependency>
            <groupId>by.imix.cms</groupId>
            <artifactId>node</artifactId>
            <version>1.0.0</version>
  </dependency>
