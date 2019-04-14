build: Checker.java Main.java
	javac Checker.java Main.java -classpath json-simple-1.1.1.jar
	@echo ""
	@echo "To run 'java -cp *:. Main <account ID>'"

buildlibra:
	git clone https://github.com/fangyidong/json-simple.git
	cd json-simple/ && ant && cd ..
	cp json-simple/target/json-simple-1.1.1.jar .
	cp json-simple/target/classes/org/json/simple/parser/* .

test:
	java -cp *:. Main 16461605
