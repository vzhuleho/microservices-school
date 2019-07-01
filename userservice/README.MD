# User Service

##  How to run on Minikube

* Install Minikube and VirtualBox (there is a problem with DNS if Minikube runs on Host OS).
* Run `minikube start`
* To make application docker image available locally on the virtual machine running Minikube execute `eval $(minikube docker-env)`. After that docker commands will be excuted against Docker daemon inside the virtual machine.
* Build the project and a docker image by executing `./mvnw package dockerfile:build`
* Create Secret that contains a password for DB user, for example, by applying following resource:
```yaml
apiVersion: v1
kind: Secret
metadata:
  name: mysql-pass
type: Opaque
data:
  password: ${PUT_YOUR_PASSWORD}
```
* Run the following command to get the IP Address for the service `minikube service userservice-app --url`
