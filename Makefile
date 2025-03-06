default: compile
compile: src/*.java
	@mkdir -p bin
	@javac -d bin src/*.java 
run: compile
	@java -cp bin src.Main