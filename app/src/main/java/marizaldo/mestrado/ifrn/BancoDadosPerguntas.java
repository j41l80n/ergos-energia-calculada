package marizaldo.mestrado.ifrn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDadosPerguntas extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "banco_ergos";
    private static final String TB_PERGUNTAS = "tb_perguntas";
    private static final String CODIGO_PERGUNTA = "codigo_pergunta";
    private static final String PERGUNTA = "pergunta";
    private static final String RESPOSTA = "resposta";
    private static final String ASSERTIVA_A = "assertiva_a";
    private static final String ASSERTIVA_B = "assertiva_b";
    private static final String ASSERTIVA_C = "assertiva_c";
    private static final String ASSERTIVA_D = "assertiva_d";
    private static final String NIVEL_PERGUNTA = "nivel_pergunta";
    private SQLiteDatabase bancoDadosErgos;

    String pergunta1 = "A unidade de energia no Sistema Internacional de Unidades (SI) é:";
    String resposta1 = "joule;";
    String alt1_1 = "watt;";
    String alt1_2 = "caloria;";
    String alt1_3 = "joule;";
    String alt1_4 = "volt;";

    String pergunta2 = "Quando, necessariamente, um equipamento está consumindo energia elétrica?";
    String resposta2 = "quando passa corrente elétrica;";
    String alt2_1 = "quando está conectado na tomada;";
    String alt2_2 = "quando passa corrente elétrica;";
    String alt2_3 = "quando o potencial elétrico é alto;";
    String alt2_4 = "quando o interruptor ou disjuntor está ligado;";

    String pergunta3 = "Um equipamento tem potência elétrica de 200 W. Isto significa:";
    String resposta3 = "que em 1 segundo de funcionamento o equipamento consome 200 J de energia;";
    String alt3_1 = "que em 1 segundo de funcionamento o equipamento consome 200 J de energia;";
    String alt3_2 = "que em 200 segundos de funcionamento o equipamento consome 1 joule de energia;";
    String alt3_3 = "que o equipamento consome 1 joule de energia a cada 200 segundos de funcionamento;";
    String alt3_4 = "que em 1 hora de funcionamento o equipamento consome 200 joule de energia;";

    String pergunta4 = "Se um equipamento tem potência elétrica de 3 kW. Isso é o mesmo que:";
    String resposta4 = "3.000 W;";
    String alt4_1 = "300 W;";
    String alt4_2 = "30 W;";
    String alt4_3 = "3.000.000 W;";
    String alt4_4 = "3.000 W;";

    String pergunta5 = "Das unidades abaixo, a única que não é de energia é:";
    String resposta5 = "Newton (N);";
    String alt5_1 = "joule (J);";
    String alt5_2 = "Caloria (cal);";
    String alt5_3 = "quilowatt-hora (kWh);";
    String alt5_4 = "Newton (N);";

    String pergunta6 = "Um ferro elétrico tem potência de 1000 W e um ventilador 60 W. Qual " +
            "gasta mais energia em 1 hora de funcionamento?";
    String resposta6 = "o ferro;";
    String alt6_1 = "o ferro;";
    String alt6_2 = "o ventilador;";
    String alt6_3 = "igual pois são ligados na mesma voltagem;";
    String alt6_4 = "não se pode afirmar nada, pelas informações dadas;";

    String pergunta7 = "A maior parte da energia elétrica no Brasil é produzida por:";
    String resposta7 = "usinas hidrelétricas;";
    String alt7_1 = "usinas nucleares;";
    String alt7_2 = "usinas termoelétricas;";
    String alt7_3 = "usinas hidrelétricas;";
    String alt7_4 = "usinas eólicas;";

    String pergunta8 = "Das usinas abaixo, qual causa menos impacto ao meio ambiente?";
    String resposta8 = "eólica;";
    String alt8_1 = "hidrelétrica;";
    String alt8_2 = "eólica;";
    String alt8_3 = "nuclear;";
    String alt8_4 = "termoelétrica;";

    String pergunta9 = "Quais são os equipamentos elétricos que gastam menos energia elétrica?";
    String resposta9 = "os de menor potência elétrica;";
    String alt9_1 = "os de maior potência elétrica;";
    String alt9_2 = "os que são ligados numa tensão elétrica menor;";
    String alt9_3 = "os de menor potência elétrica;";
    String alt9_4 = "os que são ligados numa tensão elétrica maior;";

    String pergunta10 = "Qual a função de um disjuntor no circuito elétrico:";
    String resposta10 = "proteger o circuito;";
    String alt10_1 = "proteger o circuito;";
    String alt10_2 = "produzir a corrente elétrica;";
    String alt10_3 = "produzir a diferença de potencial;";
    String alt10_4 = "aumentar a corrente elétrica;";

    String pergunta11 = "Um chuveiro elétrico tem uma chave seletora para as posições inverno " +
            "e verão. Em qual posição da chave seletora o chuveiro gasta mais energia?";
    String resposta11 = "na posição inverno;";
    String alt11_1 = "tanto faz, pois o chuveiro, independente da posição da chave, estará ligado;";
    String alt11_2 = "na posição verão;";
    String alt11_3 = "na posição inverno;";
    String alt11_4 = "em qualquer posição dependerá do fluxo de água;";

    String pergunta12 = "As usinas eólica, hidrelétrica e termoelétrica, tem como fonte primária " +
            "de energia, respectivamente:";
    String resposta12 = "vento, água e combustível fóssil;";
    String alt12_1 = "água, vento e urânio;";
    String alt12_2 = "vento, combustível fóssil e água;";
    String alt12_3 = "combustível fóssil, água e urânio;";
    String alt12_4 = "vento, água e combustível fóssil;";

    String pergunta13 = "coloque em ordem crescente de consumo de energia, os equipamentos: " +
            "ferro elétrico, lâmpada de 9W, chuveiro elétrico e televisão.";
    String resposta13 = "lâmpada de 9W, televisão, ferro elétrico e chuveiro elétrico;";
    String alt13_1 = "chuveiro elétrico, ferro elétrico, televisão e lâmpada de 9W;";
    String alt13_2 = "lâmpada de 9W, chuveiro elétrico, ferro elétrico e televisão;";
    String alt13_3 = "lâmpada de 9W, televisão, ferro elétrico e chuveiro elétrico;";
    String alt13_4 = "televisão, ferro elétrico, lâmpada de 9W e chuveiro elétrico;";

    String pergunta14 = "Existe um fenômeno chamado de efeito joule, no qual a energia elétrica " +
            "é convertida em energia térmica. Existe equipamento que tem seu funcionamento " +
            "baseado neste fenômeno. Qual equipamento abaixo tem o efeito joule como base " +
            "de seu funcionamento?";
    String resposta14 = "ferro elétrico;";
    String alt14_1 = "forno micro-onda;";
    String alt14_2 = "ferro elétrico;";
    String alt14_3 = "geladeira;";
    String alt14_4 = "receptor de antena parabólica;";

    String pergunta15 = "A energia solar, consiste em converter a energia radiante do Sol em" +
            " energia elétrica, através de painéis fotovoltaicos. O Nordeste brasileiro apresenta" +
            " um grande potencial na produção de energia elétrica através deste processo. Que " +
            "característica o Nordeste brasileiro apresenta, que o torna uma região adequada a" +
            " produção deste tipo de energia?";
    String resposta15 = "incidência solar alta;";
    String alt15_1 = "baixa incidência de chuvas;";
    String alt15_2 = "região com relevo de baixas altitudes;";
    String alt15_3 = "vegetação de pequeno porte;";
    String alt15_4 = "incidência solar alta;";

    String pergunta16 = "Quando desligamos a TV, geralmente o fazemos através do controle remoto," +
            " de modo que a TV fica no modo “stand by”, e para religa-la, basta apertarmos" +
            " novamente o controle remoto. No modo stand by, fica acesso um pequeno LED. Quando a" +
            " TV está no modo stand by:";
    String resposta16 = "gasta energia, mesmo sendo pouca, mais em período longo pode ser significativo;";
    String alt16_1 = "gasta energia, mesmo sendo pouca, mais em período longo pode ser significativo;";
    String alt16_2 = "não gasta energia, pois a TV só gasta energia quando está ligada;";
    String alt16_3 = "gasta energia, mais por maior que seja o tempo, este gasto é insignificante;";
    String alt16_4 = "não gasta energia, pois o LED brilha sozinho;";

    String pergunta17 = "Uma lâmpada incandescente:;";
    String resposta17 = "tem seu funcionamento baseado no efeito joule;";
    String alt17_1 = "é a mais apropriada para iluminar ambientes;";
    String alt17_2 = "tem seu funcionamento baseado no efeito joule;";
    String alt17_3 = "é bastante econômica;";
    String alt17_4 = "tem durabilidade muito grande;";

    String pergunta18 = "Os procedimentos citados nas alternativas, aumentam o consumo de energia" +
            " elétrica, exceto:";
    String resposta18 = "engomar as roupas todas de uma vez;";
    String alt18_1 = "usar lâmpadas incandescentes ao invés de lâmpadas fluorescentes;";
    String alt18_2 = "engomar as roupas todas de uma vez;";
    String alt18_3 = "colocar comida ainda quente na geladeira;";
    String alt18_4 = "ficar abrindo constantemente a geladeira;";

    String pergunta19 = "A lei da conservação da energia diz que a energia não se cria, não se" +
            " perde, ela se transforma. Quando um ventilador é ligado, temos, predominantemente," +
            " a transformação de energia elétrica em energia:";
    String resposta19 = "cinética;";
    String alt19_1 = "cinética;";
    String alt19_2 = "térmica;";
    String alt19_3 = "química;";
    String alt19_4 = "potencial;";

    String pergunta20 = "A energia elétrica deve ser conduzida desde o local de sua geração " +
            "(usina) até as nossas residências. Durante a transmissão:";
    String resposta20 = "existe perda de energia devido a resistência elétrica dos fios de transmissão;";
    String alt20_1 = "não existe perda de energia;";
    String alt20_2 = "existe perda de energia, pelo fato dos fios de transmissão não serem encapados;";
    String alt20_3 = "existe perda de energia devido a resistência elétrica dos fios de transmissão;";
    String alt20_4 = "existe perda de energia devido os fios serem de transmissão serem maus condutores;";

    String pergunta21 = "Um chuveiro tem potência de 3000 W. Durante o dia é ligado por 1 hora." +
            " Quantos kWh de energia ele gasta em um mês?";
    String resposta21 = "90;";
    String alt21_1 = "30;";
    String alt21_2 = "90;";
    String alt21_3 = "360;";
    String alt21_4 = "3;";

    String pergunta22 = "Para determinar a energia consumida por um equipamento elétrico é " +
            "necessário saber:";
    String resposta22 = "a sua potência e o tempo de uso;";
    String alt22_1 = "a voltagem que o equipamento é ligado e o tempo de uso;";
    String alt22_2 = "a corrente elétrica que percorre o equipamento e sua potência;";
    String alt22_3 = "a sua potência e o tempo de uso;";
    String alt22_4 = "a frequência da rede elétrica que ele é ligado e corrente elétrica que o percorre;";

    String pergunta23 = "Numa usina termoelétrica de produção de energia elétrica, um dos " +
            "grandes problemas ambientais é a emissão de dióxido de carbono. Esse gás: ";
    String resposta23 = "contribui para o efeito estufa;";
    String alt23_1 = "causa os buracos na camada de ozônio;";
    String alt23_2 = "contribui para o efeito estufa;";
    String alt23_3 = "impede a fotossíntese das plantas;";
    String alt23_4 = "polui os mares;";

    String pergunta24 = "O maior problema das usinas hidrelétricas é o grande lago formado. Este lago:";
    String resposta24 = "em alguns casos alaga grandes áreas de floresta, afetando o ecossistema local;";
    String alt24_1 = "faz com que o rio antes perene fique temporário;";
    String alt24_2 = "água não é propícia ao consumo humano;";
    String alt24_3 = "em alguns casos alaga grandes áreas de floresta, afetando o ecossistema local;";
    String alt24_4 = "reduz as chuvas na região;";

    String pergunta25 = "No cálculo do custo da energia elétrica, o que mais impacta é:";
    String resposta25 = "a geração nas usinas;";
    String alt25_1 = "a geração nas usinas;";
    String alt25_2 = "transmissão das usinas até as subestações;";
    String alt25_3 = "a distribuição das subestações até as residências;";
    String alt25_4 = "os tributos;";

    String pergunta26 = "Uma das medidas recomendadas para economizar energia elétrica é trocar " +
            "as lâmpadas incandescentes por lâmpadas fluorescentes (lâmpadas frias). Esta " +
            "recomendação se baseia no fato das lâmpadas fluorescentes:";
    String resposta26 = "com uma potência menor produzirem a mesma luminosidade;";
    String alt26_1 = "usam uma voltagem menor;";
    String alt26_2 = "a corrente elétrica nelas é maior;";
    String alt26_3 = "produzirem um efeito joule muito grande;";
    String alt26_4 = "com uma potência menor produzirem a mesma luminosidade;";

    String pergunta27 = "Numa usina nuclear a sequência de transformação de energia, até chegar a " +
            "energia elétrica é?";
    String resposta27 = "nuclear – térmica – cinética – elétrica;";
    String alt27_1 = "nuclear – térmica – cinética – elétrica;";
    String alt27_2 = "cinética – térmica - nuclear – elétrica;";
    String alt27_3 = "térmica – cinética – nuclear – elétrica;";
    String alt27_4 = "nuclear – potencial gravitacional – térmica – elétrica;";

    String pergunta28 = "Das usinas produtoras de energia elétrica, qual não usa gerador" +
            " eletromagnético?";
    String resposta28 = "termoelétrica;";
    String alt28_1 = "nuclear;";
    String alt28_2 = "eólica;";
    String alt28_3 = "solar;";
    String alt28_4 = "termoelétrica;";

    String pergunta29 = "O que diz a lei da conservação da energia?";
    String resposta29 = "a energia não se perde nem se cria, ela se transforma;";
    String alt29_1 = "a energia não pode ser transformada de um tipo em outro;";
    String alt29_2 = "a energia não se perde nem se cria, ela se transforma;";
    String alt29_3 = "a energia que se conserva é a energia elétrica;";
    String alt29_4 = "uma outra energia pode se transformar em elétrica, mas a energia elétrica " +
            "não pode se transforma em outra;";

    String pergunta30 = "É uma fonte renovável de energia:";
    String resposta30 = "vento;";
    String alt30_1 = "petróleo;";
    String alt30_2 = "carvão mineral;";
    String alt30_3 = "vento;";
    String alt30_4 = "gás natural;";

    public BancoDadosPerguntas(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase bancoDadosOnCreate)
    {
        bancoDadosErgos = bancoDadosOnCreate;
        String dropTable = "DROP TABLE IF EXISTS " + DATABASE_NAME + "." + TB_PERGUNTAS;
        bancoDadosErgos.execSQL(dropTable);
        String sql = "CREATE TABLE IF NOT EXISTS " + TB_PERGUNTAS + "(" + CODIGO_PERGUNTA + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PERGUNTA + " TEXT, " + RESPOSTA + " TEXT, " + ASSERTIVA_A + " TEXT, " + ASSERTIVA_B + " TEXT, " + ASSERTIVA_C + " TEXT, " + ASSERTIVA_D + " TEXT, " + NIVEL_PERGUNTA + " INTEGER);";
        bancoDadosErgos.execSQL(sql);
        adicionarQuestoes();
    }

    private void adicionarQuestoes()
    {
        Questao q1 = new Questao(pergunta1, alt1_1 ,alt1_2, alt1_3, alt1_4, resposta1, 0);
        this.addQuestion(q1);
        Questao q2 = new Questao(pergunta2, alt2_1, alt2_2, alt2_3, alt2_4, resposta2, 0);
        this.addQuestion(q2);
        Questao q3 = new Questao(pergunta3, alt3_1, alt3_2, alt3_3, alt3_4, resposta3, 0);
        this.addQuestion(q3);
        Questao q4 = new Questao(pergunta4, alt4_1, alt4_2, alt4_3, alt4_4, resposta4, 0);
        this.addQuestion(q4);
        Questao q5 = new Questao(pergunta5, alt5_1, alt5_2, alt5_3, alt5_4, resposta5, 0);
        this.addQuestion(q5);
        Questao q6 = new Questao(pergunta6, alt6_1, alt6_2, alt6_3, alt6_4, resposta6, 0);
        this.addQuestion(q6);
        Questao q7 = new Questao(pergunta7, alt7_1, alt7_2, alt7_3, alt7_4, resposta7, 0);
        this.addQuestion(q7);
        Questao q8 = new Questao(pergunta8, alt8_1, alt8_2, alt8_3, alt8_4, resposta8, 0);
        this.addQuestion(q8);
        Questao q9 = new Questao(pergunta9, alt9_1, alt9_2, alt9_3, alt9_4, resposta9, 0);
        this.addQuestion(q9);
        Questao q10 = new Questao(pergunta10, alt10_1, alt10_2, alt10_3, alt10_4, resposta10, 0);
        this.addQuestion(q10);
        Questao q11 = new Questao(pergunta11, alt11_1, alt11_2, alt11_3, alt11_4, resposta11, 0);
        this.addQuestion(q11);
        Questao q12 = new Questao(pergunta12, alt12_1, alt12_2, alt12_3, alt12_4, resposta12, 0);
        this.addQuestion(q12);
        Questao q13 = new Questao(pergunta13, alt13_1, alt13_2, alt13_3, alt13_4, resposta13, 0);
        this.addQuestion(q13);
        Questao q14 = new Questao(pergunta14, alt14_1, alt14_2, alt14_3, alt14_4, resposta14, 0);
        this.addQuestion(q14);
        Questao q15 = new Questao(pergunta15, alt15_1, alt15_2, alt15_3, alt15_4, resposta15, 0);
        this.addQuestion(q15);
        Questao q16 = new Questao(pergunta16, alt16_1, alt16_2, alt16_3, alt16_4, resposta16, 0);
        this.addQuestion(q16);
        Questao q17 = new Questao(pergunta17, alt17_1, alt17_2, alt17_3, alt17_4, resposta17, 0);
        this.addQuestion(q17);
        Questao q18 = new Questao(pergunta18, alt18_1, alt18_2, alt18_3, alt18_4, resposta18, 0);
        this.addQuestion(q18);
        Questao q19 = new Questao(pergunta19, alt19_1, alt19_2, alt19_3, alt19_4, resposta19, 0);
        this.addQuestion(q19);
        Questao q20 = new Questao(pergunta20, alt20_1, alt20_2, alt20_3, alt20_4, resposta20, 0);
        this.addQuestion(q20);
        Questao q21 = new Questao(pergunta21, alt21_1, alt21_2, alt21_3, alt21_4, resposta21, 0);
        this.addQuestion(q21);
        Questao q22 = new Questao(pergunta22, alt22_1, alt22_2, alt22_3, alt22_4, resposta22, 0);
        this.addQuestion(q22);
        Questao q23 = new Questao(pergunta23, alt23_1, alt23_2, alt23_3, alt23_4, resposta23, 0);
        this.addQuestion(q23);
        Questao q24 = new Questao(pergunta24, alt24_1, alt24_2, alt24_3, alt24_4, resposta24, 0);
        this.addQuestion(q24);
        Questao q25 = new Questao(pergunta25, alt25_1, alt25_2, alt25_3, alt25_4, resposta25, 0);
        this.addQuestion(q25);
        Questao q26 = new Questao(pergunta26, alt26_1 ,alt26_2, alt26_3, alt26_4, resposta26, 0);
        this.addQuestion(q26);
        Questao q27 = new Questao(pergunta27, alt27_1, alt27_2, alt27_3, alt27_4, resposta27, 0);
        this.addQuestion(q27);
        Questao q28 = new Questao(pergunta28, alt28_1, alt28_2, alt28_3, alt28_4, resposta28, 0);
        this.addQuestion(q28);
        Questao q29 = new Questao(pergunta29, alt29_1, alt29_2, alt29_3, alt29_4, resposta29, 0);
        this.addQuestion(q29);
        Questao q30 = new Questao(pergunta30, alt30_1, alt30_2, alt30_3, alt30_4, resposta30, 0);
        this.addQuestion(q30);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bancoDadosOnUpgrade, int versaoAntiga, int novaVersao)
    {
        bancoDadosOnUpgrade.execSQL("DROP TABLE IF EXISTS " + TB_PERGUNTAS + ";");
        onCreate(bancoDadosOnUpgrade);
    }

    public void addQuestion(Questao questao)
    {
        ContentValues values = new ContentValues();
        values.put(PERGUNTA, questao.getQuestao());
        values.put(RESPOSTA, questao.getQuestaoResposta());
        values.put(ASSERTIVA_A, questao.getQuestaoAssertivaA());
        values.put(ASSERTIVA_B, questao.getQuestaoAssertivaB());
        values.put(ASSERTIVA_C, questao.getQuestaoAssertivaC());
        values.put(ASSERTIVA_D, questao.getQuestaoAssertivaD());
        values.put(NIVEL_PERGUNTA, questao.getQuestaoNivelPergunta());
        bancoDadosErgos.insert(TB_PERGUNTAS, null, values);
    }

    public List<Questao> pegarTodasQuestoes()
    {
        List<Questao> listaBancoDadosPerguntasQuestoes = new ArrayList<>();
        String sql = "SELECT * FROM " + TB_PERGUNTAS + " ORDER BY RANDOM() LIMIT 10;";
        this.bancoDadosErgos = this.getReadableDatabase();
        Cursor cursor = bancoDadosErgos.rawQuery(sql, null);
        List<Questao> teste = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            do
            {
                Questao questao = new Questao();
                List<String> assertivas = new ArrayList<>();

                questao.setQuestao(cursor.getString(1));
                questao.setQuestaoResposta(cursor.getString(2));

                assertivas.add(cursor.getString(3));
                assertivas.add(cursor.getString(4));
                assertivas.add(cursor.getString(5));
                assertivas.add(cursor.getString(6));
                Collections.shuffle(assertivas);

                questao.setQuestaoAssertivaA(assertivas.get(0));
                questao.setQuestaoAssertivaB(assertivas.get(1));
                questao.setQuestaoAssertivaC(assertivas.get(2));
                questao.setQuestaoAssertivaD(assertivas.get(3));

                listaBancoDadosPerguntasQuestoes.add(questao);
            }
            while(cursor.moveToNext());
            for(int i = 0; i < listaBancoDadosPerguntasQuestoes.size(); i++)
            {
                teste.add(listaBancoDadosPerguntasQuestoes.get(i));
            }
        }
        Collections.shuffle(teste);
        return  teste;
    }

    public int quantidadePerguntas()
    {
        int row = 0;
        try
        {
            String selectQuery = "SELECT  * FROM tb_perguntas;";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            row = cursor.getCount();
            cursor.close();
        }
        catch (Exception e)
        {

        }

        return row;
    }
}