package marizaldo.mestrado.ifrn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Questao
{
    private int questaoCodigoPergunta;
    private int questaoNivelPergunta;
    private String questao;
    private String questaoAssertivaA;
    private String questaoAssertivaB;
    private String questaoAssertivaC;
    private String questaoAssertivaD;
    private String questaoResposta;

    Questao()
    {
        questaoCodigoPergunta = 0;
        questao = "";
        questaoResposta = "";
        questaoAssertivaA = "";
        questaoAssertivaB = "";
        questaoAssertivaC = "";
        questaoAssertivaD = "";
        questaoNivelPergunta = 0;
    }

    public Questao(String questao, String questaoAssertivaA, String questaoAssertivaB, String questaoAssertivaC, String questaoAssertivaD, String questaoResposta, int questaoNivelPergunta)
    {
        this.questao = questao;
        this.questaoAssertivaA = questaoAssertivaA;
        this.questaoAssertivaB = questaoAssertivaB;
        this.questaoAssertivaC = questaoAssertivaC;
        this.questaoAssertivaD = questaoAssertivaD;
        this.questaoResposta = questaoResposta;
        this.questaoNivelPergunta = questaoNivelPergunta;
    }

    public int getQuestaoCodigoPergunta()
    {
        return questaoCodigoPergunta;
    }

    public String getQuestao()
    {
        return questao;
    }

    public String getQuestaoAssertivaA()
    {
        return questaoAssertivaA;
    }

    public String getQuestaoAssertivaB()
    {
        return questaoAssertivaB;
    }

    public String getQuestaoAssertivaC()
    {
        return questaoAssertivaC;
    }

    public String getQuestaoAssertivaD()
    {
        return questaoAssertivaD;
    }

    public String getQuestaoResposta()
    {
        return questaoResposta;
    }

    public int getQuestaoNivelPergunta()
    {
        return questaoNivelPergunta;
    }

    public void setQuestaoCodigoPergunta(int questaoCodigoPergunta) {
        this.questaoCodigoPergunta = questaoCodigoPergunta;
    }

    public void setQuestao(String questao)
    {
        this.questao = questao;
    }

    public void setQuestaoAssertivaA(String questaoAssertivaA)
    {
        this.questaoAssertivaA = questaoAssertivaA;
    }

    public void setQuestaoAssertivaB(String questaoAssertivaB)
    {
        this.questaoAssertivaB = questaoAssertivaB;
    }

    public void setQuestaoAssertivaC(String questaoAssertivaC)
    {
        this.questaoAssertivaC = questaoAssertivaC;
    }

    public void setQuestaoAssertivaD(String questaoAssertivaD)
    {
        this.questaoAssertivaD = questaoAssertivaD;
    }

    public void setQuestaoResposta(String questaoResposta)
    {
        this.questaoResposta = questaoResposta;
    }

    public void setQuestaoNivelPergunta(int questaoNivelPergunta)
    {
        this.questaoNivelPergunta = questaoNivelPergunta;
    }

    public List<String> assertivasRandomicas()
    {
        List<String> shuffle = new ArrayList<>();
        shuffle.add(getQuestaoAssertivaA());
        shuffle.add(getQuestaoAssertivaB());
        shuffle.add(getQuestaoAssertivaC());
        shuffle.add(getQuestaoAssertivaD());
        Collections.shuffle(shuffle);
        return shuffle;
    }
}
