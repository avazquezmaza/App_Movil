
<java.version>11</java.version>

Ejecutar
mvn clean install spring-boot:run

Test

curl --location --request POST 'http://localhost:8080/app/plan/purchased/phone/5534718454' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data '{
    "data": "algo"
}'


curl --location --request POST 'http://localhost:8080/app/plan/purchased/phone/:phoneNumber' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data '{
    "data": "algo"
}'