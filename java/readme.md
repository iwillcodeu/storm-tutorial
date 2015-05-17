# How to build
Maven is the build system we're using.  The previous upload actually was faulty,
so I've updated it to actually work.  Here's how to run from the command line

## Building
I'm assuming you have Maven installed. From the same folder as this readme.md file
open a command prompt.  You'll need to run the following commands:

```bash
mvn package
```
or
```bash
mvn assembly:assembly

The first command packages a jar, without dependencies included.  The latter includes
dependencies.  That's the one you'll want.

This will create a target folder if it doesn't already exist.  And in it, it will
create a new jar file.

## Running
Again, from the command prompt:

```bash
java -cp target\\tutorial01-1.0-SNAPSHOT-jar-with-dependencies.jar tutorials
```

At this point, you should be good to go!
