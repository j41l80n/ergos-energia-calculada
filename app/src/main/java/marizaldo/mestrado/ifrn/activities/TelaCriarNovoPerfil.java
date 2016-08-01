package marizaldo.mestrado.ifrn.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import marizaldo.mestrado.ifrn.R;

public class TelaCriarNovoPerfil extends Activity
{
    private SQLiteDatabase bancoDadosErgos;
    private static final String DATABASE_NAME = "banco_ergos";
    EditText et;
    Button btSalvarPerfil;
    Intent intentTelaSalvarPerfil;
    Bundle bundleTelaSalvarPerfil;
    Button btCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_salvar_perfil);

        configuraEditText();
        configuraButton();
        btSalvarNovoPerfil();
        etNomePerfil();
        configurarJanela();
        btCancelar();
    }

    private void btSalvarNovoPerfil()
    {
        btSalvarPerfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String verificaCampo = et.getText().toString();
                if (verificaCampo.trim().isEmpty())
                {
                    et.setError("Campo vazio");
                    et.requestFocus();
                }
                else
                {
                    criarNovoPerfil();
                    Toast.makeText(getApplicationContext(), "Perfil salvo!", Toast.LENGTH_SHORT).show();
                    bundleTelaSalvarPerfil = new Bundle();
                    intentTelaSalvarPerfil = new Intent();
                    bundleTelaSalvarPerfil.putInt("chave", 666);
                    bundleTelaSalvarPerfil.putString("nomeTabela", et.getText().toString().trim().replace(' ','_'));
                    intentTelaSalvarPerfil.putExtras(bundleTelaSalvarPerfil);
                    setResult(RESULT_OK, intentTelaSalvarPerfil);
                    finish();
                }
            }
        });
    }

    private void btCancelar()
    {
        btCancelar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void configuraEditText()
    {
        et = (EditText)findViewById(R.id.et_tela_salvar_perfil);
    }

    private void configuraButton()
    {
        btSalvarPerfil = (Button) findViewById(R.id.bt_tela_salvar_perfil);
        btCancelar = (Button) findViewById(R.id.bt_tela_salvar_cancelar);
    }

    private void criarNovoPerfil()
    {
        try
        {
            bancoDadosErgos = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            String SQL = "CREATE TABLE IF NOT EXISTS " + et.getText().toString().trim().replace(' ','_') + " (codigo_item INTEGER NOT NULL PRIMARY KEY, nome_item VARCHAR(100), energia INTEGER, custo INTEGER)";
            bancoDadosErgos.execSQL(SQL);
        }
        catch (Exception erro)
        {
            mensagemAlerta("(criarNovoPerfil)", erro.toString());
        }
        finally
        {
            bancoDadosErgos.close();
        }
    }

    public void mensagemAlerta(String titulo, String mensagem)
    {
        AlertDialog.Builder alerta = new AlertDialog.Builder(getApplicationContext());
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

    private void etNomePerfil()
    {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et.getText().toString().equals("")) {
                    et.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
    }

    private void configurarJanela()
    {
        WindowManager.LayoutParams parametros = getWindow().getAttributes();
        parametros.width = LinearLayout.LayoutParams.MATCH_PARENT;
        parametros.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(parametros);
    }
}
