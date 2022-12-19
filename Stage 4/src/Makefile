JFXLIB = "/home/theuszero/USM/javafx-sdk-11.0.2/lib" #change this for your JavaFx Lib
JAVAFX_JFLAGS = --module-path $(JFXLIB) --add-modules javafx.controls,javafx.fxml,javafx.media
JFLAGS = -g -encoding ISO8859_1 $(JAVAFX_JFLAGS)
MP3 = "BatmanSoundEffect.mp3"
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
	$(JVM) $(JAVAFX_JFLAGS) $(MAIN) $(ARGS) $(MP3)

clean:
	$(RM) *.class

