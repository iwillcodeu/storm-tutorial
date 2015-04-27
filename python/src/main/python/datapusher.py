from kafka.client import KafkaClient
from kafka.producer import SimpleProducer

import time
import random
import logging

logging.basicConfig(level=logging.INFO)

pronouns_possessive = [ "my", "your", "his", "her", "its", "our", "their", "whose"]
nouns_singular = ["attorney-general", "brother-in-law", "bystander", "commander-in-chief", "court martial", "cupful", "father-in-law", "general staff", "go-between", "governor-general", "handful", "heir apparent", "maidservant", "man-of-war", "mix-up", "mother-in-law", "mouthful", "notary public", "passer-by", "sergeant major", "sister-in-law", "son-in-law", "step-parent", "step-daughter", "step-mother", "tablespoonful", "takeoff"]
nouns_plural = ["attorneys-general", "brothers-in-law", "bystanders", "commanders-in-chief", "courts martial", "cupfuls", "fathers-in-law", "general staff", "go-betweens", "governors-general", "heirs apparent", "handfuls", "maidservants", "men-of-war", "mix-ups", "mothers-in-law", "mouthfuls", "notaries public", "passers-by", "sergeants major", "sisters-in-law", "sons-in-law", "step-parents", "step-daughters", "step-mothers", "tablespoonfuls", "takeoffs"]
verbs_singluar = ["become", "catch", "drink", "drive", "fly", "forgive", "get", "go", "hide", "know", "lead", "pay", "ride", "seek", "sing", "speak", "spring", "steal", "tear", "wear", "write"]
verbs_plural = ["becomes", "catches", "drinks", "drives", "flies", "forgives", "gets", "goes", "hides", "knows", "leads", "pays", "rides", "seeks", "sings", "speaks", "springs", "steals", "tears", "wears", "writes"]
infinitives = ["to make a pie.", "for no apparent reason.", "because the sky is green.", "for a disease.", "to be able to make toast explode.", "to know more about archeology."]

broker = "hostname:9092"

logging.info("Connection to Kafka = {0}".format(broker))

client = KafkaClient(broker)
producer = SimpleProducer(client)
index = 0
logging.info("Sending strings")

client.ensure_topic_exists("sentenceTutorial")

while index < 10000:
    index+=1
    sentence = "{0} {1} {2} {3} {4}".format( random.choice(pronouns_possessive),
        random.choice(nouns_singular),
        random.choice(verbs_plural).lower(),
        random.choice(nouns_singular).lower() or random.choice(nouns_plural).lower(),
        random.choice(infinitives))
    producer.send_messages('sentenceTutorial', sentence)
    logging.info("sent {0} of 10000. Message: {1}".format(index, sentence))
    time.sleep(5)
