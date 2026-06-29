#!/bin/bash

echo "Iniciando Servidor de Descubrimiento Eureka (Puerto 8761)..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/eureka\" && ./mvnw spring-boot:run"'

echo "Esperando 12 segundos a que Eureka se estabilice..."
sleep 12

echo "Iniciando API Gateway..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/gateway\" && ./mvnw spring-boot:run"'

echo "Iniciando Microservicio Productos..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/servicio-productos\" && ./mvnw spring-boot:run"'

echo "Iniciando Microservicio Usuarios..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/servicio-usuarios\" && ./mvnw spring-boot:run"'

echo "Iniciando Microservicio Ventas..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/servicio-ventas\" && ./mvnw spring-boot:run"'

echo "Ecosistema lanzado. Dashboard disponible en http://localhost:8761"