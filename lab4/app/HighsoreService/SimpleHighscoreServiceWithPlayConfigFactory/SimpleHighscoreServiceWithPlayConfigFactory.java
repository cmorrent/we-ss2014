package HighsoreService.SimpleHighscoreServiceWithPlayConfigFactory;

import HighsoreService.HighscoreService;
import HighsoreService.HighscoreServiceConfig;
import HighsoreService.HighscoreServiceFactory;
import HighsoreService.PlayConfig.PlayConfig;
import HighsoreService.SimpleHighscoreService.SimpleHighscoreService;

/**
 * Created by willi on 5/16/14.
 */
public class SimpleHighscoreServiceWithPlayConfigFactory implements HighscoreServiceFactory{

    public static SimpleHighscoreServiceWithPlayConfigFactory getInstance(){
        return new SimpleHighscoreServiceWithPlayConfigFactory();
    }

    @Override
    public HighscoreService create() {
        HighscoreServiceConfig highscoreServiceConfig = new PlayConfig();
        return new SimpleHighscoreService(highscoreServiceConfig);
    }
}
