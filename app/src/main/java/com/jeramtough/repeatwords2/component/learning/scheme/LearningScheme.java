package com.jeramtough.repeatwords2.component.learning.scheme;

import com.jeramtough.jtcomponent.callback.CommonCallback;
import com.jeramtough.oedslib.entity.LargeWord;

/**
 * Created on 2019-08-31 22:04
 * by @author JeramTough
 */
public interface LearningScheme {

    void initScheme(CommonCallback<LargeWord> callback);

}
