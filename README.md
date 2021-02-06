# room-occupancy-manager

Room Occupancy manager it's an Optimization Tool which is a Spring Boot based Restful Mircoservice design to optimize the process of selecting a room based on the available price offered by hotel customers.

#Software required to run service #
Java 11

maven 3

Refer below for Running Service

To run this service one can either clone or download the source code, open it with editor and perform unit tests.

or one can run using maven command

mvn clean test

To create Jar file open command prompt and run the maven command from the root directory

mvn clean install or mvn clean package

This will create the jar in target directory room-occupancy-manager-0.0.1-SNAPSHOT.jar

To run service as a jar, Change directory to the location of the room-occupancy-manager-0.0.1-SNAPSHOT.jar , the jar should have a name like this room-occupancy-manager-<version>-SNAPSHOT.jar if downloaded from the target folder in this repository.

From your command-line run java -jar room-occupancy-manager-0.0.1-SNAPSHOT.jar 

change jar version accordingly

This will start the service on the port 8084. This is build with the swagger to all the rest endpoint documentation will be available on the following URL

http://localhost:8084/swagger-ui/index.html

Also this used the open docs to rest API documentation is available on following URL. Once can use this api documentation as a service contract

http://localhost:8084/api-docs


Use this Json format for a simple test

{
"availablePremiumRooms": 3,
"availableEconomyRooms": 3
}


For Bulk request use following format 

[ { "availablePremiumRooms":3, "availableEconomyRooms":3 }, { "availablePremiumRooms":7, "availableEconomyRooms":5 }, { "availablePremiumRooms":2, "availableEconomyRooms":7 }, { "availablePremiumRooms":7, "availableEconomyRooms":1 } ]
