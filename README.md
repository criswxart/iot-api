******************NO DESCARGAR - EN PROGRESO*****************
(feature-sebastian tiene el proyecto para ser usado en servidor aws con version sensorData antiguo)
En AWS server debes:

-Tener instalado postgres > crear BD iot-api
-Instalar Java 21
-Generar variables de entorno
-Instalar rabbitMQ > crear usuario admin con todos los permisos> crear sensor_cola, sensor_exchange, sensor_ruta.
-Habilitar puertos de entrada: 8080, 5672 (mensajeria rabbitmq), 443, 22, 5432(postgres), 16672 (panel de control de rabbit)
-Copiar jar a algun directorio y ejecutarlo a traves de algun script donde: el java corra en segundo plano y se genere un archivo log.



Variables de entorno:
export DB_HOST="localhost"
export DB_PORT="5432"
export DB_NAME="iot-api"
export DB_USER="postgres"
export DB_PASSWORD="postgres852"
export JWT_PRIVATE_KEY="fade5fd33efa966698314310e74c3bf9827a04b875d1e4f8a088b389c54ab7a1"
export JWT_USER_KEY="AUTH0JWT"
export RB_HOST="localhost"
export RB_PORT="5672"
export RB_USER="rabbit_sebastian"
export RB_PASSWORD="rabbit_159753"


Para crear usuario en rabbit y no tener problemas en el proyecto.
rabbitmqctl add_user new_user password
rabbitmqctl set_user_tags new_user administrator
rabbitmqctl set_permissions -p / new_user ".*" ".*" ".*"


ejemplo Script para linux:
JAR_PATH="/home/ubuntu/iot-api-AWS.jar"
LOG_FILE="/var/log/iot.log"
echo "Iniciando $JAR_PATH..."
nohup java -jar "$JAR_PATH" > "$LOG_FILE" 2>&1 &
echo "Proceso iniciado en segundo plano. Revisa $LOG_FILE para ver los logs."


