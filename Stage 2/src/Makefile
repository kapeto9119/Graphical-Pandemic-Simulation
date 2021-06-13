JFXLIB = "/home/theuszero/USM/POO/javafx-sdk-11.0.2/lib" #change this for your JavaFx Lib
JFLAGS = -g -encoding ISO8859_1 --module-path $(JFXLIB) --add-modules javafx.controls,javafx.fxml
JC = javac
JVM= java  # Added by Agust�n Gonz�lez
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = *.java

MAIN = Stage1

default: classes

classes: $(CLASSES:.java=.class)

run:
	$(JVM) $(MAIN) $(ARGS)

clean:
	$(RM) *.class
