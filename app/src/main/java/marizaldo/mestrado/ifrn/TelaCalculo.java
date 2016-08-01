package marizaldo.mestrado.ifrn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;


public class TelaCalculo extends ActionBarActivity implements Serializable
{
    private Toolbar toolbar;
    private SQLiteDatabase bancoDadosErgos = null;
    private static final String DATABASE_NAME = "banco_ergos";
    private static final String TB_ITENS = "tb_itens";
    private Double consumoKwhDiario = 0.0;
    private Double consumoKwhMensal = 0.0;
    private Double diminuir = 0.0;
    private Double diminuir2 = 0.0;
    private Double diminuir3 = 0.0;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, String>> arrayMap = new ArrayList<>();
    private Map<String, String> mapString = new HashMap<>();
    private ListView lvTelaCalculoItens;
    private Cursor cursor;
    private String listChoice;
    private TextView text1;
    private TextView text2;
    private View minhaView;
    private TextView tvTelaCalculoKWhMensalResultado;
    private TextView tvTelaCalculoKWhDiarioResultado;
    private TextView tvExtra;
    private TextView tvTelaCalculoKHwDiario;
    private TextView tvTelaCalculoKWhMensal;
    private TextView tvTelaCalculoCustoTotalMensalCalculado;
    private TableLayout tlTelaCalculoConsumosCusto;
    private TableLayout tlTelaCalculoConsumosCustoExtra;
    private TextView tvTelaCalculoCustoTotalMensal;
    private Context context;
    private Bundle bundleTelaCalculo;
    private Intent intentTelaCalculo;
    private EditText etTelaCalculoValorKWh;
    private String nomeTabela;
    ArrayList<String> itensSelecionados = new ArrayList<>();
    Double novoValor = 0.0;
    DecimalFormat decimal = new DecimalFormat("#,##0.000");
    DecimalFormat decimal2 = new DecimalFormat("#,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_calculo);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        configuraTableLayout();
        configuraListView();
        configuraEditText();
        configuraTextViews();
        calculaGastoDiarioMensal();
        verificaPerfis();
    }

    private void configuraTableLayout()
    {
        tlTelaCalculoConsumosCusto = (TableLayout) findViewById(R.id.tl_tela_calculo_consumos_custo);
        tlTelaCalculoConsumosCusto.setVisibility(View.GONE);

        tlTelaCalculoConsumosCustoExtra = (TableLayout) findViewById(R.id.tl_tela_calculo_consumos_custo_extra);
        tlTelaCalculoConsumosCusto.setVisibility(View.GONE);
    }

    private void verificaPerfis()
    {
        try
        {
            consultaSQL("SELECT name FROM sqlite_master WHERE type='table';");

            ArrayList<String> perfis = new ArrayList<String>();

            if (cursor.moveToFirst())
            {
                while(cursor.moveToNext())
                {
                    if(!cursor.getString(cursor.getColumnIndex("name")).equals("sqlite_sequence") &&
                            !cursor.getString(cursor.getColumnIndex("name")).equals("tb_perguntas")
                            && !cursor.getString(cursor.getColumnIndex("name")).equals("tb_itens"))
                    {
                        perfis.add(cursor.getString(cursor.getColumnIndex("name")));
                    }
                }
            }

            if(perfis.size() > 0)
            {
                modoInicio();
            }
            else
            {
                toast("Crie um novo perfil!");
            }
        }
        catch (Exception erro)
        {
            mensagemAlerta("Banco de Dados (verificaPerfis)", erro.toString());
        }
        finally
        {
            bancoDadosErgos.close();
        }
    }

    private void configuraListView()
    {
        lvTelaCalculoItens = (ListView) findViewById(R.id.lv_tela_calculo_itens);
    }

    private void configuraEditText()
    {
        etTelaCalculoValorKWh = (EditText) findViewById(R.id.et_tela_calculo_inserir_custo_kwh);
    }

