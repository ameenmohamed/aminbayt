from local 
mvn -Djasypt.encryptor.password=<> -Dspring.profiles.active=local,tvroom spring-boot:run

sudo nohup java -Xmx512m -Djasypt.encryptor.password=<> -Dspring.profiles.active=local,tvroom -DHOME=/home/pi/amdata/logs -jar ameenbayt.jar > >/home/pi/amdata/logs/ameenbayt.log &