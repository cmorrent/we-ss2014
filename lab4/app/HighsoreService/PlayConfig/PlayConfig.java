package HighsoreService.PlayConfig;

import HighsoreService.HighscoreServiceConfig;
import com.typesafe.config.ConfigFactory;
import play.api.Play;

/**
 * Created by willi on 5/16/14.
 */
public class PlayConfig implements HighscoreServiceConfig {
    @Override
    public String getUrl() {
        return ConfigFactory.load().getString("highscoreService.url");
    }

    @Override
    public String getUserKey() {
        return ConfigFactory.load().getString("highscoreService.userKey");
    }
}
