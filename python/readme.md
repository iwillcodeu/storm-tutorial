# building the Python examples
The python folder contains an example of submitting data to Kafka. It's rudimentary
(to say the least) but it's all that's needed.

## How To build
1. Make sure you have PIP installed
2. I'm using PyBuilder for dependency checking.  So you can install PyBuilder via
   pip install pybuilder
   1. PyBuilder on Windows has some weirdness. You run it via pyb_. Every other
      platform is pyb.
   2. so, under windows, open up a shell and type pyb_ in order to build.
3. Running the DataPusher.py script needs to run twice - the first time will fail
   because the topic doesn't exist.  The next pass will work.
   - I need to have it test and create the topic later on.
4. You'll also need to supply a valid hostname in code (I'm going to add a config
   for that shortly)
