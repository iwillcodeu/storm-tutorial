from kafka.client import KafkaClient
from kafka.producer import SimpleProducer

import time
import random
import logging

logging.basicConfig(level=logging.INFO)

nouns_singular = []
nouns_possesive = []
verbs_singluar = []
verbs_possesive = []
infinitives = []

broker = ":9020"

logging.info("Connection to Kafka = {0}".format(broker))

client = KafkaClient(broker)

index = 0
logging.info("Sending strings")
while index < 10000:
    index+=1
    sentence = random.choice(verbs_singluar) + " " \
        random.choice(nouns_singular).lower or random.choice(nouns_possesive).lower() + " " \
        random.choice(infinitives)
    producer.send_message('myTopic', sentence)
    logging.info("sent {0} of 10000".format(index))
    time.sleep(5)
