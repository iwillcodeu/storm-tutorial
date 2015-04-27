
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import org.apache.log4j.Logger;
import storm.kafka.Broker;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StaticHosts;
import storm.kafka.StringScheme;
import storm.kafka.trident.GlobalPartitionInformation;

/**
 * Tutorials class
 * This is the beginning for a set of Apache Storm tutorials to share.
 * Once you get the general concepts, it's pretty straightforward.  However
 * getting thos concepts can take a bit of work.
 * @author Ash
 */
public class tutorials {

    /**
     * This is our beginning storm bolt.  This processes Tuple data sent to it
     * from a spout.
     */
    public static class PrinterBolt extends BaseBasicBolt {
        static Logger logger = Logger.getLogger(PrinterBolt.class);
        
        /**
         * This execute method reads the data ingested by the spout and simply
         * logs it.
         * It's a very straightforward bolt.
         * 
         * @param input
         * @param collector
         */
        @Override
        public void execute(Tuple input, BasicOutputCollector collector) {
            String msg = input.getString(0);
            logger.info(String.format("Received message: %s", msg));
        }
        
        /**
         * Currently this does nothing.
         * @param declarer
         */
        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
        }
    }

    /**
     * Main entry point to the application
     * See inline for details.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        
        // The TopologyBuilder is what defines the topology for the storm
        // processing 'network'
        TopologyBuilder builder = new TopologyBuilder();
        
        // This next section defines the configuration information for the Kafka
        // spout.
        GlobalPartitionInformation hostsAndPartition = new GlobalPartitionInformation();
        hostsAndPartition.addPartition(0, new Broker("ec2-54-237-25-84.compute-1.amazonaws.com", 9092));
        BrokerHosts brokerHosts = new StaticHosts(hostsAndPartition);
        SpoutConfig config = new SpoutConfig(brokerHosts, "sentenceTutorial", "/demonwaretutorial", "987654321");
        
        // defining a scheme here does some default data transformation.
        // if we don't define it, the data comes across as raw binary.
        config.scheme = new SchemeAsMultiScheme(new StringScheme());
        
        // This is where we define a spout in our topology
        builder.setSpout("spout", new KafkaSpout(config), 1);
        
        // This is were we define our bolt in the topology and what data it receives
        builder.setBolt("print", new PrinterBolt(), 1).shuffleGrouping("spout");

        // reduce the amount of debugging information
        Config conf = new Config();
        conf.setDebug(false);
        
        if (args != null && args.length > 0) {
            // So, if we're in here, we're running the topology on a cluster.
            conf.setNumWorkers(3);
            
            StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
        }
        else {
            // if we're here, we're running stand-alone.
            conf.setMaxTaskParallelism(3);
            
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("tutorial01", conf, builder.createTopology());
            
            Thread.sleep(100000);
            
            cluster.killTopology("tutorial01");
            cluster.shutdown();
        }
    }
}
