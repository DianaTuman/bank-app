#!/bin/bash
set -e

# Загрузка переменных из .env
if [ -f .env ]; then
  export $(grep -v '^#' .env | xargs)
fi

# Проверка переменной
if [ -z "$DOCKER_REGISTRY" ]; then
  echo "DOCKER_REGISTRY не задан в .env"
  exit 1
fi

echo "Using DOCKER_REGISTRY: $DOCKER_REGISTRY"

echo "Uninstalling Helm releases..."
for ns in test prod; do
  helm delete bank-app || true
done

echo "Shutting down Jenkins..."
docker compose down -v || true
docker stop jenkins && docker rm jenkins || true
docker volume rm jenkins_home || true

echo "Removing images..."
docker image rm ${DOCKER_REGISTRY}/bank-ui:${IMAGE_TAG} || true
docker image rm ${DOCKER_REGISTRY}/accounts-service:${IMAGE_TAG} || true
docker image rm ${DOCKER_REGISTRY}/blocker-service:${IMAGE_TAG} || true
docker image rm ${DOCKER_REGISTRY}/cash-service:${IMAGE_TAG} || true
docker image rm ${DOCKER_REGISTRY}/exchange-service:${IMAGE_TAG} || true
docker image rm ${DOCKER_REGISTRY}/exchange-generator-service:${IMAGE_TAG} || true
docker image rm ${DOCKER_REGISTRY}/gateway-api:${IMAGE_TAG} || true
docker image rm ${DOCKER_REGISTRY}/notifications-service:${IMAGE_TAG} || true
docker image rm ${DOCKER_REGISTRY}/transfer-service:${IMAGE_TAG} || true
docker image rm jenkins/jenkins:lts-jdk21 || true

echo "Pruning system..."
docker system prune -af --volumes

echo "Done! All clean."
