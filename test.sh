# Wazne nalezy dodac uprawnienia chmod +x test.sh
# 1. Pobierz JUnit JAR do tego samego katalogu
curl -o junit-platform-console-standalone-1.9.2.jar \
  https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.2/junit-platform-console-standalone-1.9.2.jar

# 2. Kompiluj wszystko razem
javac -cp "junit-platform-console-standalone-1.9.2.jar:." *.java

# 3. Uruchom testy
java -jar junit-platform-console-standalone-1.9.2.jar \
     --class-path . \
     --scan-class-path
