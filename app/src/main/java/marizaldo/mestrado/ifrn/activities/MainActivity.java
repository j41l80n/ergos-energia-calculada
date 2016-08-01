package marizaldo.mestrado.ifrn.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import marizaldo.mestrado.ifrn.R;

public class MainActivity extends Activity
{
    private static final String DATABASE_NAME = "banco_ergos";
    private Button btTelaCalculo;
    private Button btConhecaSuaConta;
    private Button btQuiz;
    private Button btTelaManual;
    private Intent intentMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configuraButtons();
        chamaTelaCalculo();
        chamaTelaConhecaSuaConta();
        chamaTelaQuiz();
        chamaTelaManual();
        criarBanco();
        vibrar();
    }

    private void configuraButtons()
    {
        btTelaCalculo = (Button) findViewById(R.id.main_activity_bt_tela_calculo);
        btConhecaSuaConta = (Button) findViewById(R.id.main_activity_bt_conheca_sua_conta);
        btQuiz = (Button) findViewById(R.id.main_activity_bt_quiz);
        btTelaManual = (Button) findViewById(R.id.main_activity_bt_tela_manual);
    }

    private void chamaTelaCalculo()
    {
        btTelaCalculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentMainActivity = new Intent(getApplicationContext(), TelaCalculo.class);
                startActivity(intentMainActivity);
            }
        });
    }

    private void chamaTelaConhecaSuaConta()
    {
        btConhecaSuaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentMainActivity = new Intent(getApplicationContext(), TelaConhecaSuaConta.class);
                startActivity(intentMainActivity);
            }
        });
    }

    private void chamaTelaQuiz()
    {
        btQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentMainActivity = new Intent(getApplicationContext(), TelaQuiz.class);
                startActivity(intentMainActivity);
            }
        });
    }

    private void chamaTelaManual()
    {
        btTelaManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CopyReadAssets();
            }
        });
    }

    private void mensagemAlerta(String titulo, String mensagem)
    {
        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setIcon(R.drawable.ic_icone_exclamacao);
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }
        });
        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        alerta.show();
    }

    private void criarBanco()
    {
        try
        {
            openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        }
        catch (Exception erro)
        {
            mensagemAlerta("ERRO Banco de Dados", erro.toString());
        }
    }

    private void vibrar()
    {
        Vibrator vibrar = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long milliseconds = 50;
        vibrar.vibrate(milliseconds);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        //mensagemAlerta("Sair", "Deseja realmente sair da aplicação?");
    }

    private void CopyReadAssets()
    {
        AssetManager assetManager = getAssets();
        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), "manual.pdf");
        try
        {
            in = assetManager.open("manual.pdf");
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        }
        catch (Exception e)
        {
            Log.e("tag", e.getMessage());
            Log.e(getFilesDir() + "", e.getMessage());
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + getFilesDir() + "/manual.pdf"), "application/pdf");
        startActivity(intent);
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }
}