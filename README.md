# gameOfLife

## Launch

In order to launch the application do the folowing :

```bash
cd /gameOfLife
```

then do 

```bash
mvn package
```

finaly do 

```bash
java -jar GameOfLife-0.0.1-SNAPSHOT
```
## Programm Arguments

### 1) First rule :
alternatively you can launch the application with the following arguments :

```bash
java -jar GameOfLife-0.0.1-SNAPSHOT 5 5 15.5 1 true
```

respectively the argument are :

[gridRowSize gridColumnSize initialLivingCellPercentage refreshRate tcpServerMode]

### 2) Second rule :
**if no arguments are passed the values will be taken from the**
```bash
src/main/resources/application.properties file.
```
### 3) Third rule :
**if there is a existings save files the user will be promt to accept if he want or not load those files.**

**if he input 'Y' then push ENTER key => the data will be loade and thus the value mention earlier will be taken from the 'simulation_parameters_data.json file'**

**if he input 'N' or any other values then push ENTER key => then the earlier rules applies.**

_When lauch in tcp server mode the server will listen on port 4444._