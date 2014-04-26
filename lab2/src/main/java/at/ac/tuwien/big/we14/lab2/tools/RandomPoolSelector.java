package at.ac.tuwien.big.we14.lab2.tools;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.domain.Round;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by willi on 4/26/14.
 */
public class RandomPoolSelector <A> {

    private Random random = new Random();
    Logger logger = Logger.getLogger("");

    public List<A> getRandomPoolWithSizeForList(int size, List<A> pool){
        List<Integer> keys = getRandomListKeysForListSizeAndCount(pool.size(), size);
        List<A> retList = new ArrayList<A>();

        for(int i = 0; i < keys.size(); i++){
            int key = keys.get(i);
            retList.add(pool.get(key));
        }

        return retList;
    }


    private List<Integer> getRandomListKeysForListSizeAndCount(int listSize, int count){
        List<Integer> pool = new ArrayList<Integer>();
        List<Integer> retList = new ArrayList<Integer>();

        for(int i = 0; i < listSize; i++){
            pool.add(i);
        }

        for(int i = 0; i < count; i++){
            int poolSize = pool.size();
            int randomNr = random.nextInt(poolSize);
            retList.add(pool.get(randomNr));
            pool.remove(randomNr);
        }

        return retList;
    }
}
