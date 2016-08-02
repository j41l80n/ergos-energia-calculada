package marizaldo.mestrado.ifrn.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;

import marizaldo.mestrado.ifrn.R;

public class TelaSplash extends Activity implements Runnable
{
    private Animation zoom_out;
    private ImageView iv;

    public void onCreate(Bundle savedInstanceState)
    {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_splash);

        Handler handler = new Handler();
        handler.postDelayed(this, 3100);

        //iv = (ImageView) findViewById(R.id.iv_animacao);
        //zoom_out = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
        //zoom_out.setAnimationListener(this);
        //iv.startAnimation(zoom_out);
    }

    public void run()
    {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}