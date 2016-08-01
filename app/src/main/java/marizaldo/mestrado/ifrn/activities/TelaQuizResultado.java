package marizaldo.mestrado.ifrn.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import marizaldo.mestrado.ifrn.R;

public class TelaQuizResultado extends Activity
{
    private Bundle bundleTelaQuizResultado;
    private Intent intentTelaQuizResultado;
    private Button btTerminarJogo;
    private TextView tvAcertos;
    private TextView tvErros;
    private TextView tvPulos;
    private int erros;
    private int acertos;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_quiz_resultado);

        intentTelaQuizResultado = getIntent();
        bundleTelaQuizResultado = getIntent().getExtras();

        configurarJanela();
        configuraButton();
        btTerminarjogo();
        configuraTextView();
    }

    private void configuraTextView()
    {
        tvAcertos = (TextView) findViewById(R.id.tv_tela_quiz_resultado_acertos);
        tvErros = (TextView) findViewById(R.id.tv_tela_quiz_resultado_erros);
        tvPulos = (TextView) findViewById(R.id.tv_tela_quiz_resultado_pulos);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/days_lattes_28.ttf");
        tvAcertos.setTypeface(font);
        tvErros.setTypeface(font);
        tvPulos.setTypeface(font);

        tvAcertos.setText("Acertos: " + bundleTelaQuizResultado.getInt("acertos"));
        tvErros.setText("Erros: " + bundleTelaQuizResultado.getInt("erros"));
        tvPulos.setText("Pulos utilizados: " + bundleTelaQuizResultado.getInt("pulos"));
    }

    private void configuraButton()
    {
        btTerminarJogo = (Button) findViewById(R.id.bt_tela_quiz_resultado_terminar_jogo);
    }

    private void configurarJanela()
    {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
    }

    @Override
    public void onBackPressed()
    {
        //
    }

    private void btTerminarjogo()
    {
        btTerminarJogo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bundleTelaQuizResultado = new Bundle();
                bundleTelaQuizResultado.putInt("chave", 1);
                intentTelaQuizResultado = new Intent();
                intentTelaQuizResultado.putExtras(bundleTelaQuizResultado);
                setResult(RESULT_OK, intentTelaQuizResultado);
                finish();
            }
        });
    }
}
