package marizaldo.mestrado.ifrn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Collections;
import java.util.List;

public class TelaQuiz extends Activity
{
    BancoDadosPerguntas bancoDadosPerguntas = new BancoDadosPerguntas(this);
    private List<Questao> arrayListQuestao;
    private Questao questaoAtual;
    private int acertos = 0;
    private int quantidadeQuestoesJogadas = 0;
    private RadioButton rbAssertivaA;
    private RadioButton rbAssertivaB;
    private RadioButton rbAssertivaC;
    private RadioButton rbAssertivaD;
    private RadioGroup rgQuiz;
    private RadioButton resposta;
    private Bundle bundleTelaQuiz;
    private Intent intentTelaQuiz;
    private TextView tvPergunta;
    private Button btPular;
    private int quantidadePulos = 3;
    private int pulosUtilizados = 0;
    private int erros = 0;
    private TextView tvQuantidadeAcertos;
    private TextView tvQuantidadeErros;
    private int quantidadePerguntasCadastradas = 0;
    private RelativeLayout rlPrincipal;
    private RelativeLayout rlBotaoPular;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_quiz);

        configuraRelativeLayout();
        configuraScrollView();
        configuraRadioButton();
        configuraTextView();
        configuraButton();
        pularQuestao();
        verificaAcertivaSelecionada();
        iniciarQuis();
    }

    private void setarNovaQuestao()
    {
        tvPergunta.setText(questaoAtual.getQuestao());
        rbAssertivaA.setText(questaoAtual.getQuestaoAssertivaA());
        rbAssertivaB.setText(questaoAtual.getQuestaoAssertivaB());
        rbAssertivaC.setText(questaoAtual.getQuestaoAssertivaC());
        rbAssertivaD.setText(questaoAtual.getQuestaoAssertivaD());
    }

    private void configuraRelativeLayout()
    {
        rlPrincipal = (RelativeLayout) findViewById(R.id.rl_principal);
        rlBotaoPular = (RelativeLayout) findViewById(R.id.rl_bt_pular);
    }

    private void configuraScrollView()
    {
        scrollView = (ScrollView) findViewById(R.id.sv_quis);
        //
    }

    private void iniciarQuis()
    {
        toast("toque na tela para iniciar");
        rlPrincipal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mostrarComponentes();
            }
        });
    }

    private void mostrarComponentes()
    {
        tvPergunta.setVisibility(View.VISIBLE);
        RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.rl_bt_pular);
        scrollView.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);

        quantidadePerguntasCadastradas = bancoDadosPerguntas.quantidadePerguntas();
        arrayListQuestao = bancoDadosPerguntas.pegarTodasQuestoes();
        questaoAtual = arrayListQuestao.get(0);
        setarNovaQuestao();

        rlPrincipal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            }
        });
    }

    private void configuraButton()
    {
        btPular = (Button) findViewById(R.id.tela_quiz_bt_pular);

        btPular.setTextColor(getResources().getColor(R.color.branco));
        btPular.setShadowLayer(3, 0, 0, getResources().getColor(R.color.preto));
        btPular.setText("pular " + quantidadePulos + "X");
    }

    private void configuraTextView()
    {
        tvPergunta = (TextView) findViewById(R.id.tela_quiz_tv_pergunta);
        tvPergunta.setTextColor(getResources().getColor(R.color.branco));
        tvPergunta.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));

        tvQuantidadeAcertos = (TextView) findViewById(R.id.tv_acertos_quis);
        tvQuantidadeAcertos.setTextColor(getResources().getColor(R.color.branco));
        tvQuantidadeAcertos.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));

        tvQuantidadeErros = (TextView) findViewById(R.id.tv_erros_quis);
        tvQuantidadeErros.setTextColor(getResources().getColor(R.color.branco));
        tvQuantidadeErros.setShadowLayer(3, 0, 0, getResources().getColor(R.color.amarelo_ouro));
    }

    private void configuraRadioButton()
    {
        rgQuiz = (RadioGroup) findViewById(R.id.tela_quiz_rg_quiz);
        rbAssertivaA = (RadioButton) findViewById(R.id.tela_quiz_rb_acertiva_a);
        rbAssertivaB = (RadioButton) findViewById(R.id.tela_quiz_rb_acertiva_b);
        rbAssertivaC = (RadioButton) findViewById(R.id.tela_quiz_rb_acertiva_c);
        rbAssertivaD = (RadioButton) findViewById(R.id.tela_quiz_rb_acertiva_d);

        rbAssertivaA.setTextColor(getResources().getColor(R.color.branco));
        rbAssertivaA.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));
        rbAssertivaB.setTextColor(getResources().getColor(R.color.branco));
        rbAssertivaB.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));
        rbAssertivaC.setTextColor(getResources().getColor(R.color.branco));
        rbAssertivaC.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));
        rbAssertivaD.setTextColor(getResources().getColor(R.color.branco));
        rbAssertivaD.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));
    }

    private void verificaAcertivaSelecionada()
    {
        rgQuiz.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbAssertivaA.isChecked() || rbAssertivaB.isChecked() || rbAssertivaC.isChecked() || rbAssertivaD.isChecked()) {
                    if (rbAssertivaA.isChecked()) {
                        rbAssertivaA.setShadowLayer(3, 0, 0, getResources().getColor(R.color.amarelo));
                    } else if (rbAssertivaB.isChecked()) {
                        rbAssertivaB.setShadowLayer(3, 0, 0, getResources().getColor(R.color.amarelo));
                    } else if (rbAssertivaC.isChecked()) {
                        rbAssertivaC.setShadowLayer(3, 0, 0, getResources().getColor(R.color.amarelo));
                    } else if (rbAssertivaD.isChecked()) {
                        rbAssertivaD.setShadowLayer(3, 0, 0, getResources().getColor(R.color.amarelo));
                    }
                    intentTelaQuiz = new Intent(getApplicationContext(), TelaQuizConfirmaResposta.class);
                    startActivityForResult(intentTelaQuiz, 0);
                }
            }
        });
    }

    private void resetaAssertivas()
    {
        rgQuiz.clearCheck();
        rbAssertivaA.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));
        rbAssertivaB.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));
        rbAssertivaC.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));
        rbAssertivaD.setShadowLayer(3, 0, 0, getResources().getColor(R.color.DeepSkyBlue));
    }

    private void pularQuestao()
    {

        btPular.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                quantidadeQuestoesJogadas++;
                if ((quantidadePulos > 0) && (quantidadeQuestoesJogadas < bancoDadosPerguntas.quantidadePerguntas())) {

                    arrayListQuestao.remove(0);
                    Collections.shuffle(arrayListQuestao);
                    questaoAtual = arrayListQuestao.get(0);
                    setarNovaQuestao();
                    quantidadePulos--;
                    pulosUtilizados++;

                    if (quantidadePulos > 0 && quantidadeQuestoesJogadas == bancoDadosPerguntas.quantidadePerguntas())
                    {
                        toast("você não pode pular a última questão!");
                    }
                    if (quantidadePulos == 0 || quantidadeQuestoesJogadas == bancoDadosPerguntas.quantidadePerguntas()) {
                        btPular.setEnabled(false);

                        btPular.setText("pular " + quantidadePulos + "x");
                    }
                    btPular.setText("pular " + quantidadePulos + "x");
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        //mensagemAlerta("Sair", "Deseja realmente sair da aplicação?");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        rgQuiz = (RadioGroup) findViewById(R.id.tela_quiz_rg_quiz);

        if (resultCode == RESULT_CANCELED)
        {
            resetaAssertivas();
        }
        else if (resultCode == RESULT_OK)
        {
            try
            {
                bundleTelaQuiz = intent.getExtras();
                resposta = (RadioButton) findViewById(rgQuiz.getCheckedRadioButtonId());
                quantidadeQuestoesJogadas++;
                if (bundleTelaQuiz.getInt("chave") == 1)
                {
                    finish();
                }
                else if (bundleTelaQuiz.getInt("chave") == 99)
                {
                    if(questaoAtual.getQuestaoResposta().equals(resposta.getText()))
                    {
                        acertos++;
                        tvQuantidadeAcertos.setText("acertos: " + acertos);
                        toast("você acertou");
                    }
                    if(!questaoAtual.getQuestaoResposta().equals(resposta.getText()))
                    {
                        erros++;
                        tvQuantidadeErros.setText("erros: " + erros);
                        toast("você errou");
                    }
                    if(quantidadeQuestoesJogadas < 10)
                    {
                        arrayListQuestao.remove(0);
                        Collections.shuffle(arrayListQuestao);
                        questaoAtual = arrayListQuestao.get(0);
                        setarNovaQuestao();
                    }
                    else if (quantidadeQuestoesJogadas == 10)
                    {
                        arrayListQuestao.remove(0);
                        esconderComponentes();
                        bundleTelaQuiz = new Bundle();
                        bundleTelaQuiz.putInt("acertos", acertos);
                        bundleTelaQuiz.putInt("erros", erros);
                        bundleTelaQuiz.putInt("pulos", pulosUtilizados);
                        intentTelaQuiz = new Intent(getApplicationContext(), TelaQuizResultado.class);
                        intentTelaQuiz.putExtras(bundleTelaQuiz);
                        startActivityForResult(intentTelaQuiz, 98);
                    }
                }
            }
            catch (Exception e)
            {
                toast(e.toString());
            }
            resetaAssertivas();
        }
    }

    private void toast(String mensagem)
    {
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
    }

    private void vibrar(long milliseconds)
    {
        Vibrator vibrar = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrar.vibrate(milliseconds);
    }

    private void esconderComponentes()
    {
        scrollView.setVisibility(View.GONE);
        rlBotaoPular.setVisibility(View.GONE);
        tvPergunta.setVisibility(View.GONE);
    }
}