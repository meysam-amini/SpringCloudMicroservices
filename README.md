
![SpringCloudMicroservices(4)](https://github.com/meysam-amini/SpringCloudMicroservices/assets/59673699/5a141d1e-adbb-41ab-8321-c25a91698eb5)


A scenario of communication between MicroServices that implemented in SpringBoot using Spring Cloud Api
gateway and Eureka service discovery. SpringSecurity + JWT mechanism with a custom security filter used for
authentication and authorization, and also all common configurations on microservices are broadcasting from a
ConfigServer (fetches configs from local file system or a remote Git repository) via SpringCloud Bus. Feign
Client is used in services for fallback, in the case of unavailability.
