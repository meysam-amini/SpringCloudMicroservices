# Spring Cloud Kubernetes Microservices

A scenario of secure communications between MicroServices which are implemented using SpringBoot3 and SpringCloud2022 and deployed on k8s cluster.

---

## Tech Stack:
* JDK 17
* Spring-Boot 3.1.2
* Spring-Cloud 22.0.3
* Spring-Cloud-Kubernetes 1.1.10
* Postgres 14.2
* Redis 6
* RabbitMQ 3
* Keycloak 21.1.1
* ELK 8.3.3

---
## Architecture
The system contains following modules:

1. Auth-Service: this module is responsible for interacting with Kyecloak for users registeration, authentication and authorization. for OTP process stores OTPs on Redis.
2. Users-Service: Exposes public pathes to interact with Auth-Service for registering and login for users, and some authenticated pathes for interacting with Wallet-Service.
3. Wallet-Service: Exposes some sample services with method level security based on clients' scope or users' roles. Its accessible only from Users-Service or Backoffice-Service.
4. Backoffice-Service: Its a sample service for managing common bussiness domain data of hole system, like users, roles, wallets, coins etc. There is a role mapping in this module that will be saved on Redis at start-up and authorization is handled by a custom security filter which is used to intercept requests and grant access to management services based on permissions of each role.
5. Api-Gateway: SpringCloudApiGateway is used for route incomming request from Ingress to microservices.
6. Discovery-Service: springCloudKubernetesDiscoveryserver is used for registering each of the above services inside the k8s cluster
7. Config-Server: Instead of defining all needed docker containers configs, I prefered to use SpringCloudConfigServer for centralizing business and also infrastructure configs. This might not be a best practice but I wanted to make develop and testing easier and less resource intensive as I had a simple laptop with just 8 gb memory. Also I didn't want to restart my deployments manually on changing bussines configs and this is straightforward by calling /busrefresh endpoint of Config-Server actuator, then changed configs will broadcast to services by RabbitMQ.





![SpringCloudMicroservices(4)](https://github.com/meysam-amini/SpringCloudMicroservices/assets/59673699/5a141d1e-adbb-41ab-8321-c25a91698eb5)

