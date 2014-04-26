package at.ac.tuwien.big.we14.lab2.api.service;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.domain.Round;

import java.util.List;

/**
 * Created by Lukas on 26.04.2014.
 */
public interface RoundService {

    public void startRound();

    public List<Round> createRoundsWithAnswersFromCategories(List<Category> categories);
}
