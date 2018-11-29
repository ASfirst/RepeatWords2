package com.jeramtough.repeatwords2.component.ui.wordcard;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatImageView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.bean.word.Word;

public class WordCardView extends FrameLayout
        implements View.OnDragListener, View.OnClickListener {
    private Word word;

    private ViewGroup layout;
    private Button buttonDesert;
    private Button buttonMark;
    private ImageView imageViewGraspingArea;
    private ImageView imageViewLearningArea;
    private ImageView imageViewExposingArea;
    private LinearLayout layoutContent;
    private TextView textViewContent;
    private AppCompatImageView imageViewVernier;
    private WordActionsListener wordActionsListener;
    private TextView textViewGrasped;
    private TextView textViewLearning;
    private TextView textViewExposing;

    private boolean isInExposingArea = false;
    private boolean isInGraspingArea = false;
    private boolean isInLearningArea = false;

    public WordCardView(@NonNull Context context, Word word) {
        super(context);
        this.word = word;

        wordActionsListener = new SimpleWordActionsListener();

        initViews();
    }

    protected void initViews() {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext())
                                                        .inflate(R.layout.view_word_card,
                                                                null);
        this.addView(viewGroup);

        layout = findViewById(R.id.layout);
        buttonDesert = findViewById(R.id.button_desert);
        buttonMark = findViewById(R.id.button_mark);
        imageViewGraspingArea = findViewById(R.id.imageView_grasping_area);
        imageViewLearningArea = findViewById(R.id.imageView_learning_area);
        imageViewExposingArea = findViewById(R.id.imageView_exposing_area);
        layoutContent = findViewById(R.id.layout_content);
        textViewContent = findViewById(R.id.textView_content);
        imageViewVernier = findViewById(R.id.imageView_vernier);
        textViewGrasped = findViewById(R.id.textView_grasped);
        textViewLearning = findViewById(R.id.textView_learning);
        textViewExposing = findViewById(R.id.textView_exposing);

        textViewContent.setText(word.getEn());

        layout.setOnDragListener(this);
        buttonDesert.setOnClickListener(this);
        buttonMark.setOnClickListener(this);

        imageViewVernier.setOnTouchListener(new OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    String tag = v.getId() + "";
                    ClipData.Item item = new ClipData.Item(tag);
                    ClipData dragData = new ClipData(new ClipDescription(tag,
                            new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}), item);
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundColor(Color.RED);
                    View.DragShadowBuilder myShadow = new MyDragShadowBuilder(layoutContent);

                    // Starts the drag
                    layoutContent.startDrag(dragData,  // the data to be dragged
                            myShadow,  // the drag shadow builder
                            null,      // no need to use local data
                            0          // flags (not currently used, set to 0)
                    );
                }
                return true;
            }
        });

        textViewContent.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                wordActionsListener.onLongClickWord(word, textViewContent);
                return true;
            }
        });

        textViewContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                wordActionsListener.onSingleClickWord(word, textViewContent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_desert:
                wordActionsListener.onClickDesertButton(word, textViewContent);
                break;
            case R.id.button_mark:
                wordActionsListener.onClickMarkButton(word, textViewContent);
                break;
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case DragEvent.ACTION_DRAG_LOCATION:
                layoutContent.setX(event.getX() - layoutContent.getWidth() * 0.5f);
                layoutContent.setY(event.getY() - layoutContent.getHeight() * 0.9f);
                break;
            case DragEvent.ACTION_DROP:

                int exposingAreaLimitX = imageViewExposingArea.getWidth();
                int exposingAreaLimitY =
                        layout.getHeight() - imageViewExposingArea.getHeight() +
                                layoutContent.getHeight() / 2 + textViewContent.getHeight();
                if (x < exposingAreaLimitX && y > exposingAreaLimitY && !isInExposingArea) {
                    isInExposingArea = true;
                    wordActionsListener.inExposingArea(word, textViewContent);

                }
                else if (x < exposingAreaLimitX && y < exposingAreaLimitY && isInExposingArea) {
                    isInExposingArea = false;
                    wordActionsListener.outExposingArea(word, textViewContent);
                }

                int graspingAreaLimitX =
                        imageViewGraspingArea.getWidth() - layoutContent.getWidth() / 2;
                int graspingAreaLimitY =
                        imageViewGraspingArea.getHeight() + layoutContent.getHeight() / 2;

                if (!isInGraspingArea && x < graspingAreaLimitX && y < graspingAreaLimitY) {
                    wordActionsListener.atGraspingArea(word, textViewContent);
                }

                int learningAreaLimitX = layout.getWidth() - imageViewLearningArea.getWidth() +
                        layoutContent.getWidth() / 2;
                int learningAreaLimitY =
                        imageViewLearningArea.getHeight() + layoutContent.getHeight() / 2;

                if (!isInLearningArea && x > learningAreaLimitX && y < learningAreaLimitY) {
                    wordActionsListener.atLearningArea(word, textViewContent);
                }


                break;
        }
        return true;
    }

    public Word getWord() {
        return word;
    }

    public TextView getTextViewContent() {
        return textViewContent;
    }

    public void setWordActionsListener(WordActionsListener wordActionsListener) {
        this.wordActionsListener = wordActionsListener;
    }


    public Button getButtonDesert() {
        return buttonDesert;
    }

    public Button getButtonMark() {
        return buttonMark;
    }

    public TextView getTextViewGrasped() {
        return textViewGrasped;
    }

    public TextView getTextViewLearning() {
        return textViewLearning;
    }

    public TextView getTextViewExposing() {
        return textViewExposing;
    }

    //{{{{}}}}}}}}}
    public interface WordActionsListener {
        void inExposingArea(Word word, TextView textView);

        void outExposingArea(Word word, TextView textView);

        void atGraspingArea(Word word, TextView textView);

        void atLearningArea(Word word, TextView textView);

        void onSingleClickWord(Word word, TextView textView);

        void onLongClickWord(Word word, TextView textView);

        void onClickMarkButton(Word word, TextView textView);

        void onClickDesertButton(Word word, TextView textView);

    }

    public class SimpleWordActionsListener implements WordActionsListener {
        @Override
        public void inExposingArea(Word word, TextView textView) {

        }

        @Override
        public void outExposingArea(Word word, TextView textView) {

        }

        @Override
        public void atGraspingArea(Word word, TextView textView) {

        }

        @Override
        public void atLearningArea(Word word, TextView textView) {

        }

        @Override
        public void onSingleClickWord(Word word, TextView textView) {

        }

        @Override
        public void onLongClickWord(Word word, TextView textView) {

        }


        @Override
        public void onClickMarkButton(Word word, TextView textView) {

        }

        @Override
        public void onClickDesertButton(Word word, TextView textView) {

        }

    }

}

