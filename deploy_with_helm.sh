#!/bin/bash

set -e  # Exit on any error
set -o pipefail

[ -f ./bank-app/Chart.lock ] && rm ./bank-app/Chart.lock

if helm list --short | grep -q "^bank-app$"; then
  echo "Deleting existing Helm release: bank-app"
  helm delete bank-app
else
  echo "Helm release 'bank-app' not found; skipping delete."
fi

echo "Building Helm dependencies..."
helm dependency build ./bank-app

echo "Installing Helm chart: bank-app..."
helm install bank-app ./bank-app --timeout 5m

echo "Looking for a pod with name containing 'bank-ui'..."
BANK_UI_POD=$(kubectl get pods --no-headers -o custom-columns=":metadata.name" | grep bank-ui | head -n 1)

if [ -z "$BANK_UI_POD" ]; then
  echo "No pod found with name containing 'bank-ui'"
  exit 1
fi

echo "Found pod: $BANK_UI_POD"
echo "Waiting for pod $BANK_UI_POD to be Ready..."

for i in {1..30}; do
  READY=$(kubectl get pod "$BANK_UI_POD" -o jsonpath='{.status.conditions[?(@.type=="Ready")].status}')
  if [ "$READY" == "True" ]; then
    echo "Pod $BANK_UI_POD is Ready."
    break
  fi
  echo "Pod not ready yet... ($i/30)"
  sleep 30
done

if [ "$READY" != "True" ]; then
  echo "Pod $BANK_UI_POD did not become Ready in time."
  exit 1
fi

echo "Forwarding port from pod to localhost:8080..."
kubectl port-forward pod/"$BANK_UI_POD" 8080:8080 &
PORT_FORWARD_PID=$!

echo "Opening app in browser at http://localhost:8080..."
if command -v xdg-open > /dev/null; then
    xdg-open http://localhost:8080
elif command -v open > /dev/null; then
    open http://localhost:8080
else
    echo "Could not detect browser opener. Please open manually: http://localhost:8080"
fi

echo "Press Ctrl+C to stop port-forwarding and exit."
wait $PORT_FORWARD_PID