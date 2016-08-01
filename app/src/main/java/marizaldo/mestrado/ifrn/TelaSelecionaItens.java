package marizaldo.mestrado.ifrn;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class TelaSelecionaItens extends Activity
{
    private ListView lvItensPreSelecionados;
    private SQLiteDatabase bancoDadosErgos;
    private static final String DATABASE_NAME = "banco_ergos";
    private static final String TB_ITENS = "tb_itens";
    String SQL;
    Button btCadastrarItens;
    Button btNovoItem;
    ArrayList<String> itens;
    ArrayList<String> itensSelecionados = new ArrayList<>();
    TextView mensagem;
    Bundle bundleTelaSelecionaItens = new Bundle();
    Intent intentTelaSelecionaItens = new Intent();
    ArrayList<String> itensSelecionadostelaCalculo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_seleciona_itens);

        lvItensPreSelecionados = (ListView) findViewById(R.id.lv_itens_pre_cadastrados);

        configuraButton();
        iniciaItens();
        configurarJanela();
        btCadastrarItens();
        btNovoItem();
    }

    private void configuraButton()
    {
        btCadastrarItens = (Button) findViewById(R.id.bt_tela_seleciona_itens_cadastra_itens);
        btNovoItem = (Button) findViewById(R.id.bt_tela_seleciona_itens_novo_item);
    }

    private void iniciaItens()
    {
        try
        {
            itens = new ArrayList<>();
            itens.add("TELEVISÃO");
            itens.add("GELADEIRA");
            itens.add("VENTILADOR");
            itens.add("FREEZER");
            itens.add("MICRO-ONDAS");
            itens.add("LÂMPADA");
            itens.add("COMPUTADOR");
            itens.add("APARELHO DE SOM");
            itens.add("LIQUIDIFICADOR");
            itens.add("FERRO DE PASSAR");

            if(getIntent().getExtras() != null)
            {
                intentTelaSelecionaItens = getIntent();
                bundleTelaSelecionaItens = getIntent().getExtras();
                itensSelecionadostelaCalculo = bundleTelaSelecionaItens.getStringArrayList("itensCalculo");
            }

            lvItensPreSelecionados.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, itens));
            lvItensPreSelecionados.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            lvItensPreSelecionados.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    mensagem = (TextView) view.findViewById(android.R.id.text1);
                    if (lvItensPreSelecionados.isItemChecked(position))
                    {
                        itensSelecionados.add(mensagem.getText().toString());
                    }
                    else if (!lvItensPreSelecionados.isItemChecked(position))
                    {
                        itensSelecionados.remove(mensagem.getText().toString());
                    }
                }
            });
        }
        catch (Exception erro)
        {
            toast("(iniciaItens) " +  erro.toString());
        }
    }

    private void configurarJanela()
    {
        WindowManager.LayoutParams parametros = getWindow().getAttributes();
        parametros.width = LinearLayout.LayoutParams.MATCH_PARENT;
        parametros.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(parametros);
    }

    private void btCadastrarItens()
    {

        btCadastrarItens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundleTelaSelecionaItens.putInt("chave", 277);
                bundleTelaSelecionaItens.putStringArrayList("itens", itensSelecionados);
                intentTelaSelecionaItens.putExtras(bundleTelaSelecionaItens);
                setResult(RESULT_OK, intentTelaSelecionaItens);
                finish();
            }
        });
    }

    private void btNovoItem()
    {
        btNovoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentTelaSelecionaItens = new Intent(getApplicationContext(), TelaCadastroNovoItem.class);
                startActivityForResult(intentTelaSelecionaItens, 0);
            }
        });
    }

    private void toast(String mensagem)
    {
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(intent != null)
        {
            Bundle b = intent.getExtras();
            if (resultCode == RESULT_OK)
            {
                if(b.getInt("chave") == 2)
                {
                    bundleTelaSelecionaItens.putString("novoItem", b.getString("novoItem"));
                    bundleTelaSelecionaItens.putInt("chave", b.getInt("chave"));
                    intentTelaSelecionaItens.putExtras(bundleTelaSelecionaItens);
                    setResult(RESULT_OK, intentTelaSelecionaItens);
                    finish();
                }
            }
        }
    }
}
