call mvn clean install -DskipTests
call docker build -t boudhayandev/kymapilot:1 .
call docker push boudhayandev/kymapilot:1
call kubectl delete deployment kymapilot
call kubectl delete deployment poc-gateway
call kubectl delete ServiceBinding kyma-native-uaa-binding
call kubectl delete ServiceBinding kyma-native-destination-binding
call kubectl delete Secret kyma-cf-hana-secret
call kubectl delete Service kymapilot
call kubectl delete Service poc-gateway
call kubectl delete APIRule poc-gateway
call kubectl apply -f deployment.yaml