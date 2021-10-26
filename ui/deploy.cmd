call docker build -t boudhayandev/kymapilot-ui:latest .
call docker push boudhayandev/kymapilot-ui:latest
call kubectl delete ServiceBinding kyma-native-uaa-binding-ui
call kubectl delete ConfigMap kyma-destination-backend-config
call kubectl delete ServiceBindingUsage kyma-native-uaa-binding-usage-ui
call kubectl delete Deployment kymapilot-ui
call kubectl delete Service kymapilot-ui
call kubectl delete APIRule kymapilot-ui
call kubectl apply -f deployment.yaml