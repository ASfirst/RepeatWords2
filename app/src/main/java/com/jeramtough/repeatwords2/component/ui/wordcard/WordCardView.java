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
import com.jeramtough.repeatwords2.dao.dto.word.WordDto;

public class WordCardView extends FrameLayout
        implements View.OnDragListener, View.OnClickListener {
    private WordDto wordDto;

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

    public WordCardView(@NonNull Context context, WordDto wordDto) {
        super(context);
        this.wordDto = wordDto;

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

        textViewContent.setText(wordDto.getWord());

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
                wordActionsListener.onLongClickWord(wordDto, textViewContent);
                return true;
            }
        });

        textViewContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                wordActionsListener.onSingleClickWord(wordDto, textViewContent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_desert:
                wordActionsListener.onClickDesertButton(wordDto, textViewContent);
                break;
            case R.id.button_mark:
                wordActionsListener.onClickMarkButton(wordDto, textViewContent);
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
                    wordActionsListener.inExposingArea(wordDto, textViewContent);

                }
                else if (x < exposingAreaLimitX && y < exposingAreaLimitY && isInExposingArea) {
                    isInExposingArea = false;
                    wordActionsListener.outExposingArea(wordDto, textViewContent);
                }

                int graspingAreaLimitX =
                        imageViewGraspingArea.getWidth() - layoutContent.getWidth() / 2;
                int graspingAreaLimitY =
                        imageViewGraspingArea.getHeight() + layoutContent.getHeight() / 2;

                if (!isInGraspingArea && x < graspingAreaLimitX && y < graspingAreaLimitY) {
                    wordActionsListener.atGraspingArea(wordDto, textViewContent);
                }

                int learningAreaLimitX = layout.getWidth() - imageViewLearningArea.getWidth() +
                        layoutContent.getWidth() / 2;
                int learningAreaLimitY =
                        imageViewLearningArea.getHeight() + layoutContent.getHeight() / 2;

                if (!isInLearningArea && x > learningAreaLimitX && y < learningAreaLimitY) {
                    wordActionsListener.atLearningArea(wordDto, textViewContent);
                }


                break;
        }
        return true;
    }

    public WordDto getWordDto() {
        return wordDto;
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
        void inExposingArea(WordDto wordDto, TextView textView);

        void outExposingArea(WordDto wordDto, TextView textView);

        void atGraspingArea(WordDto wordDto, TextView textView);

        void atLearningArea(WordDto wordDto, TextView textView);

        void onSingleClickWord(WordDto wordDto, TextView textView);

        void onLongClickWord(WordDto wordDto, TextView textView);

        void onClickMarkButton(WordDto wordDto, TextView textView);

        void onClickDesertButton(WordDto wordDto, TextView textView);

    }

    public class SimpleWordActionsListener implements WordActionsListener {
        @Override
        public void inExposingArea(WordDto wordDto, TextView textView) {

        }

        @Override
        public void outExposingArea(WordDto wordDto, TextView textView) {

        }

        @Override
        public void atGraspingArea(WordDto wordDto, TextView textView) {

        }

        @Override
        public void atLearningArea(WordDto wordDto, TextView textView) {

        }

        @Override
        public void onSingleClickWord(WordDto wordDto, TextView textView) {

        }

        @Override
        public void onLongClickWord(WordDto wordDto, TextView textView) {

        }


        @Override
        public void onClickMarkButton(WordDto wordDto, TextView textView) {

        }

        @Override
        public void onClickDesertButton(WordDto wordDto, TextView textView) {

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


