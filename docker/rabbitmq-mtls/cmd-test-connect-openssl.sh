openssl s_client \
  -connect localhost:5671 \
  -cert rabbitmq/certs/client.pem \
  -key rabbitmq/certs/client.key \
  -CAfile rabbitmq/certs/ca.pem