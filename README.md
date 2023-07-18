

<iframe src="https://miro.com/app/live-embed/uXjVM1HktrI=/?moveToViewport=-2835,-2618,5752,5628&embedId=885752217427" scrolling="no" allow="fullscreen; clipboard-read; clipboard-write" allowfullscreen width="768" height="432" frameborder="0"></iframe>


A scenario of communication between MicroServices that implemented in SpringBoot using Spring Cloud Api
gateway and Eureka service discovery. SpringSecurity + JWT mechanism with a custom security filter used for
authentication and authorization, and also all common configurations on microservices are broadcasting from a
ConfigServer (fetches configs from local file system or a remote Git repository) via SpringCloud Bus. Feign
Client is used in services for fallback, in the case of unavailability.
