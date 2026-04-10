mkdir -p rabbitmq/certs

#### Create a CA
openssl req -x509 -newkey rsa:4096 -days 3650 -nodes \
  -keyout rabbitmq/certs/ca.key \
  -out rabbitmq/certs/ca.pem \
  -subj "/CN=MyCA"

#### Server cert
openssl req -newkey rsa:2048 -nodes \
  -keyout rabbitmq/certs/server.key \
  -out rabbitmq/certs/server.csr \
  -subj "/CN=localhost"

openssl x509 -req \
  -in rabbitmq/certs/server.csr \
  -CA rabbitmq/certs/ca.pem \
  -CAkey rabbitmq/certs/ca.key \
  -CAcreateserial \
  -out rabbitmq/certs/server.pem \
  -days 365

#### Client cert
openssl req -newkey rsa:2048 -nodes \
  -keyout rabbitmq/certs/client.key \
  -out rabbitmq/certs/client.csr \
  -subj "/CN=spring-client"

openssl x509 -req \
  -in rabbitmq/certs/client.csr \
  -CA rabbitmq/certs/ca.pem \
  -CAkey rabbitmq/certs/ca.key \
  -CAcreateserial \
  -out rabbitmq/certs/client.pem \
  -days 365