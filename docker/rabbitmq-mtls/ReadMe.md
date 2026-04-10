### Folder Structure
````
rabbitmq-mtls/
├── docker-compose.yml
├── rabbitmq.conf
├── enabled_plugins
└── certs/
    ├── ca.pem
    ├── server.pem
    ├── server.key
    ├── client.pem
    └── client.pem
````
---
| Purpose | Private Key  | CSR          | Certificate  |
| ------- | ------------ | ------------ | ------------ |
| CA      | `ca.key`     | —            | `ca.pem`     |
| Server  | `server.key` | `server.csr` | `server.pem` |
| Client  | `client.key` | `client.csr` | `client.pem` |

---
### Generate Certificates (Quick Dev Setup)
#### Create a CA
```bash
openssl req -x509 -newkey rsa:4096 -days 3650 -nodes \
  -keyout ca.key -out ca.pem -subj "/CN=MyCA"
```
#### Server cert (RabbitMQ)
```bash
openssl req -newkey rsa:2048 -nodes \
  -keyout server.key -out server.csr -subj "/CN=localhost"
openssl x509 -req -in server.csr -CA ca.pem -CAkey ca.key \
  -CAcreateserial -out server.pem -days 365
```
#### Client cert (Spring Boot app)
```bash
openssl req -newkey rsa:2048 -nodes \
  -keyout client.key -out client.csr -subj "/CN=spring-client"
openssl x509 -req -in client.csr -CA ca.pem -CAkey ca.key \
  -CAcreateserial -out client.pem -days 365
```
---
### Generate Certificates (Self-Signed CA)
##### Create CA
```bash
openssl genrsa -out ca.key 4096
openssl req -x509 -new -nodes -key ca.key -sha256 -days 3650 -out ca.pem
```
##### Server cert
``````bash
openssl genrsa -out server.key 2048
openssl req -new -key server.key -out server.csr
openssl x509 -req -in server.csr \
  -CA ca.pem -CAkey ca.key -CAcreateserial \
  -out server.pem -days 365 -sha256
``````
##### Client cert
```bash
openssl genrsa -out client.key 2048
openssl req -new -key client.key -out client.csr
openssl x509 -req -in client.csr \
  -CA ca.pem -CAkey ca.key -CAcreateserial \
  -out client.pem -days 365 -sha256
```

---
### Configure RabbitMQ for mTLS
RabbitMQ Config (rabbitmq.conf)
```
listeners.tcp = none

listeners.ssl.default = 5671

ssl_options.cacertfile = /etc/rabbitmq/certs/ca.pem
ssl_options.certfile   = /etc/rabbitmq/certs/server.pem
ssl_options.keyfile    = /etc/rabbitmq/certs/server.key

ssl_options.verify               = verify_peer
ssl_options.fail_if_no_peer_cert = true

management.ssl.port       = 15671
management.ssl.cacertfile = /etc/rabbitmq/certs/ca.pem
management.ssl.certfile   = /etc/rabbitmq/certs/server.pem
management.ssl.keyfile    = /etc/rabbitmq/certs/server.key
```
##### Imp: Key lines
verify_peer → enforces client cert validation
fail_if_no_peer_cert = true → mTLS required

### Enable Plugins (enabled_plugins)
```
[rabbitmq_management].
```

### Test with OpenSSL
```bash
openssl s_client \
  -connect localhost:5671 \
  -cert certs/client.pem \
  -key certs/client.key \
  -CAfile certs/ca.pem
```
If mTLS works, will see:
```
Verify return code: 0 (ok)
```
