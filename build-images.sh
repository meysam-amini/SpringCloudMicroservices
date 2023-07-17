#for registering images on minikube env
eval $(minikube -p minikube docker-env)

#cd wallet-manager/wallet-webapi/
#docker build -t wallet-ws .
#
#cd ../../users/users-webapi/
#docker build -t users-ws .

#cd ../../config-server/
#cd config-server/
#docker build -t config-server .

cd backoffice/backoffice-webapi/
#cd backoffice/backoffice-webapi/
docker build -t backoffice-ws .

#cd ../../auth/auth-webapi/
#docker build -t auth-ws .

#cd ../../api-gateway/
#docker build -t api-gateway .