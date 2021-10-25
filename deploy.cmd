call mvn clean install -DskipTests
call docker build -t boudhayandev/kymapilot:1 .
call docker push boudhayandev/kymapilot:1
call kubectl delete deployment kymapilot
call kubectl apply -f deployment.yaml