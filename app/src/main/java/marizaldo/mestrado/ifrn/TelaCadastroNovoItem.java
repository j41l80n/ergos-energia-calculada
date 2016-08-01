package marizaldo.mestrado.ifrn;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TelaCadastroNovoItem extends Activity
{
    private Intent intentTelaCadastroNovoItem;
    private Button btSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_novo_item);
        setTitle("Cadastre um novo item");

        intentTelaCadastroNovoItem = getIntent();
        btSalvar = (Button) findViewById(R.id.botao_salvar_item);

        configurarJanela();
        salvarNovoItem();
    }

    private void salvarNovoItem()
    {
        btSalvar.setOnClickListener(new View.OnClickListener() {
            EditText et_novo_item = (EditText) findViewById(R.id.et_novo_item);

            @Override
            public void onClick(View v)
            {
                if (et_novo_item.getText().toString().trim().equals("") || et_novo_item.getText()
                        .toString().trim().equals(null))
                {
                    et_novo_item.setError("Campo vazio");
                }
                else
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("novoItem", et_novo_item.getText().toString().trim().toUpperCase().replace('\'', ' '));
                    bundle.putInt("chave", 2);
                    intentTelaCadastroNovoItem.putExtras(bundle);
                    setResult(RESULT_OK, intentTelaCadastroNovoItem);
                    finish();
                }
            }
        });
    }

    private void configurarJanela()
    {
        WindowManager.LayoutParams parametros = getWindow().getAttributes();
        parametros.width = LinearLayout.LayoutParams.MATCH_PARENT;
        parametros.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(parametros);
    }

    private void toast(String mensagem)
    {
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }
}
