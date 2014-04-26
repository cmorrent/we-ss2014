package at.ac.tuwien.big.we14.lab2.api.service.impl;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.domain.Answer;
import at.ac.tuwien.big.we14.lab2.api.domain.AnswerStatus;
import at.ac.tuwien.big.we14.lab2.api.domain.Round;
import at.ac.tuwien.big.we14.lab2.api.domain.RoundStatus;
import at.ac.tuwien.big.we14.lab2.api.service.RoundService;
import at.ac.tuwien.big.we14.lab2.tools.RandomPoolSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by willi on 4/26/14.
 */
public class RoundServiceImpl implements RoundService {

    private RandomPoolSelector<Question> randomPoolSelector = new RandomPoolSelector<Question>();

    @Override
    public void startRound() {

    }

    @Override
    public List<Round> createRoundsWithAnswersFromCategories(List<Category> categories) {
        List<Round> rounds = new ArrayList<Round>();

        for(int i = 0; i < categories.size(); i++){
            Category c = categories.get(i);
            rounds.add(createRoundWithAnswersFromCategorie(c));
        }

        return rounds;
    }


    public Round createRoundWithAnswersFromCategorie(Category category){
        Round r = new Round();
        List<Answer> answers = getAnswersWithRandomQuestionsForCategorie(category);

        r.setRoundStatus(RoundStatus.open);
        r.setCategory(category);
        r.setAnswers(answers);
        r.setActualAnswer(answers.get(0));

        return r;
    }


    public List<Answer> getAnswersWithRandomQuestionsForCategorie(Category category){
        List<Question> questions = category.getQuestions();
        List<Question> randomQuestions = randomPoolSelector.getRandomPoolWithSizeForList(3, questions);
        List<Answer> answers = new ArrayList<Answer>();

        for(int i = 0; i < randomQuestions.size(); i++){
            Question q = randomQuestions.get(i);
            Answer a = new Answer();
            a.setPlayer1AnswerStatus(AnswerStatus.open);
            a.setPlayer2AnswerStatus(AnswerStatus.open);
            a.setQuestion(q);
            answers.add(a);
        }

        return answers;
    }
}
