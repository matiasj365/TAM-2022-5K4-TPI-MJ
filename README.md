# Tecnologías de Desarrollo de Aplicaciones Moviles - 2022 - 5k4
## Aplicación Android para Control de Alarma
* Alumno:  Jarab, Damián Matías;  44.157  
* Legajo Nº: 44.157
* Profesores:  
	- Szyrko, Pablo Andres  
	- Gonzalez, Claudio Javier
* Curso: 5k4  
## Alcances
### Interfaz de Usuario
   
   * Login: Debe mostrar el login al cloud o permitir omitir dicho login
      - Agregar dispositivo SMS
   * Listar dispositivos (Del cloud si se esta autenticado y los dispositivos SMS)
   * Mostrar panel de dispositivo seleccionado
        - Mostrar estado de dispositivo
        - Enviar comando a dispositivo (Activar, Desactivar, Activar en casa, Botón de Pánico)
        - Mostrar mensajes de dispositivo seleccionado: Listado con historial de eventos del dispositivo
        - Panel configuración: Permite elegir el método de comunicación con el dispositivo (SDK, API, SMS)
  ### Comunicación con el dispositivo. 
  Existen tres maneras de comunicarse con el dispositivo. Dos de ellas provistas por un servicio IOT donde esta registrada la alarma y la tercera via SMS.
  Se implementarán los tres servicios con las siguientes alcances para cada uno:
  * Implementación mensajes SDK ([GizWits SDK](http://docs.gizwits.com/en-us/AppDev/AppDevGetStarted.html)):
    - Login
    - Listar dispositivos
    - Información 1 dispositivo
    - Enviar comando a dispositivo
    - Escuchar por notificaciones
   
   Se utiliza el cloud IOT donde esta registrada la Alarma. El mismo cuenta con un SDK para el desarrollo de las interacciones:
    
   ![SDK_Overview](http://docs.gizwits.com/assets/en-us/AppDev/AppDevGetStarted/1.png)
    
    
  * Implementación mensajes API ([GizWits API](http://docs.gizwits.com/en-us/UserManual/UseOpenAPI.html)):
    - Login
    - Listar dispositivos
    - Información 1 dispositivo
    - Enviar comando a dispositivo
    
      ![API_Overview](http://docs.gizwits.com/assets/en-us/UserManual/OpenAPI/101.png)
      
  * Implementación mensajes SMS (No requiere autenticación previa):
    - Enviar comando a dispositivo
    - Escuchar por SMSs del dispositivo
### Widget
  * Set de 4 botones por dispositivo registrado en la app para Armar, Desarmar, Activar en casa y Botón de panico 