class MyDragShadowBuilder extends View.DragShadowBuilder {
    private Drawable shadow;

    MyDragShadowBuilder(View v) {
        super(v);
        shadow = new ColorDrawable(Color.LTGRAY);
    }

    // Defines a callback that sends the drag shadow dimensions and touch point back to the
    // system.
    @Override
    public void onProvideShadowMetrics(Point size, Point touch) {
        // Defines local variables
        int width, height;

        // Sets the width of the shadow to half the width of the original View
        width = getView().getWidth() / 2;

        // Sets the height of the shadow to half the height of the original View
        height = getView().getHeight() / 2;

        // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
        // Canvas that the system will provide. As a result, the drag shadow will fill the
        // Canvas.
        shadow.setBounds(0, 0, width, height);

        // Sets the size parameter's width and height values. These get back to the system
        // through the size parameter.
        size.set(width, height);

        // Sets the touch point's position to be in the middle of the drag shadow
        touch.set(width / 2, height / 2);
    }

    // Defines a callback that draws the drag shadow in a Canvas that the system constructs
    // from the dimensions passed in onProvideShadowMetrics().
    @Override
    public void onDrawShadow(Canvas canvas) {
        // Draws the ColorDrawable in the Canvas passed in from the system.
        shadow.draw(canvas);
    }
}


