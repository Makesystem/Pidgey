/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.brazil;

import java.util.Arrays;

/**
 *
 * @author Richeli.vargas
 */
public enum DDD {
    DDD_11("11", "São Paulo", Uf.SP),
    DDD_12("12", "São José dos Campos", Uf.SP),
    DDD_13("13", "Santos", Uf.SP),
    DDD_14("14", "Bauru", Uf.SP),
    DDD_15("15", "Sorocaba", Uf.SP),
    DDD_16("16", "Ribeirão Preto", Uf.SP),
    DDD_17("17", "São José do Rio Preto", Uf.SP),
    DDD_18("18", "Presidente Prudente", Uf.SP),
    DDD_19("19", "Campinas", Uf.SP),
    DDD_21("21", "Rio de Janeiro", Uf.RJ),
    DDD_22("22", "Campos dos Goytacazes", Uf.RJ),
    DDD_24("24", "Volta Redonda", Uf.RJ),
    DDD_27("27", "Vila Velha/Vitória", Uf.ES),
    DDD_28("28", "Cachoeiro de Itapemirim", Uf.ES),
    DDD_31("31", "Belo Horizonte", Uf.MG),
    DDD_32("32", "Juiz de Fora", Uf.MG),
    DDD_33("33", "Governador Valadares", Uf.MG),
    DDD_34("34", "Uberlândia", Uf.MG),
    DDD_35("35", "Poços de Caldas", Uf.MG),
    DDD_37("37", "Divinópolis", Uf.MG),
    DDD_38("38", "Montes Claros", Uf.MG),
    DDD_41("41", "Curitiba", Uf.PR),
    DDD_42("42", "Ponta Grossa", Uf.PR),
    DDD_43("43", "Londrina", Uf.PR),
    DDD_44("44", "Maringá", Uf.PR),
    DDD_45("45", "Foz do Iguaçú", Uf.PR),
    DDD_46("46", "Francisco Beltrão/Pato Branco", Uf.PR),
    DDD_47("47", "Joinville", Uf.SC),
    DDD_48("48", "Florianópolis", Uf.SC),
    DDD_49("49", "Chapecó", Uf.SC),
    DDD_51("51", "Porto Alegre", Uf.RS),
    DDD_53("53", "Pelotas", Uf.RS),
    DDD_54("54", "Caxias do Sul", Uf.RS),
    DDD_55("55", "Santa Maria", Uf.RS),
    DDD_61("61", "Brasília", Uf.DF),
    DDD_62("62", "Goiânia", Uf.GO),
    DDD_63("63", "Palmas", Uf.TO),
    DDD_64("64", "Rio Verde", Uf.GO),
    DDD_65("65", "Cuiabá", Uf.MT),
    DDD_66("66", "Rondonópolis", Uf.MT),
    DDD_67("67", "Campo Grande", Uf.MS),
    DDD_68("68", "Rio Branco", Uf.AC),
    DDD_69("69", "Porto Velho", Uf.RO),
    DDD_71("71", "Salvador", Uf.BA),
    DDD_73("73", "Ilhéus", Uf.BA),
    DDD_74("74", "Juazeiro", Uf.BA),
    DDD_75("75", "Feira de Santana", Uf.BA),
    DDD_77("77", "Barreiras", Uf.BA),
    DDD_79("79", "Aracaju", Uf.SE),
    DDD_81("81", "Recife", Uf.PE),
    DDD_82("82", "Maceió", Uf.AL),
    DDD_83("83", "João Pessoa", Uf.PB),
    DDD_84("84", "Natal", Uf.RN),
    DDD_85("85", "Fortaleza", Uf.CE),
    DDD_86("86", "Teresina", Uf.PI),
    DDD_87("87", "Petrolina", Uf.PE),
    DDD_88("88", "Juazeiro do Norte", Uf.CE),
    DDD_89("89", "Picos", Uf.PI),
    DDD_91("91", "Belém", Uf.PA),
    DDD_92("92", "Manaus", Uf.AM),
    DDD_93("93", "Santarém", Uf.PA),
    DDD_94("94", "Marabá", Uf.PA),
    DDD_95("95", "Boa Vista", Uf.RR),
    DDD_96("96", "Macapá", Uf.AP),
    DDD_97("97", "Coari", Uf.AM),
    DDD_98("98", "São Luís", Uf.MA),
    DDD_99("99", "Imperatriz", Uf.MA);

    private final String ddd;
    private final String regiao;
    private final Uf uf;

    private DDD(String ddd, String regiao, Uf uf) {
        this.ddd = ddd;
        this.regiao = regiao;
        this.uf = uf;
    }

    public String getDdd() {
        return ddd;
    }

    public String getRegiao() {
        return regiao;
    }

    public Uf getUf() {
        return uf;
    }

    public static DDD fromString(final String ddd) {

        if (ddd == null || ddd.trim().isEmpty()) {
            return null;
        }

        for (DDD _ddd : values()) {
            if (_ddd.toString().equals(ddd.toUpperCase()) || _ddd.getDdd().equals(ddd)) {
                return _ddd;
            }
        }
        return null;
    }
    
    public static DDD[] fromUf(final Uf uf){
        return Arrays.stream(values()).filter(ddd -> ddd.getUf().equals(uf)).toArray(DDD[]::new);
    }
}
