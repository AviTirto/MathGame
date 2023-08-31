runTests: FrontendDeveloperTests.class
	java --module-path openjfx-19_linux-aarch64_bin-sdk/javafx-sdk-19/lib --add-modules javafx.controls --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar junit5.jar -cp .:JavaFXTester.jar --scan-classpath

FrontendDeveloperTests.class: FrontendDeveloperTests.java DijkstrasBackend.class MCDFrontend.class
	javac --module-path openjfx-19_linux-aarch64_bin-sdk/javafx-sdk-19/lib --add-modules javafx.controls -cp .:junit5.jar:JavaFXTester.jar FrontendDeveloperTests.java

DijkstrasBackend.class: ICMDijkstrasBackend.java DijkstrasBackendFD.java
	javac DijkstrasBackendFD.java

MCDFrontend.class: IMCDFrontend.java MCDFrontend.java
	javac --module-path openjfx-19_linux-aarch64_bin-sdk/javafx-sdk-19/lib/ --add-modules javafx.controls MCDFrontend.java
clean:
	rm *.class
