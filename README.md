# recipe_app
Recipe application for creating ,updating,deleting and listing your favorite cooks.

The system has dockerize option to start it.

You should follow the below steps to configure it.

1) You should enter the /backend folder and execute the below commands.

	a) mvn clean install
	b) docker build -t backend .

2) You should enter the /frontend folder and execute the below command.

        a) docker build -t frontend.


3) After all, you should go back to the parent folder /recipe_app project for composing docker.

	a) docker-compose up.


You will be able to see the application is started on : 

http://localhost:3000/

———————————————————————————————————————————————————————————

The system has defaulted a user to log in to the recipe application.
You can use this user to log in to our system.

username: demo
password: 12345


