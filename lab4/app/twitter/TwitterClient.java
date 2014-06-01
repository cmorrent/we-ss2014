package twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;



public class TwitterClient implements ITwitterClient {
	
	private Twitter twitter;
	
	public TwitterClient(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("GZ6tiy1XyB9W0P4xEJudQ")
          .setOAuthConsumerSecret("gaJDlW0vf7en46JwHAOkZsTHvtAiZ3QUd2mD1x26J9w")
          .setOAuthAccessToken("1366513208-MutXEbBMAVOwrbFmZtj1r4Ih2vcoHGHE2207002")
          .setOAuthAccessTokenSecret("RMPWOePlus3xtURWRVnv1TgrjTyK7Zk33evp4KKyA");
        TwitterFactory tf = new TwitterFactory(cb.build());
        this.twitter = tf.getInstance();
	}

	@Override
	public void publishUuid(TwitterStatusMessage message) throws twitter4j.TwitterException {
		
		if (this.twitter == null){
			throw new twitter4j.TwitterException("TwitterClient was not initialized");
		}
		else {
			
				twitter.updateStatus(message.getTwitterPublicationString());
			
		}
	}

}
