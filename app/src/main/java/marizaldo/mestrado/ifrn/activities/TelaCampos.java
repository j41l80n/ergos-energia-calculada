package marizaldo.mestrado.ifrn.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import marizaldo.mestrado.ifrn.R;

public class TelaCampos extends Activity
{
    private Double total_kwh_mensal = 0.0;
    private Double auxiliar = 0.0;
    private Double tempo = 0.0;
    private Double potencia = 0.0;
    private Double aux_potencia = 0.0;
    private TextView tvTelaCamposErro;
    private Intent intentTelaCampos;
    private Bundle bundleTelaCampos;
    private EditText edTelaCamposPotencia;
    private EditText edTelaCamposTempo;
    private TextView tvConsumoDiarioKWwh;
    private TextView tvConsumoMensalKWh;
    private TextView tvPotenciaKWh;
    private TextView tvConsumoDiario;
    private TextView tvConsumoMensal;
    private TextView tvTelaCamposPotencia;
    private TextView tvTelaCamposTempo;
    private Button btConfirmar;
    private Button btCacelar;
    private String codigo;
    private int posicao;
    private DecimalFormat decimal = new DecimalFormat("#,##0.000");
    private double valorCalculado = 0.0;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_campos);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        intentTelaCampos = getIntent();
        bundleTelaCampos = getIntent().getExtras();
        codigo = bundleTelaCampos.getString("nomeItem");
        posicao = bundleTelaCampos.getInt("posicaoItem");

        configurarJanela();
        configuraTextView();
        configuraEditText();
        configuraButton();
        btConfirmar();
        potenciaKeyDown();
        tempoKeyDown();
        botaoCancela(codigo, posicao);
    }

    private void configuraTextView()
    {
        tvTelaCamposPotencia = (TextView) findViewById(R.id.tv_tela_campos_potencia);
        tvConsumoDiarioKWwh = (TextView) findViewById(R.id.tv_consumo_diario_kwh);
        tvConsumoMensalKWh = (TextView) findViewById(R.id.tv_consumo_mensal_kwh);
        tvTelaCamposTempo = (TextView) findViewById(R.id.tv_tela_campos_tempo);
        tvTelaCamposErro = (TextView) findViewById(R.id.tv_tela_campos_erro);
        tvConsumoMensal = (TextView) findViewById(R.id.tv_consumo_mensal);
        tvConsumoDiario = (TextView) findViewById(R.id.tv_consumo_diario);
        tvPotenciaKWh = (TextView) findViewById(R.id.tv_tela_campos_potencia_kwh);

        tvTelaCamposTempo.setTextColor(getResources().getColor(R.color.preto));
        tvTelaCamposTempo.setShadowLayer(3, 0, 0, getResources().getColor(R.color.branco));

        tvTelaCamposPotencia.setTextColor(getResources().getColor(R.color.preto));
        tvTelaCamposPotencia.setShadowLayer(3, 0, 0, getResources().getColor(R.color.branco));

        tvConsumoDiario.setTextColor(getResources().getColor(R.color.preto));
        tvConsumoDiario.setShadowLayer(3, 0, 0, getResources().getColor(R.color.branco));

        tvConsumoMensal.setTextColor(getResources().getColor(R.color.preto));
        tvConsumoMensal.setShadowLayer(3, 0, 0, getResources().getColor(R.color.branco));

        tvPotenciaKWh.setTextColor(getResources().getColor(R.color.preto));
        tvPotenciaKWh.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));

        tvTelaCamposErro.setTextColor(getResources().getColor(R.color.preto));
        tvTelaCamposErro.setShadowLayer(4, 0, 0, getResources().getColor(R.color.vermelho));

        tvConsumoDiarioKWwh.setTextColor(getResources().getColor(R.color.preto));
        tvConsumoDiarioKWwh.setShadowLayer(3, 0, 0, getResources().getColor(R.color.amarelo));

        tvConsumoMensalKWh.setTextColor(getResources().getColor(R.color.preto));
        tvConsumoMensalKWh.setShadowLayer(3, 0, 0, getResources().getColor(R.color.amarelo));
    }

    private void configuraEditText()
    {
        edTelaCamposPotencia = (EditText) findViewById(R.id.et_tela_campos_potencia);
        edTelaCamposTempo = (EditText) findViewById(R.id.et_tela_campos_tempo);
    }

    private void configuraButton()
    {
        btConfirmar = (Button) findViewById(R.id.bt_tela_campos_confirmar);
        btCacelar = (Button) findViewById(R.id.bt_tela_campos_cancelar);
    }

    public void btConfirmar()
    {
        tvTelaCamposErro.setVisibility(View.GONE);

        btConfirmar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String verificaPotencia = edTelaCamposPotencia.getText().toString();
                String verificaTempo = edTelaCamposTempo.getText().toString();

                if (verificaPotencia.trim().isEmpty())
                {
                    edTelaCamposPotencia.setError("Campo vazio");
                    edTelaCamposPotencia.requestFocus();
                }
                else if (verificaTempo.trim().isEmpty())
                {
                    edTelaCamposTempo.setError("Campo vazio");
                    edTelaCamposTempo.requestFocus();
                }
                else if (Double.parseDouble(edTelaCamposTempo.getText().toString()) > 24)
                {
                    edTelaCamposTempo.setError("maior que 24 horas");
                    edTelaCamposTempo.requestFocus();
                }
                else if (verificaTempo.equals("0") || verificaPotencia.equals("0"))
                {
                    tvTelaCamposErro.setVisibility(View.VISIBLE);
                }
                else
                {
                    tvTelaCamposErro.setVisibility(View.GONE);

                    potencia = Double.parseDouble(edTelaCamposPotencia.getText().toString());
                    tempo = Double.parseDouble(edTelaCamposTempo.getText().toString());

                    auxiliar = Double.parseDouble(decimal.format((potencia * tempo) / 1000).replace(".", "").replace(",", "."));
                    total_kwh_mensal = Double.parseDouble(decimal.format(auxiliar * 30).replace(".", "").replace(",", "."));

                    bundleTelaCampos.putDouble("auxiliar", auxiliar);
                    bundleTelaCampos.putDouble("tempo", tempo);
                    bundleTelaCampos.putDouble("total_kwh_mensal", total_kwh_mensal);
                    bundleTelaCampos.putDouble("potencia", potencia);
                    bundleTelaCampos.putString("codigo", codigo);
                    bundleTelaCampos.putInt("posicao", posicao);
                    bundleTelaCampos.putInt("chave", 1);
                    intentTelaCampos.putExtras(bundleTelaCampos);
                    setResult(RESULT_OK, intentTelaCampos);
                    finish();
                }
            }
        });
    }

    private void botaoCancela(final String codigo, final int posicao)
    {
        btCacelar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                bundleTelaCampos.putString("codigo", codigo);
                bundleTelaCampos.putInt("posicao", posicao);
                bundleTelaCampos.putInt("chave", 1);
                intentTelaCampos.putExtras(bundleTelaCampos);
                setResult(RESULT_CANCELED, intentTelaCampos);
                finish();
            }
        });
    }

    private void potenciaKeyDown()
    {
        edTelaCamposPotencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (edTelaCamposPotencia.getText().toString().equals(""))
                {
                    tvPotenciaKWh.setVisibility(View.VISIBLE);
                    tvPotenciaKWh.setText("");
                    tvConsumoMensalKWh.setText("");
                    tvConsumoDiarioKWwh.setText("");
                    edTelaCamposTempo.setText("");
                    edTelaCamposTempo.setEnabled(false);
                }
                else if (!edTelaCamposPotencia.getText().toString().equals("") &&
                        !edTelaCamposTempo.getText().toString().equals(""))
                {
                    edTelaCamposTempo.setEnabled(true);
                    aux_potencia = (Double.parseDouble(edTelaCamposPotencia.getText()
                            .toString()) / 1000);
                    tvPotenciaKWh.setVisibility(View.VISIBLE);
                    tvPotenciaKWh.setText("kW: " + decimal.format(aux_potencia));

                    valorCalculado = (Double.parseDouble(edTelaCamposPotencia.getText().toString())
                            * Double.parseDouble(edTelaCamposTempo.getText().toString()));
                    valorCalculado = valorCalculado / 1000;

                    tvConsumoDiarioKWwh.setText(" " + decimal.format(valorCalculado) + " kWh");
                    tvConsumoMensalKWh.setText(" " + decimal.format(valorCalculado * 30) + " kWh");
                }
                else if (!edTelaCamposPotencia.getText().toString().equals(""))
                {
                    edTelaCamposTempo.setEnabled(true);
                    aux_potencia = (Double.parseDouble(edTelaCamposPotencia.getText().toString()) / 1000);
                    tvPotenciaKWh.setVisibility(View.VISIBLE);
                    tvPotenciaKWh.setText("kW: " + decimal.format(aux_potencia));
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    private void tempoKeyDown()
    {
        edTelaCamposTempo.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (edTelaCamposTempo.getText().toString().equals(""))
                {
                    tvConsumoDiarioKWwh.setText("");
                    tvConsumoMensalKWh.setText("");
                }
                else if(!edTelaCamposPotencia.getText().toString().equals("") &&
                        !edTelaCamposTempo.getText().toString().equals(""))
                {
                    aux_potencia = (Double.parseDouble(edTelaCamposPotencia.getText().toString()) / 1000);
                    tvPotenciaKWh.setVisibility(View.VISIBLE);
                    tvPotenciaKWh.setText("kW: " + decimal.format(aux_potencia));
                    valorCalculado = (Double.parseDouble(edTelaCamposPotencia.getText().toString()) *
                            Double.parseDouble(edTelaCamposTempo.getText().toString()));
                    valorCalculado = valorCalculado / 1000;
                    DecimalFormat form = new DecimalFormat("0.000");
                    form.setRoundingMode(RoundingMode.UP);

                    tvConsumoDiarioKWwh.setText(" " + decimal.format(valorCalculado) + " kWh");
                    tvConsumoMensalKWh.setText(" " + decimal.format(valorCalculado*30) + " kWh");
                }
                else if (!edTelaCamposPotencia.getText().toString().equals(""))
                {
                    aux_potencia = (Double.parseDouble(edTelaCamposPotencia.getText().toString()) / 1000);
                    tvPotenciaKWh.setVisibility(View.VISIBLE);
                    tvPotenciaKWh.setText("kW: " + decimal.format(aux_potencia));
                    valorCalculado = (Double.parseDouble(edTelaCamposPotencia.getText().toString()) *
                            Double.parseDouble(edTelaCamposTempo.getText().toString()));
                    valorCalculado = valorCalculado / 1000;
                    tvPotenciaKWh.setVisibility(View.VISIBLE);
                    tvPotenciaKWh.setText("kW: " + aux_potencia.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        bundleTelaCampos = new Bundle();
        bundleTelaCampos.putInt("chave", 3);
        intentTelaCampos.putExtras(bundleTelaCampos);
        setResult(RESULT_OK, intentTelaCampos);
        finish();
    }

    private void configurarJanela()
    {
        WindowManager.LayoutParams parametros = getWindow().getAttributes();
        parametros.width = LinearLayout.LayoutParams.MATCH_PARENT;
        parametros.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(parametros);
    }
}

