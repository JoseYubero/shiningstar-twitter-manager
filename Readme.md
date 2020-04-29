# Shiningstar Twitter Manager

Este proyecto es un **microservicio** hecho con _**Spring-Boot**_. El microservicio consume **Tweets** y en base a unos criterios los persiste en base de datos.
Estos datos podrán ser gestionados a través de un API REST.

## Repositorio del código

El código del proyecto está disponible en GitHup en el enlace:

- https://github.com/JoseYubero/shiningstar-twitter-manager

Una vez que clonemos la rama Master en nuestro equipo, podremos editarlo con nuestro IDE favorito (Eclipse p.e.).

Mira **Deployment** para conocer como desplegar el proyecto.


### Pre-requisitos

Para poder editar y ejecutar el microservicio es necesario tener el siguiente software instalado:

- java 8
- maven
- cliente Git
- Eclipse o Intellij (se recomienda última versión)
- Postmam

### Instalación

Abriremos el IDE y le indicaremos la ruta en la que se encuentra el proyecto.
Una vez que esté configurado el proyecto en el IDE (Eclipse o Intellij) como proyecto maven, java y Spring-Boot,
asignaremos los valores a las propiedades de Twitter4j en el fichero _**application.yml**_:

```
twitter4j:
  oauth:
    consumerKey: XXXXXXXXXX
    consumerSecret: XXXXXXXXXX
    accessToken: XXXXXXXXXX
    accessTokenSecret: XXXXXXXXXX
```

**NOTA: Los valores se consiguen en el espacio de desarrollo de Twitter asociado a nuestro usuario Twitter, para la aplicación que hemos generado)**

Ejecutaremos el proyecto como Spring-Boot y siendo la clase de inicio: 

```
com.shiningstar.twitter.application.ShiningstarTwitterManagerApplication
```

En un terminal del sistema, nos situamos en el directorio del proyecto y ejecutaremos el comando:

```
mvn clean install
```

Si todo ha ido correcto construirá el fichero `jar` que podremos lanzar desde la línea de comandos.
Además de compilar y ejecutar todas los test del proyecto:

```
[INFO] --- spring-boot-maven-plugin:2.2.6.RELEASE:repackage (repackage) @ shiningstar-twitter-manager ---
[INFO] Replacing main artifact with repackaged archive
[INFO]
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ shiningstar-twitter-manager ---
[INFO] Installing C:\REPOSITORIO\shiningstar-twitter-manager\target\shiningstar-twitter-manager-0.0.1-SNAPSHOT.jar to C:\Users\xxx\.m2\repos
itory\com\shiningstar\twitter\shiningstar-twitter-manager\0.0.1-SNAPSHOT\shiningstar-twitter-manager-0.0.1-SNAPSHOT.jar
[INFO] Installing C:\REPOSITORIO\shiningstar-twitter-manager\pom.xml to C:\Users\Usuario\.m2\repository\com\shiningstar\twitter\shiningstar-twit
ter-manager\0.0.1-SNAPSHOT\shiningstar-twitter-manager-0.0.1-SNAPSHOT.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 45.821 s
[INFO] Finished at: 2020-04-28T16:40:11+02:00
[INFO] Final Memory: 41M/224M
[INFO] ------------------------------------------------------------------------

``` 

## Pruebas

Para poder ejecutar pruebas lo haremos con el programa Postman, que puede lanzar peticiones al microservicio.
En el proyecto está el fichero `postman/shiningstar.postman_collection.json` que al ser importado por el programa Postman,
generará las peticiones de prueba para poder probar el microservicio.

Las peticiones son:
* **Shiningstar Load Tweet local** : Hace una carga de un tweet en BBDD
* **Shiningstar Load Tweet 2 local** : Hace una carga de otro tweet en BBDD
* **Shiningstar searchTweet local** : Hace una búsqueda en BBDD de tweets por _text_, _userName_, _placeName_ y _validated_
* **Shiningstar searchUserTweet local** : Hace una búsqueda en BBDD de tweets por _userName_
* **Shiningstar searchMoreUsedHashtags local** : Hace una búsqueda de los **_hashtags_** más utilizados de todos los tweets guardados en BBDD
* **Shiningstar validateTweet local** : Valida un tweet, cambiando a _true_ el campo _validated_ en BBDD

Para acceder a la definición de los End-Points del microservicio mediante Swagger la URL es:

```
http://localhost:8100/swagger-ui.html
```

Los End-Points con un formato **_/web/_** harán peticiones directamente al API de Twitter.

* Para cargar los tweets mediante Stream API de Twitter se puede hacer a demanda mediante la petición:

```
http://localhost:8100/web/load
```

**NOTA: El método de Stream API de Twitter tiene un temporizador (_@Scheduled_) para lanzar el _listener_ y cargar los tweets)**

* Para buscar los tweets por el texto:

```
http://localhost:8100/api/web/search/{tweetText}
```

* Para buscar los hashtags más usados por el usuario:

```
http://localhost:8100/api/web/search/hashtagMoreUsed
```


**(NOTA: No he podido acceder a la aplicación de desarrollo que proporciona Twitter, por lo que no funciona el Stream API, ni las peticiones directas a Twitter)**



Para poder acceder a la gestión de la BBDD podremos hacerlo a través de la URL:

```
http://localhost:8100/h2-console
```

con usuario/contraseña : **sa/sa**



## Construido con

_Menciona las herramientas que utilizaste para crear tu proyecto_

* [Spring-framework](https://spring.io/projects/spring-framework) - Framework java de desarrollo
* [Spring-Boot](https://spring.io/projects/spring-boot) - Herramienta para simplificar el desarrollo con Spring
* [Twitter4j](http://twitter4j.org/en/index.html) - Librería no oficial para el API de Twitter.
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Git](https://git-scm.com/) - Software de control de versiones.

