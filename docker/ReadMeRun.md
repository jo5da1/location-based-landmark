```
docker images | grep landmark
docker volume ls
docker network ls
```
```
docker/
│
├── command-docker-build.sh
├── command-docker-clean.sh
├── command-docker-network.sh
│  
├── docker-infra/
│   ├── commands
│   └── docker-compose.yml
│  
├── docker-landmark/
│   └── docker-compose.yml
│
├── docker-monitoring/
│   ├── commands
│   └── docker-compose.yml
│  
├── docker-localstack-terraform/
│   └── docker-compose.yml
│  
├── README.md
└── .gitignore
```

### 🚀 Running the System

1️⃣ Build Everything.
```
sh command-docker-build.sh
sh command-docker-network.sh
```
<i>or</i>
```
make all
```
2️⃣ Clean Everything
```
sh command-docker-clean.sh
```
<i>or</i>
```
make clean
```
3️⃣  
```
```