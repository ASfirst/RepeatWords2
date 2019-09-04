package com.jeramtough.repeatwords2.component.learning.school.teacher;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.bean.word.WordWithIsLearnedAtLeastTwiceToday;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 11718
 * on 2018  May 03 Thursday 19:07.
 */
@JtComponent
public class WordsTeacher implements Teacher {
    private List<Word> shallLearningWords;
    private Word previousWord;
    private Word currentWord;


    @IocAutowire
    public WordsTeacher() {
        shallLearningWords = new ArrayList<>();
    }

    public void addWordToList(Word word) {
        shallLearningWords.add(word);
    }

    public void clear() {
        shallLearningWords.clear();
        currentWord = null;
        previousWord = null;
    }

    public void removeWordFromList(Word word) {
        shallLearningWords.remove(word);
        previousWord = word;
    }

    @Override
    public Word getNextNeedLearningWord() {
        if (shallLearningWords.size() > 0) {
            int position = RandomUtils.nextInt(0, shallLearningWords.size());
            Word word = shallLearningWords.get(position);
            if (shallLearningWords.size() != 1) {
                while (currentWord == word) {
                    position = RandomUtils.nextInt(0, shallLearningWords.size());
                    word = shallLearningWords.get(position);
                }
            }

            previousWord = currentWord;
            currentWord = word;

            return currentWord;
        }
        else {
            return null;
        }
    }

    @Override
    public Word[] getAllRandomNeedLearningWords() {
        Collections.shuffle(shallLearningWords);
        return shallLearningWords.toArray(new Word[shallLearningWords.size()]);
    }

    @Override
    public Word[] getRandomNeedLearningWords(int size) {
        if (shallLearningWords.size() > size) {
            Word[] words = new Word[size];
            System.arraycopy(getAllRandomNeedLearningWords(), 0, words, 0, size);
            return words;
        }
        else {
            return getAllRandomNeedLearningWords();
        }

    }

    @Override
    public WordWithIsLearnedAtLeastTwiceToday[] getAllRandomNeedLearningWordsWithIsLearedTodayAtLeastTwice(
            List<Integer> todaysHaveLearnedWordsIdAtLeastTwice) {
        WordWithIsLearnedAtLeastTwiceToday[] wordWithIsLearnedAtLeastTwiceTodays = new
                WordWithIsLearnedAtLeastTwiceToday[shallLearningWords.size()];
        for (int i = 0; i < shallLearningWords.size(); i++) {
            Word word = shallLearningWords.get(i);
            boolean isLearedAtLeastTwiceToday = false;
            for (int haveLearnedWordsId : todaysHaveLearnedWordsIdAtLeastTwice) {
                if (word.getId() == haveLearnedWordsId) {
                    isLearedAtLeastTwiceToday = true;
                    break;
                }
            }
            WordWithIsLearnedAtLeastTwiceToday wordWithIsLearnedAtLeastTwiceToday = new
                    WordWithIsLearnedAtLeastTwiceToday();
            wordWithIsLearnedAtLeastTwiceToday.setLearnedAtLeastTwiceToday(
                    isLearedAtLeastTwiceToday);
            wordWithIsLearnedAtLeastTwiceToday.setCh(word.getCh());
            wordWithIsLearnedAtLeastTwiceToday.setEn(word.getEn());
            wordWithIsLearnedAtLeastTwiceToday.setId(word.getId());
            wordWithIsLearnedAtLeastTwiceToday.setPhonetic(word.getPhonetic());

            wordWithIsLearnedAtLeastTwiceTodays[i] = wordWithIsLearnedAtLeastTwiceToday;
        }

        return wordWithIsLearnedAtLeastTwiceTodays;
    }


    public Word getPreviousWord() {
        return previousWord;
    }

    public synchronized Word getCurrentlyLearningWord() {
        return currentWord;
    }

    public int getShallLearningSize() {
        return shallLearningWords.size();
    }

}