    private void configuraTextViews()
    {
        tvTelaCalculoKHwDiario = (TextView) findViewById(R.id.tv_tela_calculo_kwh_diario);
        tvTelaCalculoKHwDiario.setTextColor(getResources().getColor(R.color.branco));
        tvTelaCalculoKHwDiario.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));

        tvTelaCalculoKWhDiarioResultado = (TextView) findViewById(R.id.tv_tela_calculo_kwh_diario_resultado);
        tvTelaCalculoKWhDiarioResultado.setTextColor(getResources().getColor(R.color.branco));
        tvTelaCalculoKWhDiarioResultado.setShadowLayer(3, 0, 0, getResources().getColor(R.color.amarelo));

        tvTelaCalculoKWhMensal = (TextView) findViewById(R.id.tv_tela_calculo_kwh_mensal);
        tvTelaCalculoKWhMensal.setTextColor(getResources().getColor(R.color.branco));
        tvTelaCalculoKWhMensal.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));

        tvTelaCalculoKWhMensalResultado = (TextView) findViewById(R.id.tv_tela_calculo_kwh_mensal_resultado);
        tvTelaCalculoKWhMensalResultado.setTextColor(getResources().getColor(R.color.branco));
        tvTelaCalculoKWhMensalResultado.setShadowLayer(3, 0, 0, getResources().getColor(R.color.laranja));

        tvTelaCalculoCustoTotalMensal = (TextView) findViewById(R.id.tv_tela_calculo_custo_total_mensal);
        tvTelaCalculoCustoTotalMensal.setTextColor(getResources().getColor(R.color.branco));
        tvTelaCalculoCustoTotalMensal.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));

        tvTelaCalculoCustoTotalMensalCalculado = (TextView) findViewById(R.id.tv_tela_calculo_custo_total_mensal_calculado);
        tvTelaCalculoCustoTotalMensalCalculado.setTextColor(getResources().getColor(R.color.branco));
        tvTelaCalculoCustoTotalMensalCalculado.setShadowLayer(3, 0, 0, getResources().getColor(R.color.verde));

        tvExtra = (TextView) findViewById(R.id.tv_extra);
        tvExtra.setTextColor(getResources().getColor(R.color.branco));
        tvExtra.setShadowLayer(3, 0, 0, getResources().getColor(R.color.verde));

        tvTelaCalculoCustoTotalMensalCalculado.setTextSize(15);
    }

    private void iniciaListview()
    {
        arrayMap.clear();
        bancoDadosErgos = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        String sql = "SELECT * FROM " + nomeTabela + ";";
        cursor = bancoDadosErgos.rawQuery(sql, null);
        DecimalFormat form = new DecimalFormat("0.000");
        itensSelecionados = new ArrayList<>();
        try
        {
            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    mapString = new HashMap<>();
                    mapString.put("nomeItem", cursor.getString(cursor.getColumnIndex("nome_item"))
                            .toUpperCase());

                    itensSelecionados.add(cursor.getString(cursor.getColumnIndex("nome_item"))
                            .toUpperCase());

                    if(cursor.getDouble(cursor.getColumnIndex("energia")) > 0.000)
                    {
                        mapString.put("valorItem", "kWh(D): " + decimal.format(cursor.getDouble(
                                cursor.getColumnIndex("energia"))) + "   kWh(M): " + decimal.format(
                                (cursor.getDouble(cursor.getColumnIndex("energia"))) * 30));
                        consumoKwhDiario += cursor.getDouble(cursor.getColumnIndex("energia"));
                    }
                    else
                    {
                        mapString.put("valorItem", "clique para inserir potência e tempo");
                    }
                    arrayMap.add(mapString);
                }
                consumoKwhMensal = consumoKwhDiario * 30;
                tlTelaCalculoConsumosCusto.setVisibility(View.VISIBLE);
            }
            else
            {
                toast("Cadastre itens");
            }
        }
        catch (Exception erro)
        {
            mensagemAlerta("Banco de Dados (carregadDados2)", erro.toString());
        }

        tvTelaCalculoKWhDiarioResultado.setText(decimal.format(this.consumoKwhDiario));
        tvTelaCalculoKWhMensalResultado.setText(decimal.format(this.consumoKwhMensal));

        String[] from = { "nomeItem", "valorItem" };
        int[] to = { R.id.tv_listview_personalizado1, R.id.tv_listview_2};

        simpleAdapter = new SimpleAdapter(this, arrayMap, R.layout.listview_personalizado, from, to)
        {
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View view = super.getView(position, convertView, parent);
                text1 = (TextView) view.findViewById(R.id.tv_listview_personalizado1);
                text2 = (TextView) view.findViewById(R.id.tv_listview_2);

                text1.setTextColor(getResources().getColor(R.color.preto));
                text2.setTextColor(getResources().getColor(R.color.preto));
                text2.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));

                minhaView = super.getView(position, convertView, parent);
                return view;
            }
        };

        lvTelaCalculoItens.setAdapter(simpleAdapter);

        lvTelaCalculoItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int posicao, long l) {
                text1 = (TextView) view.findViewById(R.id.tv_listview_personalizado1);
                text2 = (TextView) view.findViewById(R.id.tv_listview_2);
                listChoice = text1.getText().toString();

                if (lvTelaCalculoItens.isItemChecked(posicao) && text2.getText().toString()
                        .equals("clique para inserir potência e tempo")) {
                    chamaTelaCampos(listChoice, posicao);
                } else if (!lvTelaCalculoItens.isItemChecked(posicao) && !text2.getText()
                        .toString().equals("clique para inserir potência e tempo")) {
                    lvTelaCalculoItens.setItemChecked(posicao, false);
                    atualizaCampos(listChoice);
                }
                //else if (!lvTelaCalculoItens.isItemChecked(posicao))
                //{
                ////    lvTelaCalculoItens.setItemChecked(posicao, false);
                //    atualizaCampos(listChoice);
                //}
            }
        });

        lvTelaCalculoItens.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                text1 = (TextView) view.findViewById(R.id.tv_listview_personalizado1);
                text2 = (TextView) view.findViewById(R.id.tv_listview_2);
                String listChoice = text1.getText().toString();
                mensagemAlerta("Excluir", "Deseja realmente excluir o item?", listChoice, position);
                return true;
            }
        });
    }

    private void atualizaCampos(String nomeItem)
    {
        bancoDadosErgos = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        String SQL = "SELECT * FROM " + nomeTabela + " WHERE nome_item = '" + nomeItem + "';";
        cursor = bancoDadosErgos.rawQuery(SQL, null);

        try
        {
            if(cursor.moveToFirst())
            {
                DecimalFormat form = new DecimalFormat("000.000");
                DecimalFormat decimal2 = new DecimalFormat("#,##0.000");

                diminuir = Double.parseDouble(cursor.getString(cursor.getColumnIndex("energia")).replace(',', '.'));
                diminuir2 = Double.parseDouble(cursor.getString(cursor.getColumnIndex("custo")).replace(',', '.'));
                diminuir3 = Double.parseDouble(form.format(diminuir * 30).replace(',', '.'));

                Double valor_existente = Double.parseDouble(tvTelaCalculoKWhDiarioResultado.getText().toString().replace(".", "").replace(",", "."));
                Double valor_existente3 = Double.parseDouble(tvTelaCalculoKWhMensalResultado.getText().toString().replace(".", "").replace(",", "."));

                Double consumoKwhDiario = (valor_existente - diminuir);
                Double custoMensal = (valor_existente3 - diminuir3);

                this.consumoKwhDiario = consumoKwhDiario;
                this.consumoKwhMensal = custoMensal;

                tvTelaCalculoKWhDiarioResultado.setText(decimal2.format(this.consumoKwhDiario));
                tvTelaCalculoKWhMensalResultado.setText(decimal2.format(this.consumoKwhMensal));

                if(!tvTelaCalculoCustoTotalMensalCalculado.getText().toString().equals("clique para inserir o custo kWh"))
                {
                    DecimalFormat decimal = new DecimalFormat("#,##0.00");
                    Double valor = Double.parseDouble(tvTelaCalculoKWhMensalResultado.getText().toString().replace(',', '.'));
                    novoValor = valor * Double.parseDouble(etTelaCalculoValorKWh.getText().toString().trim());

                    tvTelaCalculoCustoTotalMensalCalculado.setVisibility(View.VISIBLE);
                    tvTelaCalculoCustoTotalMensalCalculado.setText(decimal.format(novoValor));
                    tvTelaCalculoCustoTotalMensalCalculado.setTextColor(getResources().getColor(R.color.preto));
                    tvTelaCalculoCustoTotalMensalCalculado.setShadowLayer(3, 0, 0, getResources().getColor(R.color.azul_escuro));
                }

                if(consumoKwhDiario <= 0.000)
                {
                    tlTelaCalculoConsumosCusto.setVisibility(View.GONE);
                    novoValor = 0.0;
                    tvTelaCalculoCustoTotalMensalCalculado.setText("clique para inserir o custo kWh");
                }
                else
                {
                    tlTelaCalculoConsumosCusto.setVisibility(View.VISIBLE);
                }

                String SQL1 = "UPDATE " + nomeTabela + " SET energia = " + 0 +", custo = " + 0 + " WHERE nome_item = '" + nomeItem + "';";
                bancoDadosErgos.execSQL(SQL1);
            }
        }
        catch(Exception erro)
        {
            toast("(atualizaCampos)" + erro.toString());
        }
        finally
        {
            preencheListview();
            bancoDadosErgos.close();
            cursor.close();
        }
    }

    private void excluirItem(String item)
    {
        try
        {
            atualizaCampos(item);
            bancoDadosErgos = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            String sql = "DELETE FROM " + nomeTabela +" WHERE nome_item = '" + item + "'; ";
            bancoDadosErgos.execSQL(sql);
            toast("Item excluído");
            vibrar(90);
        }
        catch (Exception erro)
        {
            mensagemAlerta("Banco de dados (excluirItem)", erro.toString());
        }
        finally
        {
            bancoDadosErgos.close();
        }
    }

    private void mensagemAlerta(String titulo, String mensagem, final String item, final int posicao)
    {
        AlertDialog.Builder alerta = new AlertDialog.Builder(TelaCalculo.this);
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setIcon(R.drawable.ic_icone_exclamacao);
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                excluirItem(item);
                arrayMap.remove(posicao);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta.show();
    }

    private void mensagemAlerta(String titulo, String mensagem)
    {
        AlertDialog.Builder alerta = new AlertDialog.Builder(TelaCalculo.this);
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setNeutralButton("OK", null);
        alerta.show();
    }

    private void vibrar(long milisegundos)
    {
        Vibrator vibrar = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
        vibrar.vibrate(milisegundos);
    }

    private void chamaTelaCampos(String codigo, int posicao)
    {
        bundleTelaCalculo = new Bundle();
        bundleTelaCalculo.putString("nomeItem", codigo);
        bundleTelaCalculo.putInt("posicaoItem", posicao);

        intentTelaCalculo = new Intent(getApplicationContext(), TelaCampos.class);
        intentTelaCalculo.putExtras(bundleTelaCalculo);
        startActivityForResult(intentTelaCalculo, 1);
    }

    private void gravarBanco(String item)
    {
        try
        {
            bancoDadosErgos = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            String SQL = "INSERT INTO " + nomeTabela + " (nome_item, energia, custo) VALUES('" + item + "', " + 0 + ", " + 0 + ");";
            bancoDadosErgos.execSQL(SQL);
            //toast("Registro gravado com sucesso");
        }
        catch (Exception erro)
        {
            mensagemAlerta("Banco de Dados (gravaBanco)", erro.toString());
        }
        finally
        {
            bancoDadosErgos.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(intent != null)
        {
            Bundle b = intent.getExtras();
            int posicao = b.getInt("posicao");
            Double auxiliar = b.getDouble("auxiliar");
            Double total_kwh_mensal = b.getDouble("total_kwh_mensal");
            String codigo = b.getString("codigo");

            itensSelecionados = b.getStringArrayList("itens");

            if (resultCode == RESULT_CANCELED)
            {
                lvTelaCalculoItens.setItemChecked(posicao, false);
            }
            else if(resultCode == RESULT_OK)
            {
                consumoKwhDiario += auxiliar;
                consumoKwhMensal += total_kwh_mensal;

                if(b.getInt("chave") == 132)
                {
                    nomeTabela = b.getString("nomeTabela");
                    tvTelaCalculoKWhDiarioResultado.setText("");
                    tvTelaCalculoKWhMensalResultado.setText("");
                    consumoKwhDiario = 0.0;
                    consumoKwhMensal = 0.0;
                    tlTelaCalculoConsumosCusto.setVisibility(View.GONE);
                    getSupportActionBar().setTitle("Perfil: " + nomeTabela);
                    iniciaListview();
                }
                else if(b.getInt("chave") == 666)
                {
                    nomeTabela = b.getString("nomeTabela");
                    getSupportActionBar().setTitle("Perfil: " + nomeTabela);
                    tvTelaCalculoKWhDiarioResultado.setText("");
                    tvTelaCalculoKWhMensalResultado.setText("");
                    consumoKwhDiario = 0.0;
                    consumoKwhMensal = 0.0;
                    tvTelaCalculoCustoTotalMensalCalculado.setText("");
                    tlTelaCalculoConsumosCusto.setVisibility(View.GONE);
                    iniciaListview();
                }
                else if (b.getInt("chave") == 1)
                {
                    try
                    {
                        bancoDadosErgos = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
                        String SQL = "UPDATE " + nomeTabela + " SET energia = " + auxiliar + " WHERE nome_item = '" + codigo + "'; ";
                        bancoDadosErgos.execSQL(SQL);

                        tvTelaCalculoKWhDiarioResultado.setText(decimal.format(consumoKwhDiario));
                        tvTelaCalculoKWhMensalResultado.setText(decimal.format(consumoKwhMensal));

                        DecimalFormat decimal2 = new DecimalFormat("#,##0.00");

                        if(!etTelaCalculoValorKWh.getText().toString().equals(""))
                        {
                            Double valor = Double.parseDouble(tvTelaCalculoKWhMensalResultado.getText().toString().replace(',', '.'));
                            novoValor = valor * Double.parseDouble(etTelaCalculoValorKWh.getText().toString().trim());

                            tvTelaCalculoCustoTotalMensalCalculado.setVisibility(View.VISIBLE);
                            tvTelaCalculoCustoTotalMensalCalculado.setText(decimal2.format(novoValor));
                            tvTelaCalculoCustoTotalMensalCalculado.setTextColor(getResources().getColor(R.color.preto));
                            tvTelaCalculoCustoTotalMensalCalculado.setShadowLayer(3, 0, 0, getResources().getColor(R.color.azul_escuro));
                        }

                        preencheListview();
                    }
                    catch (Exception e)
                    {
                        mensagemAlerta("(onActivityResult)", e.toString());
                    }
                    finally
                    {
                        bancoDadosErgos.close();
                    }
                }
                else if(b.getInt("chave") == 2)
                {
                    gravarBanco(b.getString("novoItem"));
                    mapString = new HashMap<>();
                    mapString.put("nomeItem", b.getString("novoItem"));
                    mapString.put("valorItem", "clique para inserir potência e tempo");
                    arrayMap.add(mapString);
                    tvTelaCalculoCustoTotalMensalCalculado.setText("");
                    simpleAdapter.notifyDataSetChanged();
                }
                else if(b.getInt("chave") == 277)
                {
                    for(int i = 0; i < itensSelecionados.size(); i++)
                    {
                        gravarBanco(itensSelecionados.get(i));
                        mapString = new HashMap<>();
                        mapString.put("nomeItem", itensSelecionados.get(i));
                        mapString.put("valorItem", "clique para inserir potência e tempo");
                        arrayMap.add(mapString);
                        simpleAdapter.notifyDataSetChanged();
                    }
                }
                else if (b.getInt("chave") == 3)
                {
                    lvTelaCalculoItens.setItemChecked(posicao, false);
                }
                else
                {
                    toast("Ops! Algo deu errado");
                }
            }
        }

        if(consumoKwhDiario <= 0.000)
        {
            tlTelaCalculoConsumosCusto.setVisibility(View.GONE);
        }
        else
        {
            tlTelaCalculoConsumosCusto.setVisibility(View.VISIBLE);
        }
    }

    private void preencheListview()
    {
        arrayMap.clear();
        consultaSQL("SELECT * FROM " + nomeTabela);

        try
        {
            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    mapString = new HashMap<>();
                    mapString.put("nomeItem", cursor.getString(cursor.getColumnIndex
                            ("nome_item")).toUpperCase());

                    if(cursor.getDouble(cursor.getColumnIndex("energia")) > 0.000)
                    {
                        mapString.put("valorItem", "kWh(D): " + decimal.format(cursor.getDouble
                                (cursor.getColumnIndex("energia"))) + "   kWh(M): " +
                                decimal.format((cursor.getDouble(cursor.getColumnIndex("energia"))) * 30));
                    }
                    else if(cursor.getInt(cursor.getColumnIndex("energia")) <= 0.000)
                    {
                       mapString.put("valorItem", "clique para inserir potência e tempo");
                    }
                    arrayMap.add(mapString);
                }
                simpleAdapter.notifyDataSetChanged();
            }
            else
            {
                toast("Cadastre itens");
            }
        }
        catch (Exception erro)
        {
            toast("(preencheListview) " + erro.toString());
        }
        finally
        {
            fecharBancoCursor();
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        ArrayList<String> perfis = new ArrayList<>();
        consultaSQL("SELECT name FROM sqlite_master WHERE type='table';");

        if(id == R.id.adicionar_novo_item)
        {
            if (cursor.moveToFirst())
            {
                while(cursor.moveToNext())
                {
                    if(!cursor.getString(cursor.getColumnIndex("name")).equals("sqlite_sequence") &&
                            !cursor.getString(cursor.getColumnIndex("name")).equals("tb_perguntas")
                            && !cursor.getString(cursor.getColumnIndex("name")).equals("tb_itens"))
                    {
                        perfis.add(cursor.getString(cursor.getColumnIndex("name")));
                    }
                }
            }
            if(perfis.size() > 0 && !getSupportActionBar().getTitle().equals(""))
            {
                intentTelaCalculo = new Intent(getApplicationContext(), TelaSelecionaItens.class);
                startActivityForResult(intentTelaCalculo, 0);
            }
            else
            {
                toast("Antes de iniciar, crie um ou carregue um perfil!");
                bancoDadosErgos.close();
            }
            return true;
        }
        else if(id == R.id.criar_novo_perfil)
        {
            Intent intent = new Intent(getApplicationContext(), TelaCriarNovoPerfil.class);
            startActivityForResult(intent, 94);
            return true;
        }
        else if(id == R.id.carregar_perfil)
        {
            intentTelaCalculo = new Intent(getApplicationContext(), TelaSelecionaPerfil.class);
            startActivityForResult(intentTelaCalculo, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    private void consultaSQL(String sql)
    {
        bancoDadosErgos = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        cursor = bancoDadosErgos.rawQuery(sql, null);
    }

    private void fecharBancoCursor()
    {
        bancoDadosErgos.close();
        cursor.close();
    }

    public boolean dispatchKeyEvent(KeyEvent event)
    {
        final int keycode = event.getKeyCode();
        final int action = event.getAction();
        if (keycode == KeyEvent.KEYCODE_MENU && action == KeyEvent.ACTION_UP) {
            return true; // consume the key press
        }
        return super.dispatchKeyEvent(event);
    }

    private void calculaGastoDiarioMensal()
    {
        tvTelaCalculoCustoTotalMensalCalculado.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tvTelaCalculoCustoTotalMensalCalculado.setVisibility(View.GONE);
                etTelaCalculoValorKWh.setVisibility(View.VISIBLE);
                tvExtra.setVisibility(View.VISIBLE);
                etTelaCalculoValorKWh.setBackgroundColor(getResources().getColor(R.color.laranja));
            }
        });

        etTelaCalculoValorKWh.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (!etTelaCalculoValorKWh.getText().toString().trim().equals(""))
                {
                    DecimalFormat decimal = new DecimalFormat("#,##0.00");
                    Double valor = Double.parseDouble(tvTelaCalculoKWhMensalResultado.getText().toString().replace(',', '.'));
                    novoValor = valor * Double.parseDouble(etTelaCalculoValorKWh.getText().toString().trim());

                    tvTelaCalculoCustoTotalMensalCalculado.setVisibility(View.VISIBLE);

                    tvTelaCalculoCustoTotalMensalCalculado.setText(decimal.format(novoValor));
                    tvTelaCalculoCustoTotalMensalCalculado.setTextColor(getResources().getColor(R.color.vermelho));
                    tvTelaCalculoCustoTotalMensalCalculado.setShadowLayer(7, 0, 0, getResources().getColor(R.color.branco));
                    tvTelaCalculoCustoTotalMensalCalculado.setTextSize(26);
                }
                else if(etTelaCalculoValorKWh.getText().toString().trim().equals(""))
                {
                    etTelaCalculoValorKWh.setVisibility(View.GONE);
                    tvExtra.setVisibility(View.GONE);
                    tvTelaCalculoCustoTotalMensalCalculado.setTextSize(15);
                    tvTelaCalculoCustoTotalMensalCalculado.setText("clique para inserir o custo kWh");
                    tvTelaCalculoCustoTotalMensalCalculado.setTextColor(getResources().getColor(R.color.preto));
                    tvTelaCalculoCustoTotalMensalCalculado.setShadowLayer(3, 0, 0, getResources().getColor(R.color.branco));
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    private void modoInicio()
    {
        AlertDialog.Builder alerta = new AlertDialog.Builder(TelaCalculo.this);
        alerta.setTitle("");
        alerta.setMessage("Deseja carregar um perfil ou criar um novo?");
        alerta.setIcon(R.drawable.ic_icone_exclamacao);
        alerta.setPositiveButton("carregar um perfil", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(getApplicationContext(), TelaSelecionaPerfil.class);
                startActivityForResult(intent, 0);
                bancoDadosErgos.close();
            }
        });
        alerta.setNegativeButton("criar um novo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), TelaCriarNovoPerfil.class);
                startActivityForResult(intent, 94);
            }
        });
        alerta.show();
    }

    private void toast(String mensagem)
    {
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        //
    }
}