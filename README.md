# Hello from foobar and Hello from {pod name}

Project to implement a task to test Docker, Jenkinsfile, Java, Kubernetes, Gradle

## Prerequisites
* JDK 8+
* Gradle
* Docker

## Getting started
Get project from GitHub:

```
git clone https://github.com/hivp/hellofrom.git
```

Go to the project folder

```
cd hellofrom
```

## Building the project with Gradle
To build the project and generate the Jar, just use:

```
./gradlew build
```

It generates a _fat-jar_ in the `build/libs` directory.

## Running the project with Gradle
Once you have retrieved the project, you can check that everything works with:

```
./gradlew build test run
```

The command compiles the project and runs the tests, then it launches the application. Open your browser to http://localhost:8080 or execute:

```
curl http://localhost:8080
```

This will show:
```
Hello from foobar
```


You can just run without test:

```
./gradlew run
```

## Generate Docker image

This will generate a docker image in the local Docker registry

```
docker build -t hellofrom:1.0 .
```

```
docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
hellofrom           1.0                 df09fececfca        4 seconds ago       73.9MB
```

This image can be uploaded to Docker Hub:

```
docker tag hellofrom:1.0 hugovarela/hellofrom:1.0
docker push hugovarela/hellofrom:1.0
```

```
docker images
REPOSITORY             TAG                 IMAGE ID            CREATED             SIZE
hugovarela/hellofrom   1.0                 df09fececfca        22 minutes ago      73.9MB
hellofrom              1.0                 df09fececfca        22 minutes ago      73.9MB
```

Using Docker Hub is just for testing purposes for this task. You can use your own docker registry.

## Testing the docker image

Once the hellofrom:1.0 image is in the local registry, it can be started:

```
docker run -t -i -p 8080:8080 hellofrom:1.0
```

```
curl http://localhost:8080
(example output) Hello from foobar
```

## Testing the image in Kubernetes with minikube ip

For this task, we will use Docker Hub to get the image, it is in deployment.yml (image: hugovarela/hellofrom:1.0), you can use your own registry to get the image, just change it in the file.

The image can be tested using Minikube (assuming Minikube service is running locally).

```
kubectl create -f deployment.yml
```

The Service is configured to run in port: 30397  (nodePort: 30397), so the HelloFrom can be tested using the Minikube IP.
HelloFrom app will respond with the pod name when running in Kubernetes and "Hello from foobar" without Kubernetes. Check HelloFrom.java and deployment.yml.

```
curl http://$(minikube ip):30397
Hello from "pod name here"
```

## Testing with an External URL with Ingress

For example purposes, follow this steps to enable HelloFrom app with Ingress:

Get minikube ip
```
minikube ip ----> example output: 192.168.99.100
```

Add that IP and a hostname in /etc/hosts

```
192.168.99.100 testhello
```

Enable Ingress in minikube, if not enabled
```
minikube addons enable ingress
```

Create ingress with the ingress.yml generated for this task:
```
kubectl create -f ingress.yml
```

Finally, use the host added to /etc/hosts to test HelloFrom
```
curl http://testhello/hellofrom

(example output) Hello from hellofrom-56675f664c-qqbzz
```
