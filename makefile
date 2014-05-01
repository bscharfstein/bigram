JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		b1.java \
		Encrypt.java \
		Decrypt.java \
		Gui.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class