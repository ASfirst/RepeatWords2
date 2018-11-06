package com.jeramtough.repeatwords2.component.teacher;

import android.util.SparseIntArray;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtlog.facade.L;
import com.jeramtough.repeatwords2.bean.word.Word;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
        } else {
            return null;
        }
    }

    @Override
    public Word[] getAllRandomNeedLearningWords() {
        Collections.shuffle(shallLearningWords);
        return shallLearningWords.toArray(new Word[shallLearningWords.size()]);
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

    public List<Integer> processNoNeededIdsOfLearning(List<Integer> haveLearnedWordIds) {
        SparseIntArray countMap = new SparseIntArray();
//        SparseIntArray<Integer, Integer> countMap = new SparseIntArray();
        List<Integer> noNeededIdsOfLearning = new ArrayList<>();
        for (Integer integer : haveLearnedWordIds) {
            int count = countMap.get(integer);
            count++;
            if (count >= 2) {
                noNeededIdsOfLearning.add(integer);
            } else {
                countMap.put(integer, count);
            }
        }
        return noNeededIdsOfLearning;
    }
}
