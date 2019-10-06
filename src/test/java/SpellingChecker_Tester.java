
import com.makesystem.pidgey.monitor.Monitor;
import com.makesystem.pidgey.tester.AbstractTester;
import com.makesystem.pidgey.util.LinkedMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import pt.tumba.spell.DefaultWordFinder;
import pt.tumba.spell.SpellChecker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Richeli.vargas
 */
public class SpellingChecker_Tester extends AbstractTester {

    private final Map<String, String> expected = new LinkedMap<>();

    public static void main(String[] args) {
        new SpellingChecker_Tester().run();
    }

    @Override
    protected void preExecution() {

        /* DDD */
        expected.put("dd", "ddd");
        expected.put("valordd", "ddd");
        expected.put("ddd", "ddd");
        expected.put("valorddd", "ddd");
        expected.put("dddd", "ddd");
        expected.put("valordddd", "ddd");
        expected.put("dddtel", "ddd");
        /* Telefone */
        expected.put("telefone", "telefone");
        expected.put("telefonne", "telefone");
        expected.put("telef", "telefone");
        expected.put("teleff", "telefone");
        expected.put("phone", "telefone");
        expected.put("phonne", "telefone");
        expected.put("fone", "telefone");
        expected.put("fonne", "telefone");
        expected.put("tel", "telefone");
        /* Data de Bloqueio */
        expected.put("databloqueio", "databloqueio");
        expected.put("data_bloqueio", "databloqueio");
        expected.put("databloquei", "databloqueio");
        expected.put("data_bloquei", "databloqueio");
        expected.put("databloque", "databloqueio");
        expected.put("data_bloque", "databloqueio");
        expected.put("databloqu", "databloqueio");
        expected.put("data_bloqu", "databloqueio");
        expected.put("databloq", "databloqueio");
        expected.put("data_bloq", "databloqueio");
        expected.put("datainicio", "databloqueio");
        expected.put("data_inicio", "databloqueio");
        expected.put("apartirde", "databloqueio");
        expected.put("apartir_de", "databloqueio");
        /* Evento */
        expected.put("evento", "evento");
        expected.put("event", "evento");
        expected.put("even", "evento");
        /* Data de inscrição */
        expected.put("datainscricao", "datainscricao");
        expected.put("data_inscricao", "datainscricao");
        expected.put("datainscrica", "datainscricao");
        expected.put("data_inscrica", "datainscricao");
        expected.put("datainscric", "datainscricao");
        expected.put("data_inscric", "datainscricao");
        expected.put("datainscri", "datainscricao");
        expected.put("data_inscri", "datainscricao");
        expected.put("datainscr", "datainscricao");
        expected.put("data_inscr", "datainscricao");
        expected.put("datainsc", "datainscricao");
        expected.put("data_insc", "datainscricao");
        expected.put("datains", "datainscricao");
        expected.put("data_ins", "datainscricao");
        expected.put("datacadastro", "datainscricao");
        expected.put("data_cadastro", "datainscricao");
        expected.put("cadastro", "datainscricao");
        expected.put("cadastradoem", "datainscricao");
        expected.put("cadastrado_em", "datainscricao");
        /* Nome */
        expected.put("nome", "nome");
        expected.put("nomee", "nome");
        expected.put("name", "nome");
        expected.put("namee", "nome");
        expected.put("contato", "contato");
        expected.put("contatoo", "contato");
        expected.put("contat", "contato");
        expected.put("contat.", "contato");
        expected.put("razaosocial", "razaosocial");
        expected.put("razaosociall", "razaosocial");
        expected.put("razao_social", "razaosocial");
        expected.put("razao_sociall", "razaosocial");
        expected.put("nomecliente", "nome");
        expected.put("nome_cliente", "nome");
        expected.put("nome/razaosocial", "nome");
        expected.put("nome_razaosocial", "nome");
        expected.put("name/razaosocial", "nome");
        expected.put("name_razaosocial", "nome");
        expected.put("nome/razao_social", "nome");
        expected.put("nome_razao_social", "nome");
        expected.put("name/razao_social", "nome");
        expected.put("name_razao_social", "nome");
        expected.put("razaosocial/nome", "nome");
        expected.put("razaosocial/name", "nome");
        expected.put("cliente", "cliente");
        expected.put("client", "cliente");
        expected.put("client.", "cliente");
        expected.put("clientee", "cliente");
        /* Tipo pessoa */
        expected.put("tipopessoa", "tipopessoa");
        expected.put("tipo_pessoa", "tipopessoa");
        expected.put("pessoa", "tipopessoa");
        expected.put("pessoaa", "tipopessoa");
        expected.put("pf/pj", "pf");
        expected.put("pfpj", "pf");
        expected.put("pj/pf", "pf");
        expected.put("pjpf", "pf");
        expected.put("tipop", "tipo");
        expected.put("tipope", "tipo");
        expected.put("tipopes", "tipo");
        expected.put("tipodepessoa", "tipopessoa");
        expected.put("tipodepesoa", "tipopessoa");
        expected.put("tipo_de_pessoa", "tipopessoa");
        expected.put("tppessoa", "tipopessoa");
        /* E-mail */
        expected.put("email", "email");
        expected.put("emaill", "email");
        expected.put("eemail", "email");
        expected.put("eemaill", "email");
        expected.put("e-mail", "email");
        expected.put("e-maill", "email");
        expected.put("mail", "email");
        expected.put("maill", "email");
        /* Documento */
        expected.put("documento", "documento");
        expected.put("cpf", "cpf");
        expected.put("cnpj", "cnpj");
        expected.put("cpf/cnpj", "cpf");
        expected.put("cpf_cnpj", "cpf");
        expected.put("cpfcnpj", "cpf");
        expected.put("cnpj/cpf", "cnpj");
        expected.put("cnpj_cpf", "cnpj");
        expected.put("cnpjcpf", "cnpj");
        expected.put("docume", "documento");
        expected.put("docume.", "documento");
        expected.put("documen", "documento");
        expected.put("documen.", "documento");
        expected.put("document", "documento");
        expected.put("document.", "documento");
        expected.put("doc", "documento");
        expected.put("doc.", "documento");
        /* Logradouro */
        expected.put("logradouro", "logradouro");
        expected.put("logradouroo", "logradouro");
        expected.put("lograd", "logradouro");
        expected.put("lograd.", "logradouro");
        expected.put("logrado", "logradouro");
        expected.put("logrado.", "logradouro");
        expected.put("endereco", "endereco");
        expected.put("endereço", "endereco");
        expected.put("rua", "rua");
        expected.put("ende", "endereco");
        expected.put("ende.", "endereco");
        expected.put("end.", "endereco");
        expected.put("endere", "endereco");
        expected.put("endere.", "endereco");
        expected.put("enderee", "endereco");
        expected.put("enderee.", "endereco");
        expected.put("enderec", "endereco");
        expected.put("enderec.", "telefone");
        expected.put("enderecoo", "endereco");
        /* Número */
        expected.put("numero", "numero");
        expected.put("número", "numero");
        expected.put("numeroo", "numero");
        expected.put("number", "numero");
        expected.put("nr", "numero");
        expected.put("nr.", "numero");
        expected.put("nro", "numero");
        expected.put("nro.", "numero");
        expected.put("num", "numero");
        expected.put("num.", "numero");
        expected.put("nmero", "numero");
        expected.put("nmero.", "numero");
        expected.put("n", "numero");
        expected.put("n.", "numero");
        expected.put("n°", "numero");
        /* Complemento */
        expected.put("complemento", "complemento");
        expected.put("comp", "complemento");
        expected.put("comp.", "complemento");
        expected.put("compl", "complemento");
        expected.put("compl.", "complemento");
        expected.put("comple", "complemento");
        expected.put("comple.", "complemento");
        expected.put("complem", "complemento");
        expected.put("complem.", "complemento");
        expected.put("compleme", "complemento");
        expected.put("compleme.", "complemento");
        expected.put("complemen", "complemento");
        expected.put("complemen.", "complemento");
        expected.put("complement", "complemento");
        expected.put("complement.", "complemento");
        /* CEP */
        expected.put("cep", "cep");
        expected.put("cp", "cep");
        expected.put("cp.", "cep");
        /* Bairro */
        expected.put("bairro", "bairro");
        expected.put("bairr", "bairro");
        expected.put("bairr.", "bairro");
        expected.put("bair", "bairro");
        expected.put("bair.", "bairro");
        /* Cidade */
        expected.put("cidade", "cidade");
        expected.put("cidadee", "cidade");
        expected.put("city", "cidade");
        expected.put("cidad", "cidade");
        expected.put("cidad.", "cidade");
        expected.put("cida", "cidade");
        expected.put("cida.", "cidade");
        expected.put("municipio", "municipio");
        /* Estado */
        expected.put("estado", "estado");
        expected.put("uf", "uf");
        expected.put("estad", "estado");
        expected.put("estad.", "estado");
        /* Quadra */
        expected.put("quadra", "quadra");
        expected.put("qd", "quadra");
        expected.put("qd.", "quadra");
        expected.put("quad", "quadra");
        expected.put("quad.", "quadra");
        expected.put("quadr", "quadra");
        expected.put("quadr.", "quadra");
        /* Lote */
        expected.put("lote", "lote");
        expected.put("lot", "lote");
        expected.put("lot.", "lote");
        expected.put("lt", "lote");
        expected.put("lt.", "lote");
        /* Bloco */
        expected.put("bloco", "bloco");
        expected.put("bl", "bloco");
        expected.put("bl.", "bloco");
        expected.put("bloc", "bloco");
        expected.put("bloc.", "bloco");
    }

