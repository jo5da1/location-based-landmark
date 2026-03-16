FROM localstack/localstack:4.14.0

RUN apt-get update \
    && apt-get install -y wget unzip \
    && wget https://releases.hashicorp.com/terraform/1.7.5/terraform_1.7.5_linux_amd64.zip \
    && unzip terraform_1.7.5_linux_amd64.zip \
    && mv terraform /usr/local/bin/terraform \
    && rm terraform_1.7.5_linux_amd64.zip