## Prerequisites
1. Java17 is installed 
2. Docker is installed

## Running the application locally
To run the application:

1. Clone the repo into a folder, lets call the folder `$PROJECT`
2. `cd` to the `$PROJECT` folder
3. run `./gradlew runDocker` from the `$PROJECT` folder, this will build docker image and run new docker container 
based on the image with application running
4. Use collection from `$PROJECT/postman` to interact with the application