package marizaldo.mestrado.ifrn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class TelaSelecionaPerfil extends Activity
{
    private SQLiteDatabase bancoDadosErgos;
    private static final String DATABASE_NAME = "banco_ergos";
    private SimpleAdapter simpleAdapter;
    private ListView lvPerfis;
    private Cursor cursor;
    private TextView texto1;
    private String listChoice;
    private Bundle bundleTelaSelecionaPerfil;
    private Intent intentTelaSelecionaPerfil;
    private TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_seleciona_perfil);

        configurarJanela();
        configuraListView();
        carregaPerfis();
    }

    private void carregaPerfis()
    {
        consultaSQL("SELECT name FROM sqlite_master WHERE type='table';");

        ArrayList<String> perfis = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            if(cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    if(!cursor.getString(cursor.getColumnIndex("name")).equals("sqlite_sequence") &&
                            !cursor.getString(cursor.getColumnIndex("name")).equals("tb_perguntas")
                            && !cursor.getString(cursor.getColumnIndex("name")).equals("tb_itens"))
                    {
                        perfis.add(cursor.getString(cursor.getColumnIndex("name")));
                    }
                }
                if(perfis.size() == 0)
                {
                    finish();
                }
            }
        }

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, perfis);

        lvPerfis.setAdapter(itemsAdapter);

        lvPerfis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                texto1 = (TextView) view.findViewById(android.R.id.text1);
                listChoice = texto1.getText().toString();

                bundleTelaSelecionaPerfil = new Bundle();
                intentTelaSelecionaPerfil = new Intent();

                bundleTelaSelecionaPerfil.putString("nomeTabela", listChoice);
                bundleTelaSelecionaPerfil.putInt("chave", 132);
                intentTelaSelecionaPerfil.putExtras(bundleTelaSelecionaPerfil);
                setResult(RESULT_OK, intentTelaSelecionaPerfil);
                finish();
            }
        });

        lvPerfis.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                text1 = (TextView) view.findViewById(android.R.id.text1);
                String nomeTabela = text1.getText().toString();
                mensagemAlerta("Excluir", "Deseja realmente excluir o item?", nomeTabela);
                return true;
            }
        });
    }

    private void configuraListView()
    {
        lvPerfis = (ListView) findViewById(R.id.lv_tela_seleciona_perfis_perfis);
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

    private void mensagemAlerta(String titulo, String mensagem, final String nomeTabela)
    {
        AlertDialog.Builder alerta = new AlertDialog.Builder(TelaSelecionaPerfil.this);
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setIcon(R.drawable.ic_icone_exclamacao);
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                excluirPerfil(nomeTabela);
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

    private void excluirPerfil(String nomeTabela)
    {
        try
        {
            bancoDadosErgos = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            bancoDadosErgos.execSQL("DROP TABLE " + nomeTabela +  ";");
            toast("perfil " + nomeTabela + " excluido");
            vibrar();

        }
        catch (Exception erro)
        {
            toast(erro.toString());
        }
        finally
        {
            carregaPerfis();
            fecharBancoCursor();
        }
    }

    private void toast(String mensagem)
    {
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }

    private void vibrar()
    {
        Vibrator vibrar = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
        long milliseconds = 90;
        vibrar.vibrate(milliseconds);
    }

    private void fecharBancoCursor()
    {
        bancoDadosErgos.close();
        cursor.close();
    }

    private void consultaSQL(String sql)
    {
        bancoDadosErgos = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        cursor = bancoDadosErgos.rawQuery(sql, null);
    }
}
