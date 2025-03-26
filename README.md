

Para crear usuario en rabbit y no tener problemas en el proyecto.
rabbitmqctl add_user new_user password
rabbitmqctl set_user_tags new_user administrator
rabbitmqctl set_permissions -p / new_user ".*" ".*" ".*"

