package at.ac.tuwien.big.we14.lab2.api.service;

import at.ac.tuwien.big.we14.lab2.api.domain.DisplayPageEnum;
import at.ac.tuwien.big.we14.lab2.api.domain.Game;

/**
 * Created by willi on 4/9/14.
 */
public interface PageFlowService {

    public DisplayPageEnum getTargetPage(Game game);
}
