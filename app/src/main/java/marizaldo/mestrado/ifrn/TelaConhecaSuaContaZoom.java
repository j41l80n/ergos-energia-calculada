package marizaldo.mestrado.ifrn;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class TelaConhecaSuaContaZoom extends Activity
{
    private Intent intent = new Intent();
    private Bundle bundle = new Bundle();

    private String DADOS_DA_UNIDADE_CONSUMIDORA = "<p>Além do endereço da Unidade Consumidora," +
            " você encontrará aqui o nome do titular da conta, ou seja, o responsável pelos" +
            " pagamentos das contas de energia elétrica. É muito importante que este nome esteja " +
            "sempre atualizado.</p>";
    private String INFORMACOES_IMPORTANTES = "<p>Data da próxima leitura: Nesta data, deixe " +
            "livre o acesso até o relógio de luz.</p>" +
            "<p>Conta do mês: Mês de referência ou o mês em que houve o consumo de energia.</p>" +
            "<p>Data de vencimento: Data limite para o pagamento da sua conta sem a aplicação" +
            " de acréscimos por atraso.</p>" +
            "<p>Valor da conta: Valor total da sua conta de energia elétrica.</p>";
    private String DETALHAMENTO_DA_CONTA = "<p>Aqui você identifica os valores que compões a" +
            " sua conta.</p>" +
            "<p>O consumo ativo do período, que vem expresso no campo Demonstrativo de Consumo" +
            " desta Nota Fiscal, corresponde à diferença entre os valores das leituras atual e " +
            "anterior, multiplicado pelo preço do Kwh.</p>" +
            "<p>Tributos: São os impostos PIS/Cofins e ICMS detalhados, além da bandeira tarifária.<p>" +
            "<p>Além desses, são discriminados serviços, como acréscimos por atraso, juros e" +
            " Contribuição de Iluminação Pública (CIP).</p>";
    private String HISTORICO_DECCONSUMO = "<p>Você poderá acompanhar a evolução do seu consumo " +
            "de energia elétrica dos últimos 13 meses.</p>";
    private String INFORMACOES_DE_TRIBUTOS = "<p>Descrição dos impostos, alíquota e a base" +
            " de cálculo.</p>" +
            "<p>O ICMS (Imposto sobre circulação de mercadorias e serviços) é o imposto " +
            "regulamentado por cada estado, por isso as alíquotas são variáveis.</p>" +
            "<p>O PIS (Programa de Integração Social) é uma contribuição tributária de" +
            " caráter social, que tem como objetivo financiar o pagamento do seguro-desemprego, " +
            "abono e participação na receita dos órgãos e entidades, tanto para os trabalhadores " +
            "de empresas públicas, como privadas.</p>" +
            "<p>A Cofins (Contribuição para o Financiamento da Seguridade Social) é uma " +
            "contribuição social que tem como objetivo financiar a Seguridade Social, em suas" +
            " áreas fundamentais, incluindo entre elas a Previdência Social, a Assistência Social" +
            " e a Saúde Pública.</p>";
    private String COMPOSICAO_DE_CONSUMO = "<p>Descrição dos custos de cada etapa, desde a" +
            " produção até o ponto de consumo e os percentuais de cada etapa no custo total.</p>" +
            "<p>Geração de energia: Custos com a produção da energia nas usinas geradoras.</p>" +
            "<p>Transmissão: Custos de transporte da energia, do ponto de geração até as subestações.</p>" +
            "<p>Distribuição: Custo para levar a energia das subestações para a casa dos clientes.</p>" +
            "<p>Encargos e tributos: Determinados por lei, destinados ao Poder Público.</p>";
    private String NIVEIS_DE_TENSÃO = "Informa a voltagem (tensão elétrica) recomendável (nominal)" +
            " e os limites mínimos e máximos da tensão elétrica aceitáveis disponibilizados " +
            "para o consumidor. IMPORTANTE: caso os níveis de tensão que estão chegando ao " +
            "consumidor estejam abaixo do mínimo ou acima do máximo, entre em contato com a " +
            "distribuidora de energia.";

    private TextView tvConhecaSuaContaZoom;
    private TouchImageView img;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_conheca_sua_conta_zoom);

        intent = getIntent();
        bundle = getIntent().getExtras();

        tvConhecaSuaContaZoom = new TextView(this);
        img = new TouchImageView(this);

        img.setScaleType(TouchImageView.ScaleType.MATRIX);
        img.setMaxZoom(4.5f);

        configuraTextView();

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.setBackgroundColor(getResources().getColor(R.color.branco));

        layout.addView(img);
        //layout.addView(tvConhecaSuaContaZoom);

        ScrollView scroll = new ScrollView(this);

        scroll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        scroll.addView(tvConhecaSuaContaZoom);

        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int largura = display.getWidth();
        int altura =  display.getWidth();

        layout.addView(scroll);
        LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(largura, altura);
        img.setLayoutParams(para);

        configurarJanela();
        setContentView(layout);
        zoomImagem(bundle.getInt("codigoItem"));
    }

    private void configuraTextView()
    {
        tvConhecaSuaContaZoom.setTextColor(getResources().getColor(R.color.azul_escuro));
        tvConhecaSuaContaZoom.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));
        tvConhecaSuaContaZoom.setGravity(Gravity.CENTER);
    }

    private void configurarJanela()
    {
        WindowManager.LayoutParams parametros = getWindow().getAttributes();
        parametros.width = LinearLayout.LayoutParams.MATCH_PARENT;
        parametros.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(parametros);
    }

    private void zoomImagem(int codigoZomm)
    {
        switch(codigoZomm)
        {
            case 1:
                tvConhecaSuaContaZoom.setText(Html.fromHtml(DADOS_DA_UNIDADE_CONSUMIDORA));
                img.setImageResource(R.drawable.contadeluz_1);
                break;
            case 2:
                tvConhecaSuaContaZoom.setText(Html.fromHtml(INFORMACOES_IMPORTANTES));
                img.setImageResource(R.drawable.contadeluz_2);
                break;
            case 3:
                tvConhecaSuaContaZoom.setText(Html.fromHtml(DETALHAMENTO_DA_CONTA));
                img.setImageResource(R.drawable.contadeluz_3);
                break;
            case 4:
                tvConhecaSuaContaZoom.setText(Html.fromHtml(HISTORICO_DECCONSUMO));
                img.setImageResource(R.drawable.contadeluz_4);
                break;
            case 5:
                tvConhecaSuaContaZoom.setText(Html.fromHtml(INFORMACOES_DE_TRIBUTOS));
                img.setImageResource(R.drawable.contadeluz_5);
                break;
            case 6:
                tvConhecaSuaContaZoom.setText(Html.fromHtml(COMPOSICAO_DE_CONSUMO));
                img.setImageResource(R.drawable.contadeluz_6);
                break;
            case 7:
                tvConhecaSuaContaZoom.setText(Html.fromHtml(NIVEIS_DE_TENSÃO));
                img.setImageResource(R.drawable.contadeluz_7);
                break;
        }
    }
}




















