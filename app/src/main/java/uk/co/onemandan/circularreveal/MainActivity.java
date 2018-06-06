package uk.co.onemandan.circularreveal;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import uk.co.onemandan.circularreveal.Helpers.AnimUtils;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton _fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _fab = findViewById(R.id.fab_add);

        _fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createActivity(v);
            }
        });
    }

    private void createActivity(View view){
        //Animation to create a fade
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                view, "transition");

        //Get location of the Floating Action Button and pass via Intent
        Intent intent = new Intent(this, SecondaryActivity.class);
        intent.putExtra("CIRCULAR_REVEAL_X", (int)(view.getX() + (view.getWidth() / 2)));
        intent.putExtra("CIRCULAR_REVEAL_Y", (int)(view.getY() + (view.getHeight() / 2)));

        startActivityForResult(intent, 1, options.toBundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        AnimUtils.animScaleUp(this, _fab);

        super.onActivityResult(requestCode, resultCode, data);
    }
}
