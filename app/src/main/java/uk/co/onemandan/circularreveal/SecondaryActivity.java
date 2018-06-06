package uk.co.onemandan.circularreveal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import uk.co.onemandan.circularreveal.Helpers.AnimUtils;

public class SecondaryActivity extends AppCompatActivity {

    private View _rootLayout;
    private View _overlay;

    private int _revealX;
    private int _revealY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        Intent intent = getIntent();
        _rootLayout   = findViewById(R.id.rl_root);
        _overlay      = findViewById(R.id.v_overlay);

        if (savedInstanceState == null && intent.hasExtra("CIRCULAR_REVEAL_X") &&
                intent.hasExtra("CIRCULAR_REVEAL_Y")) {

            _rootLayout.setVisibility(View.INVISIBLE);
            _revealX = intent.getIntExtra("CIRCULAR_REVEAL_X", 0);
            _revealY = intent.getIntExtra("CIRCULAR_REVEAL_Y", 0);

            ViewTreeObserver viewTreeObserver = _rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        AnimUtils.animCircularReveal(_rootLayout, _overlay, _revealX, _revealY);
                        _rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            _rootLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        AnimUtils.animCircularUnreveal(_rootLayout, _overlay, _revealX, _revealY, new AnimUtils.AnimationListener() {
            @Override
            public void onAnimationEnd() {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
