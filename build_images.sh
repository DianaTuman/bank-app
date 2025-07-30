#!/bin/bash

# Exit immediately if a command exits with a non-zero status.
set -e

# --- Configuration ---
BANK_UI_DIR="bank-ui"
BANK_UI_IMAGE_NAME="bank-ui"
BANK_UI_IMAGE_TAG="0.0.1-SNAPSHOT"

ACCOUNTS_SERVICE_DIR="accounts"
ACCOUNTS_IMAGE_NAME="accounts-service"
ACCOUNTS_IMAGE_TAG="0.0.1-SNAPSHOT"

BLOCKER_SERVICE_DIR="blocker"
BLOCKER_IMAGE_NAME="blocker-service"
BLOCKER_IMAGE_TAG="0.0.1-SNAPSHOT"

CASH_SERVICE_DIR="cash"
CASH_IMAGE_NAME="cash-service"
CASH_IMAGE_TAG="0.0.1-SNAPSHOT"

EXCHANGE_SERVICE_DIR="exchange"
EXCHANGE_IMAGE_NAME="exchange-service"
EXCHANGE_IMAGE_TAG="0.0.1-SNAPSHOT"

EXCHANGE_GENERATOR_SERVICE_DIR="exchange-generator"
EXCHANGE_GENERATOR_IMAGE_NAME="exchange-generator-service"
EXCHANGE_GENERATOR_IMAGE_TAG="0.0.1-SNAPSHOT"

GATEWAY_API_DIR="gateway-api"
GATEWAY_API_IMAGE_NAME="gateway-api"
GATEWAY_API_IMAGE_TAG="0.0.1-SNAPSHOT"

NOTIFICATIONS_SERVICE_DIR="notifications"
NOTIFICATIONS_IMAGE_NAME="notifications-service"
NOTIFICATIONS_IMAGE_TAG="0.0.1-SNAPSHOT"

TRANSFER_SERVICE_DIR="transfer"
TRANSFER_IMAGE_NAME="transfer-service"
TRANSFER_IMAGE_TAG="0.0.1-SNAPSHOT"
# ---------------------

echo "Building Docker image for Bank UI: ${BANK_UI_IMAGE_NAME}:${BANK_UI_IMAGE_TAG}"
pushd "$BANK_UI_DIR" > /dev/null
docker build -t "${BANK_UI_IMAGE_NAME}:${BANK_UI_IMAGE_TAG}" .
echo "Bank UI image built: ${BANK_UI_IMAGE_NAME}:${BANK_UI_IMAGE_TAG}"

popd > /dev/null
echo ""

echo "Building Docker image for Accounts Service: ${ACCOUNTS_IMAGE_NAME}:${ACCOUNTS_IMAGE_TAG}"
pushd "$ACCOUNTS_SERVICE_DIR" > /dev/null
docker build -t "${ACCOUNTS_IMAGE_NAME}:${ACCOUNTS_IMAGE_TAG}" .
echo "Order Accounts Service image built: ${ACCOUNTS_IMAGE_NAME}:${ACCOUNTS_IMAGE_TAG}"

popd > /dev/null
echo ""

echo "Building Docker image for Blocker Service: ${BLOCKER_IMAGE_NAME}:${BLOCKER_IMAGE_TAG}"
pushd "$BLOCKER_SERVICE_DIR" > /dev/null
docker build -t "${BLOCKER_IMAGE_NAME}:${BLOCKER_IMAGE_TAG}" .
echo "Order Blocker Service image built: ${BLOCKER_IMAGE_NAME}:${BLOCKER_IMAGE_TAG}"

popd > /dev/null
echo ""

echo "Building Docker image for Cash Service: ${CASH_IMAGE_NAME}:${CASH_IMAGE_TAG}"
pushd "$CASH_SERVICE_DIR" > /dev/null
docker build -t "${CASH_IMAGE_NAME}:${CASH_IMAGE_TAG}" .
echo "Order Cash Service image built: ${CASH_IMAGE_NAME}:${CASH_IMAGE_TAG}"

popd > /dev/null
echo ""

echo "Building Docker image for Exchange Service: ${EXCHANGE_IMAGE_NAME}:${EXCHANGE_IMAGE_TAG}"
pushd "$EXCHANGE_SERVICE_DIR" > /dev/null
docker build -t "${EXCHANGE_IMAGE_NAME}:${EXCHANGE_IMAGE_TAG}" .
echo "Order Exchange Service image built: ${EXCHANGE_IMAGE_NAME}:${EXCHANGE_IMAGE_TAG}"

popd > /dev/null
echo ""

echo "Building Docker image for Exchange Generator Service: ${EXCHANGE_GENERATOR_IMAGE_NAME}:${EXCHANGE_GENERATOR_IMAGE_TAG}"
pushd "$EXCHANGE_GENERATOR_SERVICE_DIR" > /dev/null
docker build -t "${EXCHANGE_GENERATOR_IMAGE_NAME}:${EXCHANGE_GENERATOR_IMAGE_TAG}" .
echo "Order Exchange Generator Service image built: ${EXCHANGE_GENERATOR_IMAGE_NAME}:${EXCHANGE_GENERATOR_IMAGE_TAG}"

popd > /dev/null
echo ""

echo "Building Docker image for Notifications Service: ${NOTIFICATIONS_IMAGE_NAME}:${NOTIFICATIONS_IMAGE_TAG}"
pushd "$NOTIFICATIONS_SERVICE_DIR" > /dev/null
docker build -t "${NOTIFICATIONS_IMAGE_NAME}:${NOTIFICATIONS_IMAGE_TAG}" .
echo "Order Notifications Service image built: ${NOTIFICATIONS_IMAGE_NAME}:${NOTIFICATIONS_IMAGE_TAG}"

popd > /dev/null
echo ""

echo "Building Docker image for Transfer Service: ${TRANSFER_IMAGE_NAME}:${TRANSFER_IMAGE_TAG}"
pushd "$TRANSFER_SERVICE_DIR" > /dev/null
docker build -t "${TRANSFER_IMAGE_NAME}:${TRANSFER_IMAGE_TAG}" .
echo "Order Transfer Service image built: ${TRANSFER_IMAGE_NAME}:${TRANSFER_IMAGE_TAG}"

popd > /dev/null
echo ""

echo "Building Docker image for Gateway API: ${GATEWAY_API_IMAGE_NAME}:${GATEWAY_API_IMAGE_TAG}"
pushd "$GATEWAY_API_DIR" > /dev/null
docker build -t "${GATEWAY_API_IMAGE_NAME}:${GATEWAY_API_IMAGE_TAG}" .
echo "Order Gateway API image built: ${GATEWAY_API_IMAGE_NAME}:${GATEWAY_API_IMAGE_TAG}"

popd > /dev/null
echo ""
echo "Docker images built successfully!" 