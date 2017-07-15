# hybo-db

After you checkout and import project in eclipse/sts

Go to file <project-root>src\main\resources\META-INF\spring\app-context.xml

search for following line

property name="url" value="jdbc:h2:D:\CodeConcepts\hackovation2017\code\gitSaurabh\hybo-db\hybodb\db\test/hybo" 

In value give path where you have this directory, this directory contains h2 db file which db with data.


## To see data in web console 

Goto your maven repo at path .m2\repository\com\h2database\h2\1.3.156
double click the jar h2-1.3.156.jar

For JDBC URL put your path jdbc:h2:tcp://localhost/\<put the same path that you gave above\>;AUTO_SERVER=true