    @Override
    protected void execution() {
        try {
            final SpellChecker spellCheck = new SpellChecker();
            spellCheck.initialize("/mailing_columns.dictionary");

            Monitor.MONITOR_JRE.exec(()
                    -> expected.entrySet().stream().forEach(entry -> {

                        final String word = entry.getKey().replaceAll("[^a-zA-Z]", "");
                        final String expectedWord = entry.getValue();

                        final Collection<String> mostSimilar = (Collection<String>) spellCheck
                                .findMostSimilarList(word, true)
                                .stream()
                                .collect(Collectors.toSet());

                        if (mostSimilar.isEmpty()) {
                            System.out.println(word + " : 0");
                        }

                    })
            ).print();

            Monitor.MONITOR_JRE.exec(()
                    -> expected.entrySet().stream().forEach(entry -> {

                        final String word = entry.getKey().replaceAll("[^a-zA-Z]", "");
                        final String expectedWord = entry.getValue();

                        final Collection<String> mostSimilar = (Collection<String>) spellCheck
                                .findMostSimilarList(word)
                                .stream()
                                .collect(Collectors.toSet());

                        if (mostSimilar.isEmpty()) {
                            System.out.println(word + " : 0");
                        }

                    })
            ).print();

            Monitor.MONITOR_JRE.exec(()
                    -> expected.entrySet().stream().forEach(entry -> {

                        final String word = entry.getKey();
                        final String expectedWord = entry.getValue();

                        final DefaultWordFinder finder = new DefaultWordFinder(word);
                        String aux;
                        while ((aux = finder.next()) != null) {
                            final String aux2 = spellCheck.findMostSimilar(aux);
                            final Collection suggestions = spellCheck.findMostSimilarList(aux);
                            if (aux2 == null) {
                                System.out.println("MISSPELT WORD: " + aux);
                                System.out.println("\tNo suggestions");
                            } else if (!aux2.equals(aux.toLowerCase())) {
                                System.out.println("MISSPELT WORD: " + aux);
                                if (suggestions.isEmpty()) {
                                    System.out.println("\tNo suggestions");
                                } else {
                                    System.out.println("\tBest Suggestion: " + aux2);
                                }
                                for (Iterator suggestedWord = suggestions.iterator();
                                        suggestedWord.hasNext();) {
                                    System.out.println(
                                            "\tSuggested Word: " + suggestedWord.next());
                                }
                            } else {
                                System.out.println("CORRECT WORD: " + word + " == " + aux);
                            }
                        }
                        
                        System.out.println();

                    })
            ).print();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void posExecution() {
    }
}
