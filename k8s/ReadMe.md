### Install Kompose (Convert Docker Compose → K8s).<br/>

---
Kompose converts `docker-compose.yml` to Kubernetes manifests.
```
brew install kompose
```
Then
```
kompose convert
```
---

## Minikube

---
Install:
```bash
brew install minikube
```

```bash
minikube start
kubectl get nodes
minikube dashboard
minikube dashboard --url
```

### Build and Push Images

---
🧠 Important For Minikube (Local Images)
```
docker context ls
kubectl config current-context
kubectl get storageclass

docker images | grep landmark           
minikube image list
```

```
eval $(minikube docker-env)

docker build -t landmark-nearify ./landmark-nearify
docker build -t landmark-nearby ./landmark-nearby
docker build -t landmark-geo-query-engine ./landmark-geo-query-engine

docker build -t landmark-osm-importer-local ./landmark-osm-importer-local
docker build -t landmark-osm-importer-auto ./landmark-osm-importer-auto
```
Set:
```
imagePullPolicy: Never
```
Otherwise Kubernetes will try pulling from Docker Hub.

🛠 Deploy
```
kubectl apply -f k8s/namespaces/

kubectl apply -f k8s/infrastructure/postgres/
kubectl apply -f k8s/infrastructure/rabbitmq/
kubectl apply -f k8s/infrastructure/pgadmin/


kubectl apply -f k8s/services/landmark-nearify/
kubectl apply -f k8s/services/landmark-nearby/
kubectl apply -f k8s/services/landmark-geo-query-engine/

kubectl apply -f k8s/monitoring/prometheus/
kubectl apply -f k8s/monitoring/grafana/
```

Job:
```
minikube image load landmark-osm-importer-local:latest
kubectl apply -f k8s/jobs/landmark-osm-import-local-job.yaml

minikube image load landmark-osm-importer-auto:latest
kubectl apply -f k8s/jobs/landmark-osm-import-auto-job.yaml

docker images | grep landmark 
minikube image ls | grep landmark

docker image rmi <>
minikube image rm docker.io/library/landmark-osm-importer-local:latest 
minikube image rm docker.io/library/landmark-osm-importer-auto:latest 
```

Go into Pod:
```
kubectl exec -it -n landmark landmark-nearby-7f985c48d5-sk9mg    -- sh
kubectl exec -it -n landmark landmark-osm-import-local-job-74hr5 -- sh
kubectl exec -it -n landmark landmark-osm-import-auto-job-86klj  -- sh
```
Port-Forward:
```
kubectl port-forward -n landmark svc/landmark-nearify 8084:8084 
kubectl port-forward -n landmark svc/landmark-nearby 8086:8086
kubectl port-forward -n landmark svc/landmark-geo-query-engine 8087:8087 

kubectl port-forward -n landmark svc/postgres 5432:5432
kubectl port-forward -n landmark svc/rabbitmq 15672:15672

kubectl port-forward -n monitoring svc/prometheus 9090:9090
kubectl port-forward -n monitoring svc/grafana 3000:3000

kubectl port-forward -n landmark svc/pgadmin 30505:80
```

🔗 Access URLs

| Service          | Port-Forward                                                                 | URL                                              |
| -----------------|------------------------------------------------------------------------------| ------------------------------------------------ |
| RabbitMQ         | `kubectl port-forward -n landmark svc/rabbitmq 15672:15672`                  | [http://localhost:15672](http://localhost:15672) |
| pgAdmin          | `kubectl port-forward -n landmark svc/pgadmin 30505:80`                      | [http://localhost:30505](http://localhost:30505) |
| Prometheus       | `kubectl port-forward -n monitoring svc/prometheus 9090:9090`                | [http://localhost:9090](http://localhost:9090)   |
| Grafana          | `kubectl port-forward -n monitoring svc/grafana 3000:3000`                   | [http://localhost:3000](http://localhost:3000)   |
| Nearify API      | `kubectl port-forward -n landmark svc/landmark-nearify 8084:8084`            | [http://localhost:8084](http://localhost:8084)   |
| Nearby API       | `kubectl port-forward -n landmark svc/landmark-nearby 8086:8086`             | [http://localhost:8086](http://localhost:8086)   |
| Geo Query Engine | `kubectl port-forward -n landmark svc/landmark-geo-query-engine 8087:8087`   | [http://localhost:8087](http://localhost:8087)   |



Delete:
```
kubectl delete -f k8s/namespaces/

kubectl delete -f k8s/infrastructure/postgres/
kubectl delete -f k8s/infrastructure/rabbitmq/
kubectl delete -f k8s/infrastructure/pgadmin/

kubectl delete -f k8s/services/landmark-nearify/
kubectl delete -f k8s/services/landmark-nearby/
kubectl delete -f k8s/services/landmark-geo-query-engine/

kubectl delete -f k8s/monitoring/prometheus/
kubectl delete -f k8s/monitoring/grafana/

kubectl delete -f k8s/jobs/landmark-osm-import-local-job.yaml
kubectl delete -f k8s/jobs/landmark-osm-import-auto-job.yaml

```